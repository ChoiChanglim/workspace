package my.random.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.annotation.TilesOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LayoutInterceptor extends HandlerInterceptorAdapter{

    private static Logger LOG = LoggerFactory.getLogger(LayoutInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        try{
            TilesOn tilesOn = ((HandlerMethod)handler).getMethodAnnotation(TilesOn.class);
            /*System.err.println("### "+modelAndView.getViewName());
            String[] viewPath = modelAndView.getViewName().split("[/]");
            String viewName = "";
            for(int i=0;i<viewPath.length;i++){
                System.err.println("viewPath["+i+"]:"+viewPath[i]);
                viewName = viewPath[i].replace("", "index");
            }
            System.err.println("$$$ " + viewName);*/
            if(null != tilesOn){
                modelAndView.setViewName(modelAndView.getViewName()+".tiles");
            }
            LOG.debug("posthandle-includeTiles TilesOn | VIEWNAME - ["+modelAndView.getViewName()+"]");
        }catch(Exception e){
            LOG.debug("resources!");
            e.printStackTrace();
        }



    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){

    }

}
