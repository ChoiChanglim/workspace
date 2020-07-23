package my.random.api.exception;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Component
public class Exceptionlog extends SimpleMappingExceptionResolver{
    private static Logger LOG = LoggerFactory.getLogger(Exceptionlog.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
    	//커스텀 예외
		if (exception instanceof CustomException) {
			ModelAndView modelAndView = new ModelAndView(new ExceptionMappingJacksonJsonView());
			//커스텀 에러메시지는 Map형태가 아니면 정의명 리턴
			if(JsonUtil.isJSONValid(exception.getMessage()) == false){
				modelAndView.addObject("result", exception.getMessage());
				return modelAndView;
			}
			HashMap<String, Object> result = JsonUtil.ExceptionJSONSourceToHashMap(exception.getMessage());
			modelAndView.addObject("result", result);

			return modelAndView;
		}
		//리다이렉트 예외
		if (exception instanceof RedirectException) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:"+exception.getMessage());
			return modelAndView;
		}
		//업로드 용량 초과
		if(exception instanceof MaxUploadSizeExceededException){
			ModelAndView modelAndView = new ModelAndView(new ExceptionMappingJacksonJsonView());
			modelAndView.addObject("result", CustomException.MAX_UPLOADSIZE_EXCEEDED.getMessage());
			return modelAndView;
		}
		
		//DB접속 
		if(exception instanceof CannotCreateTransactionException){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/exception/connectionTimeout");
			return modelAndView;
		}
		LOG.error(exception.getMessage());
		return null;
    }
   
    
}
