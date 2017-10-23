package my.random.api.exception;

import my.random.api.exception.ExceptionEnum.CouponType;
import my.random.api.exception.ExceptionEnum.HackingType;
import my.random.api.exception.ExceptionEnum.ParameterWrongType;
import my.random.api.exception.ExceptionEnum.ResponseType;


/**
 * Unchecked Exceptions
 * @author NHNEnt
 *
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public static final CustomException NOT_ALLOWED_USER = new CustomException(ResponseType.NOT_ALLOWED_USER);
    public static final CustomException ALREADY_REWARD = new CustomException(ResponseType.ALREADY_REWARD);
    public static final CustomException ALREADY_REGIST = new CustomException(ResponseType.ALREADY_REGIST);
    public static final CustomException ALREADY_SHARE = new CustomException(ResponseType.ALREADY_SHARE);
    public static final CustomException IS_NOT_REGIST = new CustomException(ResponseType.IS_NOT_REGIST);
    public static final CustomException IS_NOT_SHARED = new CustomException(ResponseType.IS_NOT_SHARED);
    public static final CustomException SYSTEM_FAILURE = new CustomException(ResponseType.SYSTEM_FAILURE);
    public static final CustomException EMPTY_PARAMETER = new CustomException(ResponseType.EMPTY_PARAMETER);
    public static final CustomException EMPTY_TOKEN = new CustomException(ResponseType.EMPTY_TOKEN);
    public static final CustomException EMPTY_SNO = new CustomException(ResponseType.EMPTY_SNO);
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
    public static final CustomException INVALID_SNO = new CustomException(ResponseType.INVALID_SNO);
    public static final CustomException INVALID_COUNTRY = new CustomException(ResponseType.INVALID_COUNTRY);
    public static final CustomException INVALID_LANGUAGE = new CustomException(ResponseType.INVALID_LANGUAGE);
    public static final CustomException INVALID_USER = new CustomException(ResponseType.INVALID_USER);
    public static final CustomException INVALID_EMAIL = new CustomException(ResponseType.INVALID_EMAIL);
    public static final CustomException INVALID_NICK = new CustomException(ResponseType.INVALID_NICK);
    public static final CustomException INVALID_VERSION = new CustomException(ResponseType.INVALID_VERSION);
    public static final CustomException INVALID_EVENT_INFO = new CustomException(ResponseType.INVALID_EVENT_INFO);
    public static final CustomException INVALID_EVENT_PERIOD = new CustomException(ResponseType.INVALID_EVENT_PERIOD);
    public static final CustomException INVALID_HEADER_TOKEN = new CustomException(ResponseType.INVALID_HEADER_TOKEN);
    public static final CustomException INVALID_HEADER_REFERER = new CustomException(ResponseType.INVALID_HEADER_REFERER);
    public static final CustomException INVALID_COOKIE_TOKEN = new CustomException(ResponseType.INVALID_COOKIE_TOKEN);
    public static final CustomException INVALID_REWARD_CONDITIONS = new CustomException(ResponseType.INVALID_REWARD_CONDITIONS);

    public static final CustomException WRONG_PERIOD_UNTIL6 = new CustomException(ParameterWrongType.WRONG_PERIOD_UNTIL6);

    public static final CustomException INVALID_IMAGE_FILE = new CustomException(ResponseType.INVALID_IMAGE_FILE);
    public static final CustomException MaxUploadSizeExceeded = new CustomException(ResponseType.MaxUploadSizeExceeded);
    public static final CustomException MimeTypeDetectException = new CustomException(ResponseType.MimeTypeDetectException);
    public static final CustomException UploadFileSaveException = new CustomException(ResponseType.UploadFileSaveException);

    public static final CustomException ManyVersionsOfTheSameHost = new CustomException(ParameterWrongType.ManyVersionsOfTheSameHost);


    //쿠폰관련
    public static final CustomException COUPON_EMPTY_SERIAL = new CustomException(CouponType.COUPON_EMPTY_SERIAL);
    public static final CustomException COUPON_INVALID_PERIOD = new CustomException(CouponType.COUPON_INVALID_PERIOD);
    public static final CustomException COUPON_INVALID_SERIAL = new CustomException(CouponType.COUPON_INVALID_SERIAL);
    public static final CustomException COUPON_INVALID_LENGTH = new CustomException(CouponType.COUPON_INVALID_LENGTH);
    public static final CustomException COUPON_ALREADY_USED = new CustomException(CouponType.COUPON_ALREADY_USED);
    public static final CustomException COUPON_INVALID_HGB_ITEMCODE = new CustomException(CouponType.COUPON_INVALID_HGB_ITEMCODE);

    //hsp 관련
    public static final CustomException HSP_RESPONSE_EXCEPTION = new CustomException(ResponseType.HSP_RESPONSE_EXCEPTION);

    //해킹의심관련
    public static final CustomException FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME = new CustomException(HackingType.FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME);
    public static final CustomException FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER = new CustomException(HackingType.FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER);
    public static final CustomException FLOWTIME_HACK_COOKIE_TOKEN = new CustomException(HackingType.FLOWTIME_HACK_COOKIE_TOKEN);
    public static final CustomException FLOWTIME_HACK_HEADER_TOKEN = new CustomException(HackingType.FLOWTIME_HACK_HEADER_TOKEN);

    public static final CustomException INVELID_SUM_ODDEVEN = new CustomException(ParameterWrongType.INVELID_SUM_ODDEVEN);

    public static final CustomException INVELID_PERCENT_RANGE = new CustomException(ParameterWrongType.INVELID_PERCENT_RANGE);

    public static final CustomException EMPTY_DESTINYINFO = new CustomException(ResponseType.EMPTY_DESTINYINFO);









    private CustomException(ResponseType responseType) {
        super(responseType.name());
    }


    public CustomException(ParameterWrongType parameterWrongType) {
        super(parameterWrongType.getMessage());
    }


    public CustomException(CouponType couponType) {
        super(couponType.name());
    }

    public CustomException(HackingType hackingType) {
        super(hackingType.name());
    }


    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }



}
