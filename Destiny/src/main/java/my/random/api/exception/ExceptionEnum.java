package my.random.api.exception;

import java.util.HashMap;

import org.json.JSONObject;


public class ExceptionEnum {

    public static enum DefaultType{
    	NOT_ALLOWED_FILE_GROUP,
        NOT_ALLOWED_USER,
        NOT_RECEIPTED,
        NOT_INSTALL_VERSION,
        
        EMPTY_ATTACH_FILE,
        EMPTY_SRC,
        EMPTY_LANGUAGE,
        EMPTY_PARAMETER,
        EMPTY_TOKEN,
        EMPTY_SNO,
        EMPTY_COUNTRY,
        EMPTY_USER_ID,
        EMPTY_USER_NAME,
        EMPTY_USER_PW,
        EMPTY_USER_GENDER,
        EMPTY_USER_BIRTH,
        EMPTY_EMAIL,
        EMPTY_GROUP_NAME,
        EMPTY_GROUP_MANAGER,
        EMPTY_NICK,
        EMPTY_HEADER_TOKEN,
        EMPTY_HEADER_REFERER,
        EMPTY_COOKIE_TOKEN,
        EMPTY_PROMOTION_BEFORE,
        EMPTY_OSINFO,
        EMPTY_RANKING_INFO,
        EMPTY_OAUTH_STATE,
        EMPTY_STATE_TOKEN, 
        EMPTY_ACCESS_TOKEN, 
        EMPTY_BBS_CODE,
        EMPTY_ADMIN_AUTH,
        EMPTY_CELL_PHONE,
        EMPTY_CAMPAIGN_TURN,
        EMPTY_CAMPAIGN_TARGET_AMOUNT,
        EMPTY_BANK_INFO,
        EMPTY_BIRTH,
        EMPTY_CARD_INFO,
        EMPTY_CARD_OWNER,
        EMPTY_SUBJECT,
        EMPTY_THUMBNAIL,
        EMPTY_DESCRIPT,
        EMPTY_CONTENTS,
        EMPTY_ADDR,
        EMPTY_SSN,
        EMPTY_BUSINESS_NO,
        EMPTY_USER_PHONE,
        EMPTY_GENDER,
        EMPTY_AMOUNT,
        

        INVALID_PARAMETER,
        INVALID_TOKEN,
        INVALID_SNO,
        INVALID_COUNTRY,
        INVALID_LANGUAGE,
        INVALID_USER,
        INVALID_REGULAR_USER,
        INVALID_LINKHEART_AMOUNT,
        INVALID_USER_STATE,
        INVALID_EVENT_INFO,
        INVALID_EMAIL,
        INVALID_USER_BIRTH,
        INVALID_NICK,
        INVALID_HEADER_TOKEN,
        INVALID_HEADER_REFERER,
        INVALID_COOKIE_TOKEN,
        INVALID_USN,
        INVALID_CODE,
        INVALID_TYPE,
        INVALID_BANNER_INFO,
        INVALID_IMAGE_FILE,
        INVALID_VERSION,
        INVALID_EVENT_PERIOD,
        INVALID_PROMOTION,
        INVALID_BANK_INFO,
        INVALID_BIRTH,
        INVALID_CARD_INFO,
        INVALID_LINKHEART_CHILDREN,
        INVALID_OSINFO,
        INVALID_REWARD_CONDITIONS, 
        INVALID_KEY_OR_VERSION,
        INVALID_STAGE_ZONE,
        INVALID_TWITTER_SHARE_CODE, 
        INVALID_FACEBOOK_ACCESS_TOKEN,
        INVALID_OAUTH_STATE, 
        INVALID_BBS_CODE,
        INVALID_STATE_TOKEN,
        INVALID_ACCESS_TOKEN,
        INVALID_CELL_PHONE,
        INVALID_USER_ID,
        INVALID_MEMBER_DIV,
        INVALID_USER_PASSWD,
        INVALID_USER_PASSWD_CONFIRM,
        INVALID_CAMPAIGN_TURN,  
        INVALID_CAMPAIGN_TARGET_AMOUNT,  
        INVALID_CAMPAIGN,  
        INVALID_AMOUNT,
        INVALID_USER_PHONE,
        INVALID_SSN,
        INVALID_BUSINESS_NO,
        INVALID_VERIFICATE_CODE,
        INVALID_PG,
        INVALID_PLATFORM_CODE,

        
        MAX_UPLOADSIZE_EXCEEDED,
        MIMETYPE_DETECT_EXCEPTION,
        UPLOADFILESAVE_EXCEPTION,        
        SYSTEM_FAILURE,
        DUPLICATE_ID,
        AMOUNT_ZERO_EXCEPTION,
        NOT_VERIFICATE_USER,
        VERIFICATE_FAIL_BY_NO_SUPPORTER,
        VERIFICATE_FAIL_BY_MANY_SUPPORTER,
        VERIFICATE_FAIL_BY_ALREADY,
        VERIFICATE_FAIL_BY_PAYMENT_INFO,
        SMS_SEND_FAULT,
        HAVE_TO_CHANGE_PASSWD,
        EXCEL_PARSE_EXCEPTION,
        ALREADY_REGIST,
        ALREADY_PUBLISH,
        SOLD_OUT, 
        TIME_OVER,         
        HTTP_WEB_CALL_EXCEPTION,
        MAX_BBS_EXCEPTION,
        AES_ENCRYPT_EXCEPTION,
        AES_DECRYPT_EXCEPTION,
        PAYMENT_COMPLETE_STATUS,
        THUMBNAIL_CROP_FAIL,
        LINKHEART_STATUS_COMPLETE,
        LINKHEART_STATUS_PENDDING,
        MEMBER_LEAVE_STATE,
        
