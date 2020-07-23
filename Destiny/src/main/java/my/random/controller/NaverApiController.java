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
import my.random.api.util.RequestUtil;
import my.random.api.util.StringUtil;
import my.random.bean.Member;
import my.random.service.LoginService;
import my.random.service.LoginServiceImpl;
import my.random.service.MemberService;
import my.random.service.NaverApiService;
import my.random.service.NaverApiService.NaverApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;



@RequestMapping("/api/naver")
@Controller
public class NaverApiController {	
	private static Logger LOG = LoggerFactory.getLogger(NaverApiController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	MemberService memberService;
	
	@Value("#{propinfo['Zone']}")
    private String Zone;
	
	
	
	@RequestMapping(value = "/login")
    public ModelAndView naver_oauth(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="code", required = false, defaultValue="") String code,
            @RequestParam(value="expt", required = false, defaultValue="") String expt
            ) {		
        ModelAndView modelAndView = new ModelAndView();
        AesCipher cipher  = new AesCipher(NaverApiService.NaverServerCipherKey);
        String state = NaverApi.GetgenerateState();        
        RequestUtil.setCookieByMinute(res, NaverApiService.STATE_COOKIE_NAME, cipher.encrypt(state), 30);
        
        String domain = req.getScheme()+"://" +req.getServerName();        
        String request_oauth_url = NaverApiService.GetNaverAuthUrl(state, domain, expt);
        modelAndView.addObject("request_oauth_url", request_oauth_url);
        modelAndView.setViewName("redirect:"+request_oauth_url);
        return modelAndView;
	}
	

	@RequestMapping(value = "/redirect_login")
    public ModelAndView redirect_login(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="code", required = false, defaultValue="") String code,
            @RequestParam(value="state", required = false, defaultValue="") String state
            ) throws Exception {
		AesCipher cipher  = new AesCipher(NaverApiService.NaverServerCipherKey);
		if(RequestUtil.getCookieMap(req).containsKey(NaverApiService.STATE_COOKIE_NAME) == false){
			LOG.error("naver 인증코드 발급시 state token이 없음");
			throw CustomException.EMPTY_STATE_TOKEN;
		}
		String cookie_state = cipher.decrypt(RequestUtil.getCookieMap(req).get(NaverApiService.STATE_COOKIE_NAME));
		if(state.equals(cookie_state) == false){
			LOG.error("naver 인증코드 발급시 state값이 맞지 않음");
			throw CustomException.INVALID_STATE_TOKEN;
		}
        ModelAndView modelAndView = new ModelAndView();
        
        //최초 access token, refresh token 세팅
    	HashMap<String, Object> tokenResultMap = NaverApiService.GetNaverAccessToken(cookie_state, code);
    	String accessToken = (String)tokenResultMap.get("access_token");
        String refreshToken = (String)tokenResultMap.get("refresh_token");
        RequestUtil.setCookieByMinute(res, NaverApiService.ACCESS_TOKEN_COOKIE_NAME, cipher.encrypt(accessToken), 55); //55분
        RequestUtil.setCookie(res, NaverApiService.REFRESH_TOKEN_COOKIE_NAME, cipher.encrypt(refreshToken), 5);	//5일 
        
        if(StringUtil.isNull(accessToken)){
        	throw CustomException.INVALID_ACCESS_TOKEN;
        }
        
        HashMap<String, Object> naverGetUserProfile = NaverApiService.NaverGetUserProfile(accessToken);
        if(naverGetUserProfile.containsKey("name") == false 
        		|| naverGetUserProfile.containsKey("email") == false){
        	//이름과 이메일정보 제공에 동의하지 않으면 로그인 불가능.
        	throw RedirectException.REDIRECT_LOGIN_PAGE_BY_NO_REQUIRED_PARAMETERS;
        }
        
        //로그인처리
        String nid = (String)naverGetUserProfile.get("id");
        boolean isSignup = loginService.isPletformMemberValid(MemberDivEnum.NAVER, nid);
        if(isSignup == false){
        	
        	String gender = "";
        	if(naverGetUserProfile.containsKey("gender")){
        		gender = (String)naverGetUserProfile.get("gender");
        	}
        	
        	String ageRange = "";
        	int ageMin = 0;
        	int ageMax = 0;
        	if(naverGetUserProfile.containsKey("age")){
        		ageRange = (String)naverGetUserProfile.get("age");
        		String[] arr = ageRange.split("-");
        		if(arr.length == 2){
        			String min = arr[0];
        			String max = arr[1];
        			if(StringUtil.isDigit(min)){
        				ageMin = Integer.valueOf(min);
        			}
        			if(StringUtil.isDigit(max)){
        				ageMax = Integer.valueOf(max);
        			}
        		}
        	}
        	
        	
	        Member m = new Member();
	        m.setUid(nid);
	        m.setUdiv(MemberDivEnum.NAVER.name());
	        m.setUname((String)naverGetUserProfile.get("name"));
	        m.setEmail((String)naverGetUserProfile.get("email"));        
	        m.setGender(gender);
	        m.setAgeRangeMin(ageMin);
	        m.setAgeRangeMax(ageMax);
	        m.setState(MemberStateEnum.ACTIVE.name());
	        m.setRegdate(new Date());
	        m.setRecentLoginDate(new Date());
	        m.setPasswd("");
	        memberService.insert(m);
        }
        loginService.signin(MemberDivEnum.NAVER, res, nid, false);
        
        String referer = "/";
        
        modelAndView.setViewName("redirect:"+referer);
        return modelAndView;
	}
	/**
	 * access token 삭제
	 * @param req
	 * @param res
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/logout")
    public ModelAndView naver_logout(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="code", required = false, defaultValue="") String code
            ) {
        ModelAndView modelAndView = new ModelAndView();
        AesCipher cipher  = new AesCipher(NaverApiService.NaverServerCipherKey);
        String accessToken = NaverApiService.GetAccessToken(req, res, cipher);
        NaverApiService.DeleteNaverAccessToken(accessToken);
        RequestUtil.deleteCookie(res, NaverApiService.REFRESH_TOKEN_COOKIE_NAME);
        RequestUtil.deleteCookie(res, NaverApiService.ACCESS_TOKEN_COOKIE_NAME);
        
        throw RedirectException.LOGIN_EXCEPTION; 
	}
	
	@RequestMapping(value = "/profile")
    public ModelAndView profile(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView modelAndView = new ModelAndView();
		AesCipher cipher  = new AesCipher(NaverApiService.NaverServerCipherKey);
		String accessToken = NaverApiService.GetAccessToken(req, res, cipher);
		HashMap<String, Object> response_profile= NaverApiService.NaverGetUserProfile(accessToken);
	    modelAndView.addObject("response_profile", response_profile);
	    modelAndView.setView(new MappingJackson2JsonView());
	    
	    return modelAndView;
	}
	
	
}
