package my.random.controller;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.constant.SupportMember.MemberDivEnum;
import my.random.api.constant.SupportMember.MemberReasonEnum;
import my.random.api.constant.SupportMember.MemberStateEnum;
import my.random.api.exception.CustomException;
import my.random.api.exception.RedirectException;
import my.random.api.util.AesCipher;
import my.random.api.util.JsonUtil;
import my.random.api.util.RequestUtil;
import my.random.bean.Member;
import my.random.service.KakaoApiService;
import my.random.service.KakaoApiService.KakaoApi;
import my.random.service.LoginService;
import my.random.service.LoginServiceImpl;
import my.random.service.MemberService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/api/kakao")
@Controller
public class KakaoApiController {
	private static Logger LOG = LoggerFactory.getLogger(KakaoApiController.class);
	@Autowired
	LoginService loginService;
	
	@Autowired
	MemberService memberService;
	
	AesCipher cipher  = new AesCipher(KakaoApiService.KakaoServerCipherKey);
	/**
	 * 
	 * @param req
	 * @param cipher
	 * @return
	 */
    public HashMap<String, Object> getQstrMapAtOauth(HttpServletRequest req, AesCipher cipher){
        //인증요청할때의 파라미터들
        HashMap<String, Object> qstrMap = new HashMap<String, Object>();
        if(RequestUtil.getCookieMap(req).containsKey(KakaoApiService.KakaoQstrAtOauthCookieName)){
            String foqs = cipher.decrypt(RequestUtil.getCookieMap(req).get(KakaoApiService.KakaoQstrAtOauthCookieName));
            qstrMap = RequestUtil.qstrSplit(foqs);
        }
        return qstrMap;
    }


    /**
     * 카카오 login
     * @param req
     * @param res
     */
    @RequestMapping(value = "/login")
    public ModelAndView kakao_oauth(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="code", required = false, defaultValue="") String code,
            @RequestParam(value="expt", required = false, defaultValue="") String expt
            ) {
        ModelAndView modelAndView = new ModelAndView();
        String domain = req.getScheme()+"://" +req.getServerName();        
        
        String state = KakaoApi.GetgenerateState();        
        String request_oauth_url = KakaoApiService.GetKakaoAuthUrl(state, domain, expt);
        RequestUtil.setCookieByMinute(res, KakaoApiService.STATE_COOKIE_NAME, cipher.encrypt(state), 30);
        modelAndView.addObject("request_oauth_url", request_oauth_url);
        modelAndView.setViewName("redirect:"+request_oauth_url);
        return modelAndView;
    }
    /**
     * 카카오 oauth 요청
     * @param req
     * @param res
     * @param code
     * @return
     */
    @RequestMapping(value = "/redirect_login")
    public ModelAndView redirect_login(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="code", required = false, defaultValue="") String code,
            @RequestParam(value="state", required = false, defaultValue="") String state
            ) {
		if(RequestUtil.getCookieMap(req).containsKey(KakaoApiService.STATE_COOKIE_NAME) == false){
			LOG.error("naver 인증코드 발급시 state token이 없음");
			throw CustomException.EMPTY_STATE_TOKEN;
		}
		String cookie_state = cipher.decrypt(RequestUtil.getCookieMap(req).get(KakaoApiService.STATE_COOKIE_NAME));
		if(state.equals(cookie_state) == false){
			LOG.error("kakao 인증코드 발급시 state값이 맞지 않음");
			throw CustomException.INVALID_STATE_TOKEN;
		}
        ModelAndView modelAndView = new ModelAndView();
        
        
        //최초 access token, refresh token 세팅
        String domain = req.getScheme()+"://" +req.getServerName();        
        HashMap<String, Object> tokenResultMap = KakaoApiService.getKakaoAccessToken(code, domain);
        String accessToken = (String)tokenResultMap.get("access_token");
        String refreshToken = (String)tokenResultMap.get("refresh_token");
        RequestUtil.setCookieByMinute(res, KakaoApiService.KakaoAccessTokenCookieName, cipher.encrypt(accessToken), 55); //55분
        RequestUtil.setCookie(res, KakaoApiService.KakaoRefreshTokenCookieName, cipher.encrypt(refreshToken), 1);	//1일 
        
        HashMap<String, Object> kakaoUserProfile = KakaoApiService.KakaoGetUserProfile(accessToken);
        
        JSONObject kakaoAccountJSONObject = new JSONObject(kakaoUserProfile).getJSONObject("kakao_account");
        JSONObject kakaoPropertiesJSONObject = new JSONObject(kakaoUserProfile).getJSONObject("properties");
        
        
        HashMap<String, Object> kakaoAccount = JsonUtil.JSONSourceToHashMap(kakaoAccountJSONObject.toString());
        HashMap<String, Object> kakaoProperties = JsonUtil.JSONSourceToHashMap(kakaoPropertiesJSONObject.toString());
        LOG.debug(new JSONObject(kakaoAccount).toString());
    	if(kakaoProperties.containsKey("nickname") == false 
        		|| Boolean.valueOf((String)kakaoAccount.get("has_email")) == false){
        	//이름과 이메일정보 제공에 동의하지 않으면 로그인 불가능.
        	throw RedirectException.REDIRECT_LOGIN_PAGE_BY_NO_REQUIRED_PARAMETERS;
        }
    	String nick = (String)kakaoProperties.get("nickname");
    	JSONObject profileJSONObject = kakaoAccountJSONObject.getJSONObject("profile");
    	
    	HashMap<String, Object> profile = JsonUtil.JSONSourceToHashMap(profileJSONObject.toString());
    	if(profile.containsKey("nickname") ){
    		nick = (String)profile.get("nickname");
    	}
        
        //로그인처리
        String kid = (String)kakaoUserProfile.get("id");
        
        Member singnupMember = loginService.getPletformMember(MemberDivEnum.KAKAO, kid);
    	if(null == singnupMember){
        	
        	String gender = "";
        	if(Boolean.valueOf((String)kakaoAccount.get("has_gender"))){
        		if("male".equals((String)kakaoAccount.get("gender"))){
        			gender = "M";
        		}else{
        			gender = "F";
        		}
        		
        	}
	        Member m = new Member();
	        m.setUid(kid);
	        m.setUdiv(MemberDivEnum.KAKAO.name());
	        m.setUname(nick);
	        m.setEmail((String)kakaoAccount.get("email"));        
	        m.setGender(gender);
	        m.setAgeRangeMin(0);
	        m.setAgeRangeMax(0);
	        m.setState(MemberStateEnum.ACTIVE.name());
	        m.setRegdate(new Date());
	        m.setRecentLoginDate(new Date());
	        m.setPasswd("");
	        memberService.insert(m);
        }
    	
    	if(null != singnupMember && nick.equals(singnupMember.getUname()) == false){
    		memberService.platformNickUpdate(MemberDivEnum.KAKAO, kid, nick);
    	}
        
        loginService.signin(MemberDivEnum.KAKAO, res, kid, false);
        
        
        AesCipher cipher  = new AesCipher(LoginServiceImpl.SIGNED_COOKIE_CIPHER_KEY);
        String referer = "/";
        modelAndView.setViewName("redirect:"+referer);
        return modelAndView;
	}
    

}