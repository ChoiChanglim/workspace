package my.kakao.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.kakao.constant.KakaoApiConstant;
import my.kakao.constant.KakaoApiConstant.KakaoApi;
import my.kakao.service.KakaoApiService;
import my.random.api.util.AesCipher;
import my.random.api.util.RequestUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@RequestMapping("/kakao")
@Controller
public class KakaoOauthController {
	private static Logger LOG = LoggerFactory.getLogger(KakaoOauthController.class);
	
	public String getKakaoRedirectUri(HttpServletRequest req){
        
        return "http://"+req.getServerName()+KakaoApiConstant.KakaoCallbackUri;
        
    }

    public HashMap<String, Object> getQstrMapAtOauth(HttpServletRequest req, AesCipher cipher){
        //인증요청할때의 파라미터들
        HashMap<String, Object> qstrMap = new HashMap<String, Object>();
        if(RequestUtil.getCookieMap(req).containsKey(KakaoApiConstant.KakaoQstrAtOauthCookieName)){
            String foqs = cipher.decryptString(RequestUtil.getCookieMap(req).get(KakaoApiConstant.KakaoQstrAtOauthCookieName));
            qstrMap = RequestUtil.qstrSplit(foqs);
        }
        return qstrMap;
    }


    /**
     * 카카오 oauth redirect
     * @param req
     * @param res
     */
    @RequestMapping(value = "/oauth")
    public ModelAndView kakao_oauth(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="code", required = false, defaultValue="") String code,
            @RequestParam(value="state", required = false, defaultValue="") String state
            ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        String redirect_uri = getKakaoRedirectUri(req);
        String kakaoAccessToken = KakaoApiService.getKakaoAccessToken(code, redirect_uri);

        AesCipher cipher  = new AesCipher(KakaoApiConstant.KakaoServerCipherKey);
        RequestUtil.setCookieByMinute(res, KakaoApiConstant.KakaoAccessTokenCookieName, cipher.encryptString(kakaoAccessToken), 360);

        //oauth인증 시점의 파라미터들
        HashMap<String, Object> refererParamMap = getQstrMapAtOauth(req, cipher);
        
        //이벤트로 인한 메시지 전송
        String result = KakaoApiService.EventByProcess(kakaoAccessToken, refererParamMap);
        modelAndView.addObject("result", result);
        modelAndView.addObject("snsname", "kakao");
        
        modelAndView.setView(new MappingJackson2JsonView());
        //modelAndView.setViewName("sns/callback");
        return modelAndView;
    }
    /**
     * 카카오 oauth 요청
     * @param req
     * @param res
     * @param code
     * @return
     */
    @RequestMapping(value = "/login")
    public ModelAndView kakao_oauth_login(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="code", required = false, defaultValue="") String code,
            @RequestParam(value="msg", required = false, defaultValue="") String msg
            ) {
        ModelAndView modelAndView = new ModelAndView();

        AesCipher cipher  = new AesCipher(KakaoApiConstant.KakaoServerCipherKey);
        //카카오 엑세스토큰 있으면 저장된 토큰 사용
        if(RequestUtil.getCookieMap(req).containsKey(KakaoApiConstant.KakaoAccessTokenCookieName)){
            String token = cipher.decryptString(RequestUtil.getCookieMap(req).get(KakaoApiConstant.KakaoAccessTokenCookieName));
            modelAndView.addObject("hasToken", true);

            //코드(요청)별 처리
            /*SNSShareCodeEnum snsShareCodeEnum = SNSShareCodeEnum.GetSNSShareCodeEnum(code);
            boolean isComplete = snsEventService.isShareComplete(snsEventService.getUserkeyInCookie(req), "kakao", snsShareCodeEnum);
            if(isComplete == true && snsShareCodeEnum == SNSShareCodeEnum.PRE_REGIST_LIKE){
                modelAndView.addObject("result", "success");
                return modelAndView;
            }
            String result = KakaoApiService.kakaoProcessByCode(snsEventService.getUserkeyInCookie(req), snsShareCodeEnum, token, RequestUtil.reqParamToMap(req));
            modelAndView.addObject("result", result);*/
            modelAndView.setView(new MappingJackson2JsonView());
            return modelAndView;
        }


        //oauth 요청시의 파라미터 쿠키에 저장 callback에서의 처리를 위해
        RequestUtil.setCookieByMinute(res, KakaoApiConstant.KakaoQstrAtOauthCookieName, cipher.encryptString(RequestUtil.getQstr(req)), 30);


        HashMap<String, String> param = new HashMap<String, String>();
        param.put("client_id", KakaoApiConstant.KakaoAppkey);
        param.put("redirect_uri", getKakaoRedirectUri(req));
        param.put("response_type", "code");
        param.put("state", code);
        param.put("encode_state", "false");
        param.put("scope", "talk_message,story_publish");

        
        String oauthUrl = KakaoApi.GET_CODE.getOauthApi()+"?"+RequestUtil.getMapToQstr(param);
        modelAndView.addObject("oauthUrl", oauthUrl);
        modelAndView.addObject("result", "ready");
        modelAndView.addObject("hasToken", false);
        
        modelAndView.setView(new MappingJackson2JsonView());
        //modelAndView.setViewName("redirect:"+oauthUrl);
        return modelAndView;
    }
    
    @RequestMapping(value = "/keyboard")
    public ModelAndView kakao_plus_keyboard(HttpServletRequest req, HttpServletResponse res){
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setView(new MappingJackson2JsonView());
    	System.err.println("test");
    	modelAndView.addObject("Keyboard", "text");
    	return modelAndView;
    }
}
