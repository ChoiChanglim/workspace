package my.random.api.exception;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.instagram.constant.ExceptionMappingJacksonJsonView;
import my.random.api.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Component
public class Exceptionlog extends SimpleMappingExceptionResolver{
    private static Logger LOG = LoggerFactory.getLogger(Exceptionlog.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
    	if(exception instanceof CustomException){
            ModelAndView modelAndView = new ModelAndView(new ExceptionMappingJacksonJsonView());
            HashMap<String, Object> result = JsonUtil.JSONSourceToHashMap(exception.getMessage());
        	modelAndView.addObject("result", result);	
            
            return modelAndView;
        }
    	LOG.error(exception.getMessage());
		return null;
    }
   
    
}
