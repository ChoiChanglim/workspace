package my.random.api.exception;

import java.util.HashMap;

import org.json.JSONObject;

public class ExceptionEnum {

    public static enum ResponseType{
        NOT_ALLOWED_USER,
        INVALID_PARAMETER,
        INVALID_TOKEN,
        INVALID_COUNTRY,
        INVALID_LANGUAGE,
        INVALID_USER,
        INVALID_EVENT_INFO,
        INVALID_EMAIL,
        INVALID_NICK,
        INVALID_HEADER_TOKEN,
        INVALID_HEADER_REFERER,
        INVALID_COOKIE_TOKEN,
        EMPTY_ATTACH_FILE,
        EMPTY_LANGUAGE,
        EMPTY_PARAMETER,
        EMPTY_TOKEN,
        EMPTY_COUNTRY,
        EMPTY_USER,
        EMPTY_EMAIL,
        EMPTY_NICK,
        EMPTY_HEADER_TOKEN,
        EMPTY_HEADER_REFERER,
        EMPTY_COOKIE_TOKEN,
        EMPTY_DESTINYINFO, 
        INVALID_VERSION, 
        SYSTEM_FAILURE,
        
        INVELID_PERCENT_RANGE,
        INVELID_SUM_ODDEVEN,
        
        INSTAGRAM_API_EXCEPTION,
        INSTAGRAM_AUTH_CODE_EXCEPTION,
        INSTAGRAM_ACCESS_TOKEN_EXCEPTION, 
        
        LOTTO_CRAWLING_EXCEPTION, 
        HTTP_WEB_CALL_EXCEPTION, UploadFileSaveException, INVALID_IMAGE_FILE, MimeTypeDetectException;
        
        private Object message;
        public void setMessage(String message) {
			this.message = message;
		}
        public void setMessage(HashMap<String, Object> message) {
			this.message = message;
		}
        public void setMessage(JSONObject message) {
			this.message = message;
		}
        
		public String getMessage() {
			
			if(null == message){
				return this.name();
			}
			
			if(message instanceof HashMap){
				JSONObject source = new JSONObject((HashMap)message);
				JSONObject obj = new JSONObject();
				obj.put(this.name(), source);
				return obj.toString();
			}
			
			return null;
		}
    }
}
