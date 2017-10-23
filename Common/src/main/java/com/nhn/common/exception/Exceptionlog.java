package com.nhn.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Component
public class Exceptionlog extends SimpleMappingExceptionResolver{
    private static Logger LOG = LoggerFactory.getLogger(Exceptionlog.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        if(exception instanceof CustomException){
            String from = "";
            JSONObject handler_json = new JSONObject(handler);
            if(handler_json.has("resolvedFromHandlerMethod")){
                JSONObject resolvedFromHandlerMethod = (JSONObject) handler_json.get("resolvedFromHandlerMethod");
                if(resolvedFromHandlerMethod.has("shortLogMessage")){
                    from = resolvedFromHandlerMethod.getString("shortLogMessage");
                }

            }
            LOG.error(exception.getMessage() + " : " +from);

            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
            modelAndView.addObject("result", exception.getMessage());
            return modelAndView;
        }

        return null;

    }
}
