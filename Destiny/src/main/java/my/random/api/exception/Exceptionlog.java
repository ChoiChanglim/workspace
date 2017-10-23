package my.random.api.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Component
public class Exceptionlog extends SimpleMappingExceptionResolver{
    private static Logger LOG = LoggerFactory.getLogger(Exceptionlog.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception) {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("result", exception.getMessage());
        if (exception instanceof MaxUploadSizeExceededException){
            response.setContentType("application/json");
            jsonResult.put("result", "MaxUploadSizeExceededException");
        }
        if(exception instanceof CustomException == false){
            LOG.error(exception.toString());
        }
        request.setAttribute("result", jsonResult);
        return super.resolveException(request, response, obj, exception);
    }
}
