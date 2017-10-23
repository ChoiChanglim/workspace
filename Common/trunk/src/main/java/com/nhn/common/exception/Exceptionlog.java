package com.nhn.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Component
public class Exceptionlog extends SimpleMappingExceptionResolver{
    private static Logger LOG = LoggerFactory.getLogger(Exceptionlog.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception) {
        if(exception instanceof CustomException){
            LOG.error(exception.toString());
            ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
            modelAndView.addObject("result", exception.getMessage());
        }

        return null;

    }
}
