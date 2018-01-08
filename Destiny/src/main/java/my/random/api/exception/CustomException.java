package my.random.api.exception;

import my.random.api.exception.ExceptionEnum.ResponseType;


/**
 * Unchecked Exceptions
 * @author NHNEnt
 *
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public static final CustomException NOT_ALLOWED_USER = new CustomException(ResponseType.NOT_ALLOWED_USER);
    public static final CustomException SYSTEM_FAILURE = new CustomException(ResponseType.SYSTEM_FAILURE);
    public static final CustomException EMPTY_PARAMETER = new CustomException(ResponseType.EMPTY_PARAMETER);
    public static final CustomException EMPTY_TOKEN = new CustomException(ResponseType.EMPTY_TOKEN);
    public static final CustomException EMPTY_COUNTRY = new CustomException(ResponseType.EMPTY_COUNTRY);
    public static final CustomException EMPTY_LANGUAGE = new CustomException(ResponseType.EMPTY_LANGUAGE);
    public static final CustomException EMPTY_USER = new CustomException(ResponseType.EMPTY_USER);
    public static final CustomException EMPTY_EMAIL = new CustomException(ResponseType.EMPTY_EMAIL);
    public static final CustomException EMPTY_NICK = new CustomException(ResponseType.EMPTY_NICK);
    public static final CustomException EMPTY_ATTACH_FILE = new CustomException(ResponseType.EMPTY_ATTACH_FILE);
    public static final CustomException EMPTY_HEADER_TOKEN = new CustomException(ResponseType.EMPTY_HEADER_TOKEN);
    public static final CustomException EMPTY_HEADER_REFERER = new CustomException(ResponseType.EMPTY_HEADER_REFERER);
    public static final CustomException EMPTY_COOKIE_TOKEN = new CustomException(ResponseType.EMPTY_COOKIE_TOKEN);

    public static final CustomException INVALID_PARAMETER = new CustomException(ResponseType.INVALID_PARAMETER);
    public static final CustomException INVALID_TOKEN = new CustomException(ResponseType.INVALID_TOKEN);
    public static final CustomException INVALID_COUNTRY = new CustomException(ResponseType.INVALID_COUNTRY);
    public static final CustomException INVALID_LANGUAGE = new CustomException(ResponseType.INVALID_LANGUAGE);
    public static final CustomException INVALID_USER = new CustomException(ResponseType.INVALID_USER);
    public static final CustomException INVALID_EMAIL = new CustomException(ResponseType.INVALID_EMAIL);
    public static final CustomException INVALID_NICK = new CustomException(ResponseType.INVALID_NICK);
    public static final CustomException INVALID_VERSION = new CustomException(ResponseType.INVALID_VERSION);
    public static final CustomException INVALID_EVENT_INFO = new CustomException(ResponseType.INVALID_EVENT_INFO);
    public static final CustomException INVALID_HEADER_TOKEN = new CustomException(ResponseType.INVALID_HEADER_TOKEN);
    public static final CustomException INVALID_HEADER_REFERER = new CustomException(ResponseType.INVALID_HEADER_REFERER);
    public static final CustomException INVALID_COOKIE_TOKEN = new CustomException(ResponseType.INVALID_COOKIE_TOKEN);
    public static final CustomException INVELID_PERCENT_RANGE = new CustomException(ResponseType.INVELID_PERCENT_RANGE);
    public static final CustomException INVELID_SUM_ODDEVEN = new CustomException(ResponseType.INVELID_SUM_ODDEVEN);
    public static final CustomException EMPTY_DESTINYINFO = new CustomException(ResponseType.EMPTY_DESTINYINFO);

    //인스타 API통신
    public static final CustomException INSTAGRAM_API_EXCEPTION = new CustomException(ResponseType.INSTAGRAM_API_EXCEPTION);
    public static final CustomException INSTAGRAM_AUTH_CODE_EXCEPTION = new CustomException(ResponseType.INSTAGRAM_AUTH_CODE_EXCEPTION);
    public static final CustomException INSTAGRAM_ACCESS_TOKEN_EXCEPTION = new CustomException(ResponseType.INSTAGRAM_ACCESS_TOKEN_EXCEPTION);
    
    //크롤링
    public static final CustomException LOTTO_CRAWLING_EXCEPTION = new CustomException(ResponseType.LOTTO_CRAWLING_EXCEPTION);







    private CustomException(ResponseType responseType) {
    	super(responseType.getMessage());
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
    public CustomException(String message) {
        super(message);
    }

}
