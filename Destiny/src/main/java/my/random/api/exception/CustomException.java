package my.random.api.exception;

import my.random.api.exception.ExceptionEnum.CustomType;
import my.random.api.exception.ExceptionEnum.DefaultType;
import my.random.api.exception.ExceptionEnum.ResponseType;

/**
 * Unchecked Exceptions
 * @author Changlim
 *
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public static final CustomException NOT_ALLOWED_FILE_GROUP = new CustomException(DefaultType.NOT_ALLOWED_FILE_GROUP);
    public static final CustomException NOT_ALLOWED_USER = new CustomException(DefaultType.NOT_ALLOWED_USER);
    public static final CustomException NOT_RECEIPTED = new CustomException(DefaultType.NOT_RECEIPTED);
    public static final CustomException ALREADY_REGIST = new CustomException(DefaultType.ALREADY_REGIST);
    public static final CustomException ALREADY_PUBLISH = new CustomException(DefaultType.ALREADY_PUBLISH);
    public static final CustomException SYSTEM_FAILURE = new CustomException(DefaultType.SYSTEM_FAILURE);
    public static final CustomException DUPLICATE_ID = new CustomException(DefaultType.DUPLICATE_ID);
    public static final CustomException DUPLICATE_DATE = new CustomException(DefaultType.DUPLICATE_DATE);
    public static final CustomException AMOUNT_ZERO_EXCEPTION = new CustomException(DefaultType.AMOUNT_ZERO_EXCEPTION);
    public static final CustomException NOT_VERIFICATE_USER = new CustomException(DefaultType.NOT_VERIFICATE_USER);
    public static final CustomException NOT_INSTALL_VERSION = new CustomException(DefaultType.NOT_INSTALL_VERSION);
    
    public static final CustomException EMPTY_PARAMETER = new CustomException(DefaultType.EMPTY_PARAMETER);
    public static final CustomException EMPTY_TOKEN = new CustomException(DefaultType.EMPTY_TOKEN);
    public static final CustomException EMPTY_SNO = new CustomException(DefaultType.EMPTY_SNO);
    public static final CustomException EMPTY_COUNTRY = new CustomException(DefaultType.EMPTY_COUNTRY);
    public static final CustomException EMPTY_LANGUAGE = new CustomException(DefaultType.EMPTY_LANGUAGE);
    public static final CustomException EMPTY_USER_ID = new CustomException(DefaultType.EMPTY_USER_ID);
    public static final CustomException EMPTY_USER_NAME = new CustomException(DefaultType.EMPTY_USER_NAME);
    public static final CustomException EMPTY_USER_PW = new CustomException(DefaultType.EMPTY_USER_PW);
    public static final CustomException EMPTY_USER_GENDER = new CustomException(DefaultType.EMPTY_USER_GENDER);
    public static final CustomException EMPTY_USER_BIRTH = new CustomException(DefaultType.EMPTY_USER_BIRTH);
    public static final CustomException EMPTY_EMAIL = new CustomException(DefaultType.EMPTY_EMAIL);
    public static final CustomException EMPTY_GROUP_MANAGER = new CustomException(DefaultType.EMPTY_GROUP_MANAGER);
    public static final CustomException EMPTY_GROUP_NAME = new CustomException(DefaultType.EMPTY_GROUP_NAME);
    public static final CustomException EMPTY_NICK = new CustomException(DefaultType.EMPTY_NICK);
    public static final CustomException EMPTY_ATTACH_FILE = new CustomException(DefaultType.EMPTY_ATTACH_FILE);
    public static final CustomException EMPTY_SRC = new CustomException(DefaultType.EMPTY_SRC);
    public static final CustomException EMPTY_HEADER_TOKEN = new CustomException(DefaultType.EMPTY_HEADER_TOKEN);
    public static final CustomException EMPTY_HEADER_REFERER = new CustomException(DefaultType.EMPTY_HEADER_REFERER);
    public static final CustomException EMPTY_COOKIE_TOKEN = new CustomException(DefaultType.EMPTY_COOKIE_TOKEN);
    public static final CustomException EMPTY_PROMOTION_BEFORE = new CustomException(DefaultType.EMPTY_PROMOTION_BEFORE);
    public static final CustomException EMPTY_OSINFO = new CustomException(DefaultType.EMPTY_OSINFO);
    public static final CustomException EMPTY_RANKING_INFO = new CustomException(DefaultType.EMPTY_RANKING_INFO);
    public static final CustomException EMPTY_OAUTH_STATE = new CustomException(DefaultType.EMPTY_OAUTH_STATE);
    public static final CustomException EMPTY_STATE_TOKEN = new CustomException(DefaultType.EMPTY_STATE_TOKEN);	
    public static final CustomException EMPTY_ACCESS_TOKEN = new CustomException(DefaultType.EMPTY_ACCESS_TOKEN);
    public static final CustomException EMPTY_BBS_CODE = new CustomException(DefaultType.EMPTY_BBS_CODE);
    public static final CustomException EMPTY_ADMIN_AUTH = new CustomException(DefaultType.EMPTY_ADMIN_AUTH);
    public static final CustomException EMPTY_CELL_PHONE = new CustomException(DefaultType.EMPTY_CELL_PHONE);
    public static final CustomException EMPTY_CAMPAIGN_TURN = new CustomException(DefaultType.EMPTY_CAMPAIGN_TURN);
    public static final CustomException EMPTY_CAMPAIGN_TARGET_AMOUNT = new CustomException(DefaultType.EMPTY_CAMPAIGN_TARGET_AMOUNT);
    public static final CustomException EMPTY_BANK_INFO = new CustomException(DefaultType.EMPTY_BANK_INFO);
    public static final CustomException EMPTY_BIRTH = new CustomException(DefaultType.EMPTY_BIRTH);
    public static final CustomException EMPTY_CARD_INFO = new CustomException(DefaultType.EMPTY_CARD_INFO);
    public static final CustomException EMPTY_CARD_OWNER = new CustomException(DefaultType.EMPTY_CARD_OWNER);
    public static final CustomException EMPTY_SUBJECT = new CustomException(DefaultType.EMPTY_SUBJECT);
    public static final CustomException EMPTY_THUMBNAIL = new CustomException(DefaultType.EMPTY_THUMBNAIL);
    public static final CustomException EMPTY_DESCRIPT = new CustomException(DefaultType.EMPTY_DESCRIPT);
    public static final CustomException EMPTY_CONTENTS = new CustomException(DefaultType.EMPTY_CONTENTS);
    public static final CustomException EMPTY_ADDR = new CustomException(DefaultType.EMPTY_ADDR);
    public static final CustomException EMPTY_SSN = new CustomException(DefaultType.EMPTY_SSN);
    public static final CustomException EMPTY_BUSINESS_NO = new CustomException(DefaultType.EMPTY_BUSINESS_NO);
    public static final CustomException EMPTY_USER_PHONE = new CustomException(DefaultType.EMPTY_USER_PHONE);
    public static final CustomException EMPTY_GENDER = new CustomException(DefaultType.EMPTY_GENDER);
    public static final CustomException EMPTY_AMOUNT = new CustomException(DefaultType.EMPTY_AMOUNT);
    public static final CustomException EMPTY_BOOKER_BY_DATE = new CustomException(DefaultType.EMPTY_BOOKER_BY_DATE);
    public static final CustomException EMPTY_TICKET =  new CustomException(DefaultType.EMPTY_TICKET);
    public static final CustomException EMPTY_USER = new CustomException(DefaultType.EMPTY_USER);
    public static final CustomException EMPTY_DESTINYINFO = new CustomException(DefaultType.EMPTY_DESTINYINFO);
    
    public static final CustomException INVALID_PARAMETER = new CustomException(DefaultType.INVALID_PARAMETER);
    public static final CustomException INVALID_TOKEN = new CustomException(DefaultType.INVALID_TOKEN);
    public static final CustomException INVALID_SNO = new CustomException(DefaultType.INVALID_SNO);
    public static final CustomException INVALID_COUNTRY = new CustomException(DefaultType.INVALID_COUNTRY);
    public static final CustomException INVALID_LANGUAGE = new CustomException(DefaultType.INVALID_LANGUAGE);
    public static final CustomException INVALID_USER = new CustomException(DefaultType.INVALID_USER);
    public static final CustomException INVALID_REGULAR_USER = new CustomException(DefaultType.INVALID_REGULAR_USER);
    public static final CustomException INVALID_LINKHEART_AMOUNT = new CustomException(DefaultType.INVALID_LINKHEART_AMOUNT);
    public static final CustomException INVALID_USER_STATE = new CustomException(DefaultType.INVALID_USER_STATE);
    public static final CustomException INVALID_EMAIL = new CustomException(DefaultType.INVALID_EMAIL);
    public static final CustomException INVALID_USER_BIRTH = new CustomException(DefaultType.INVALID_USER_BIRTH);
    public static final CustomException INVALID_NICK = new CustomException(DefaultType.INVALID_NICK);
    public static final CustomException INVALID_VERSION = new CustomException(DefaultType.INVALID_VERSION);
    public static final CustomException INVALID_EVENT_INFO = new CustomException(DefaultType.INVALID_EVENT_INFO);
    public static final CustomException INVALID_BANNER_INFO = new CustomException(DefaultType.INVALID_BANNER_INFO);
    public static final CustomException INVALID_EVENT_PERIOD = new CustomException(DefaultType.INVALID_EVENT_PERIOD);
    public static final CustomException INVALID_HEADER_TOKEN = new CustomException(DefaultType.INVALID_HEADER_TOKEN);
    public static final CustomException INVALID_HEADER_REFERER = new CustomException(DefaultType.INVALID_HEADER_REFERER);
    public static final CustomException INVALID_COOKIE_TOKEN = new CustomException(DefaultType.INVALID_COOKIE_TOKEN);
    public static final CustomException INVALID_REWARD_CONDITIONS = new CustomException(DefaultType.INVALID_REWARD_CONDITIONS);
    public static final CustomException INVALID_PROMOTION = new CustomException(DefaultType.INVALID_PROMOTION);
    public static final CustomException INVALID_BANK_INFO = new CustomException(DefaultType.INVALID_BANK_INFO);
    public static final CustomException INVALID_BIRTH = new CustomException(DefaultType.INVALID_BIRTH);
    public static final CustomException INVALID_CARD_INFO = new CustomException(DefaultType.INVALID_CARD_INFO);
    public static final CustomException INVALID_LINKHEART_CHILDREN = new CustomException(DefaultType.INVALID_LINKHEART_CHILDREN);
    public static final CustomException INVALID_USN = new CustomException(DefaultType.INVALID_USN);
    public static final CustomException INVALID_CODE = new CustomException(DefaultType.INVALID_CODE);
    public static final CustomException INVALID_OSINFO = new CustomException(DefaultType.INVALID_OSINFO);
    public static final CustomException INVALID_STAGE_ZONE = new CustomException(DefaultType.INVALID_STAGE_ZONE);
    public static final CustomException INVALID_TYPE = new CustomException(DefaultType.INVALID_TYPE);
    public static final CustomException INVALID_IMAGE_FILE = new CustomException(DefaultType.INVALID_IMAGE_FILE);
    public static final CustomException INVALID_OAUTH_STATE = new CustomException(DefaultType.INVALID_OAUTH_STATE);    
    public static final CustomException INVALID_TWITTER_SHARE_CODE = new CustomException(DefaultType.INVALID_TWITTER_SHARE_CODE);    
    public static final CustomException INVALID_FACEBOOK_ACCESS_TOKEN = new CustomException(DefaultType.INVALID_FACEBOOK_ACCESS_TOKEN);	
    public static final CustomException INVALID_BBS_CODE = new CustomException(DefaultType.INVALID_BBS_CODE);	
    public static final CustomException INVALID_STATE_TOKEN =  new CustomException(DefaultType.INVALID_STATE_TOKEN);	
    public static final CustomException INVALID_ACCESS_TOKEN =  new CustomException(DefaultType.INVALID_ACCESS_TOKEN);	
    public static final CustomException INVALID_CELL_PHONE =  new CustomException(DefaultType.INVALID_CELL_PHONE);	
    public static final CustomException INVALID_USER_ID =  new CustomException(DefaultType.INVALID_USER_ID);	
    public static final CustomException INVALID_USER_PASSWD =  new CustomException(DefaultType.INVALID_USER_PASSWD);	
    public static final CustomException INVALID_USER_PASSWD_CONFIRM =  new CustomException(DefaultType.INVALID_USER_PASSWD_CONFIRM);	
    public static final CustomException INVALID_MEMBER_DIV =  new CustomException(DefaultType.INVALID_MEMBER_DIV);	
    public static final CustomException INVALID_CAMPAIGN_TURN = new CustomException(DefaultType.INVALID_CAMPAIGN_TURN);
    public static final CustomException INVALID_CAMPAIGN_TARGET_AMOUNT = new CustomException(DefaultType.INVALID_CAMPAIGN_TARGET_AMOUNT);
    public static final CustomException INVALID_CAMPAIGN = new CustomException(DefaultType.INVALID_CAMPAIGN);
    public static final CustomException INVALID_AMOUNT = new CustomException(DefaultType.INVALID_AMOUNT);
    public static final CustomException INVALID_USER_PHONE = new CustomException(DefaultType.INVALID_USER_PHONE);
    public static final CustomException INVALID_SSN = new CustomException(DefaultType.INVALID_SSN);
    public static final CustomException INVALID_BUSINESS_NO = new CustomException(DefaultType.INVALID_BUSINESS_NO);
    public static final CustomException INVALID_VERIFICATE_CODE = new CustomException(DefaultType.INVALID_VERIFICATE_CODE);
    public static final CustomException INVALID_PG = new CustomException(DefaultType.INVALID_PG);
    public static final CustomException INVALID_PRDT = new CustomException(DefaultType.INVALID_PRDT);
    public static final CustomException INVALID_PLATFORM_CODE = new CustomException(DefaultType.INVALID_PLATFORM_CODE);
    public static final CustomException INVALID_NUMBER = new CustomException(DefaultType.INVALID_NUMBER);
    public static final CustomException INVALID_DATE_FORMAT = new CustomException(DefaultType.INVALID_DATE_FORMAT);
    
    public static final CustomException HTTP_WEB_CALL_EXCEPTION = new CustomException(DefaultType.HTTP_WEB_CALL_EXCEPTION);    
    public static final CustomException MAX_UPLOADSIZE_EXCEEDED = new CustomException(DefaultType.MAX_UPLOADSIZE_EXCEEDED);
    public static final CustomException MIMETYPE_DETECT_EXCEPTION = new CustomException(DefaultType.MIMETYPE_DETECT_EXCEPTION);
    public static final CustomException UPLOADFILESAVE_EXCEPTION = new CustomException(DefaultType.UPLOADFILESAVE_EXCEPTION);	
    public static final CustomException TIME_OVER = new CustomException(DefaultType.TIME_OVER);
    public static final CustomException AES_ENCRYPT_EXCEPTION = new CustomException(DefaultType.AES_ENCRYPT_EXCEPTION);
    public static final CustomException AES_DECRYPT_EXCEPTION = new CustomException(DefaultType.AES_DECRYPT_EXCEPTION);
    public static final CustomException PAYMENT_COMPLETE_STATUS = new CustomException(DefaultType.PAYMENT_COMPLETE_STATUS);
    public static final CustomException THUMBNAIL_CROP_FAIL = new CustomException(DefaultType.THUMBNAIL_CROP_FAIL);
    public static final CustomException INVELID_PERCENT_RANGE = new CustomException(DefaultType.INVELID_PERCENT_RANGE);
    public static final CustomException INVELID_SUM_ODDEVEN = new CustomException(DefaultType.INVELID_SUM_ODDEVEN);
    public static final CustomException LOTTO_CRAWLING_EXCEPTION = new CustomException(DefaultType.LOTTO_CRAWLING_EXCEPTION);
    
    //어드민 관련
  	public static final CustomException ADMIN_NO_PERMISSION_EXCEPTION = new CustomException(DefaultType.ADMIN_NO_PERMISSION_EXCEPTION);
  	

    //메시지가 필요한
    public static final CustomException FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME = new CustomException(ResponseType.FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME);
    public static final CustomException FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER = new CustomException(ResponseType.FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER);
    public static final CustomException FLOWTIME_HACK_COOKIE_TOKEN = new CustomException(ResponseType.FLOWTIME_HACK_COOKIE_TOKEN);
    public static final CustomException FLOWTIME_HACK_HEADER_TOKEN = new CustomException(ResponseType.FLOWTIME_HACK_HEADER_TOKEN);    
	public static final CustomException MAX_BBS_EXCEPTION = new CustomException(ResponseType.MAX_BBS_EXCEPTION);
	
	//메시지 세팅이 필요한
	public static final CustomException INSTAGRAM_ACCESS_TOKEN_EXCEPTION = new CustomException(CustomType.INSTAGRAM_ACCESS_TOKEN_EXCEPTION);




    

    

    
    

    

    

    

	

	

	

	


	

	

	

	

	

	
	
	
	

	

	


	
	
	
	
	
	/**
	 * 정의형 Exception
	 * @param defaultType
	 */
    private CustomException(DefaultType defaultType) {    	
    	super(defaultType.name());
    }
    
    /**
     * 메시지가 필요한 Exception
     * @param responseType
     */
    private CustomException(ResponseType responseType) {    	
    	super(responseType.getMessage());
    }
    
    /**
     * 메시지 세팅이 필요한 Exception
     */
    public CustomException(CustomType customType){
    	super(customType.getMessage());
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
    
   
}
