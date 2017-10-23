package my.random.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.util.RequestUtil;
import my.random.api.util.StringUtil;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CookieInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