        ADMIN_NO_PERMISSION_EXCEPTION, INVALID_PRDT, INVALID_NUMBER, INVALID_DATE_FORMAT, DUPLICATE_DATE, EMPTY_BOOKER_BY_DATE, EMPTY_TICKET, EMPTY_USER, INVELID_PERCENT_RANGE, INVELID_SUM_ODDEVEN, EMPTY_DESTINYINFO, 
        LOTTO_CRAWLING_EXCEPTION
        
        ;
        
    }
    public static enum ResponseType{
		FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME("클라이언트 시간이 서버시간보다 오래 되었을때"), 
		FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER("오차 허용 시간 초과"), 
		FLOWTIME_HACK_COOKIE_TOKEN("어뷰징 체크 쿠키 없음"), 
		FLOWTIME_HACK_HEADER_TOKEN("어뷰징 체크 헤더 토큰 없음"),				
		MAX_BBS_EXCEPTION("게시판 카테고리가 최대"), 
		EMPTY_DESTINYINFO("회차 정보 없을때");		
		private String message;
		private ResponseType(String message){
			this.message = message;
		}
    	public String getMessage(){
    		return message;
    		
    	}
    }
    
    public static enum CustomType{
    	INSTAGRAM_ACCESS_TOKEN_EXCEPTION;
    	
    	
    	private Object message = null;
        
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
			if(null == this.message){
				return this.name();
			}
			if(this.message instanceof String){
				JSONObject obj = new JSONObject();
				obj.put(this.name(), (String)this.message);
				return obj.toString();
			}
			if(this.message instanceof HashMap){
				JSONObject source = new JSONObject((HashMap)this.message);
				JSONObject obj = new JSONObject();
				obj.put(this.name(), source);
				return obj.toString();
			}
			if(this.message instanceof JSONObject){
				JSONObject obj = new JSONObject();
				obj.put(this.name(), (JSONObject)this.message);
				return obj.toString();
			}
			
			return null;
		}
    }
    
    public static enum RedirectType{
    	UNACCEPTABLE_PAGE_EXCEPTION("/"),
    	LOGIN_EXCEPTION("/login/signin?rechk=1"),
    	REDIRECT_LOGIN_PAGE("/login/signin"),
    	NOT_FOUND_JSP_EXCEPTION("/exception/pageNotFound"), 
    	NOT_SUPPORT_LANGUAGE("/ko"),
    	
    	ADMIN_NOT_FOUND_JSP_EXCEPTION("/exception/adminPageNotFound"), 
    	ADMIN_NO_PERMISSION_EXCEPTION("/exception/adminNoPermission"), 
    	ADMIN_LOGIN_EXCEPTION("/lineplay/bfpadm/login"), 
    	ADMIN_PW_CHANGE_EXCEPTION("/admin/settings/manager_pw_modify"), 
    	ADMIN_WRONG_PARAMETER_EXCEPTION("/lineplay/bfpadm/index"), 
    	REDIRECT_KAKAO_STORY_SIGNIN("https://story.kakao.com/"), 
    	REDIRECT_LOGIN_PAGE_BY_NO_REQUIRED_PARAMETERS("/login/signin?expt=NO_REQUIRED_PARAMETERS"),
    	
    	;
    	
    	
    	private String redirect_uri;
    	private RedirectType(String redirect_uri){
    		this.redirect_uri = redirect_uri;
    	}
		public String getRedirect_uri() {
			return redirect_uri;
		}
    	
    	
    }
}
