package my.random.api.interceptor;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.constant.SessionScopeBean;
import my.random.api.constant.SupportMember.MemberDivEnum;
import my.random.api.util.AesCipher;
import my.random.api.util.RequestUtil;
import my.random.api.util.StringUtil;
import my.random.service.KakaoApiService;
import my.random.service.LoginService;
import my.random.service.LoginServiceImpl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CookieInterceptor extends HandlerInterceptorAdapter{
	@Inject
    Provider<SessionScopeBean> sessionScopebeanFactory;
	
	@Autowired
    LoginService loginService;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	SessionScopeBean sessionBean = this.sessionScopebeanFactory.get();
        HashMap<String, String> cookieMap = RequestUtil.getCookieMap(request);
        sessionBean.setSignIn(loginService.isSignIn(cookieMap));
        
        if(sessionBean.isSignIn() == true){
        	AesCipher cipher  = new AesCipher(LoginServiceImpl.SIGNED_COOKIE_CIPHER_KEY);
        	sessionBean.setUid(StringUtil.nullToBlank(cipher.decrypt(cookieMap.get(LoginServiceImpl.SIGNED_UID_COOKIE_NAME)).trim()));
        	sessionBean.setUdiv(StringUtil.nullToBlank(cipher.decrypt(cookieMap.get(LoginServiceImpl.SIGNED_UDIV_COOKIE_NAME)).trim()));
        	
        	MemberDivEnum memberDivEnum = MemberDivEnum.GetMemberDivEnum(sessionBean.getUdiv());
        	if((memberDivEnum == MemberDivEnum.KAKAO) && (cookieMap.containsKey(KakaoApiService.KakaoAccessTokenCookieName) == false)){
        		if(cookieMap.containsKey(KakaoApiService.KakaoRefreshTokenCookieName)){
        			AesCipher cipher_kakao  = new AesCipher(KakaoApiService.KakaoServerCipherKey);
            		String refreshToken =  cipher_kakao.decrypt(cookieMap.get(KakaoApiService.KakaoRefreshTokenCookieName)).trim();
                	HashMap<String, Object> kakaoResult = KakaoApiService.GetRefreshKakaoAccessToken(refreshToken);
                	if(kakaoResult.containsKey("access_token")){
                		String kakaoAccessToken = (String)kakaoResult.get("access_token");
                		boolean hasValidToken = KakaoApiService.HasValidToken(kakaoAccessToken);
                		//System.err.println("kakaoAccessToken:"+kakaoAccessToken+", hasValidToken:"+hasValidToken);
                		if(hasValidToken){
                			RequestUtil.setCookieByMinute(response, KakaoApiService.KakaoAccessTokenCookieName, cipher.encrypt(kakaoAccessToken), 55); //55분
                		}
                	}
        		}
        		
        	}
        	
        }
        
        if(RequestUtil.getCookieMap(request).containsKey("ukey") == false){
            String ukey = StringUtil.getRandomString(6);
            RequestUtil.setCookie(response, "ukey", ukey, 300);
        }
        
        super.preHandle(request, response, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }


    public static String GetUkey(HttpServletRequest request, String param_ukey){
    	if("".equals(param_ukey) == false){
    		return param_ukey;
    	}
        if(RequestUtil.getCookieMap(request).containsKey("ukey")){
            String ukey = RequestUtil.getCookieMap(request).get("ukey");
            return ukey;
        }

        return StringUtil.getRandomString(6);
    }
    public static String GetUkey(HttpServletRequest request){
    	
        if(RequestUtil.getCookieMap(request).containsKey("ukey")){
            String ukey = RequestUtil.getCookieMap(request).get("ukey");
            return ukey;
        }

        return StringUtil.getRandomString(6);
    }

    public static String GetNick(HttpServletRequest request){
        if(RequestUtil.getCookieMap(request).containsKey("ukey")){
            String ukey = RequestUtil.getCookieMap(request).get("ukey");
            if(RequestUtil.getCookieMap(request).containsKey(ukey)){
                String nick = RequestUtil.getCookieMap(request).get(ukey);
                return nick;
            }else{
                return ukey;
            }
        }
        return "";
    }

    public static void SetNick(HttpServletRequest request, HttpServletResponse response, String nick){
        if(RequestUtil.getCookieMap(request).containsKey("ukey")){
            String ukey = RequestUtil.getCookieMap(request).get("ukey");
            RequestUtil.setCookie(response, ukey, nick, 300);
        }else{
            String ukey = GetUkey(request);
            RequestUtil.setCookie(response, ukey, nick, 300);
        }
    }
    
    
}
