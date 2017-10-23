package com.nhn.common.exception;

import com.nhn.common.exception.ExceptionEnum.AngryAdminApiType;
import com.nhn.common.exception.ExceptionEnum.CouponType;
import com.nhn.common.exception.ExceptionEnum.HackingType;
import com.nhn.common.exception.ExceptionEnum.ParameterWrongType;
import com.nhn.common.exception.ExceptionEnum.ResponseType;
import com.nhn.common.exception.ExceptionEnum.SNSShareType;

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
    public static final CustomException EMPTY_PROMOTION_BEFORE = new CustomException(ResponseType.EMPTY_PROMOTION_BEFORE);
    public static final CustomException EMPTY_OSINFO = new CustomException(ResponseType.EMPTY_OSINFO);
    public static final CustomException EMPTY_RANKING_INFO = new CustomException(ResponseType.EMPTY_RANKING_INFO);

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
    public static final CustomException INVALID_BANNER_INFO = new CustomException(ResponseType.INVALID_BANNER_INFO);
    public static final CustomException INVALID_EVENT_PERIOD = new CustomException(ResponseType.INVALID_EVENT_PERIOD);
    public static final CustomException INVALID_HEADER_TOKEN = new CustomException(ResponseType.INVALID_HEADER_TOKEN);
    public static final CustomException INVALID_HEADER_REFERER = new CustomException(ResponseType.INVALID_HEADER_REFERER);
    public static final CustomException INVALID_COOKIE_TOKEN = new CustomException(ResponseType.INVALID_COOKIE_TOKEN);
    public static final CustomException INVALID_REWARD_CONDITIONS = new CustomException(ResponseType.INVALID_REWARD_CONDITIONS);
    public static final CustomException INVALID_PROMOTION = new CustomException(ResponseType.INVALID_PROMOTION);
    public static final CustomException INVALID_USN = new CustomException(ResponseType.INVALID_USN);
    public static final CustomException INVALID_CODE = new CustomException(ResponseType.INVALID_CODE);
    public static final CustomException INVALID_OSINFO = new CustomException(ResponseType.INVALID_OSINFO);
    public static final CustomException INVALID_TYPE = new CustomException(ResponseType.INVALID_TYPE);
    public static final CustomException ALREADY_CROSS_MISSION = new CustomException(ResponseType.ALREADY_CROSS_MISSION);
    public static final CustomException ALREADY_USN = new CustomException(ResponseType.ALREADY_USN);
    public static final CustomException ALREADY_PHONE = new CustomException(ResponseType.ALREADY_PHONE);
    public static final CustomException NOT_ENOUGH_SCORE = new CustomException(ResponseType.NOT_ENOUGH_SCORE);
    public static final CustomException SOLD_OUT = new CustomException(ResponseType.SOLD_OUT);
    public static final CustomException HTTP_WEB_CALL_EXCEPTION = new CustomException(ResponseType.HTTP_WEB_CALL_EXCEPTION);

    public static final CustomException WRONG_PERIOD_UNTIL6 = new CustomException(ParameterWrongType.WRONG_PERIOD_UNTIL6);

    public static final CustomException INVALID_IMAGE_FILE = new CustomException(ResponseType.INVALID_IMAGE_FILE);
    public static final CustomException MaxUploadSizeExceeded = new CustomException(ResponseType.MaxUploadSizeExceeded);
    public static final CustomException MimeTypeDetectException = new CustomException(ResponseType.MimeTypeDetectException);
    public static final CustomException UploadFileSaveException = new CustomException(ResponseType.UploadFileSaveException);

    public static final CustomException ManyVersionsOfTheSameHost = new CustomException(ParameterWrongType.ManyVersionsOfTheSameHost);

    public static final CustomException TIME_OVER = new CustomException(ResponseType.TIME_OVER);

    //쿠폰관련
    public static final CustomException COUPON_EMPTY_SERIAL = new CustomException(CouponType.COUPON_EMPTY_SERIAL);
    public static final CustomException COUPON_INVALID_PERIOD = new CustomException(CouponType.COUPON_INVALID_PERIOD);
    public static final CustomException COUPON_INVALID_SERIAL = new CustomException(CouponType.COUPON_INVALID_SERIAL);
    public static final CustomException COUPON_INVALID_LENGTH = new CustomException(CouponType.COUPON_INVALID_LENGTH);
    public static final CustomException COUPON_ALREADY_USED = new CustomException(CouponType.COUPON_ALREADY_USED);
    public static final CustomException COUPON_INVALID_HGB_ITEMCODE = new CustomException(CouponType.COUPON_INVALID_HGB_ITEMCODE);
    public static final CustomException INVALID_KEY_OR_VERSION = new CustomException(ResponseType.INVALID_KEY_OR_VERSION);
    //hsp 관련
    public static final CustomException HSP_RESPONSE_EXCEPTION = new CustomException(ResponseType.HSP_RESPONSE_EXCEPTION);

    //앵그리버드 admin api
    public static final CustomException ANGRY_ADMIN_API_RESPONSE_EXCEPTION = new CustomException(AngryAdminApiType.ANGRY_ADMIN_API_RESPONSE_EXCEPTION);
    public static final CustomException ANGRY_ADMIN_API_REQUEST_URL_EXCEPTION = new CustomException(AngryAdminApiType.ANGRY_ADMIN_API_REQUEST_URL_EXCEPTION);

    //해킹의심관련
    public static final CustomException FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME = new CustomException(HackingType.FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME);
    public static final CustomException FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER = new CustomException(HackingType.FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER);
    public static final CustomException FLOWTIME_HACK_COOKIE_TOKEN = new CustomException(HackingType.FLOWTIME_HACK_COOKIE_TOKEN);
    public static final CustomException FLOWTIME_HACK_HEADER_TOKEN = new CustomException(HackingType.FLOWTIME_HACK_HEADER_TOKEN);

    public static final CustomException Maintenance = new CustomException(ResponseType.Maintenance);

    public static final CustomException INVALID_TWITTER_SHARE_CODE = new CustomException(SNSShareType.INVALID_TWITTER_SHARE_CODE);
    public static final CustomException INVALID_FACEBOOK_ACCESS_TOKEN = new CustomException(SNSShareType.INVALID_FACEBOOK_ACCESS_TOKEN);
















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


    public CustomException(AngryAdminApiType angryAdminApiResponseException) {
        super(angryAdminApiResponseException.name());
    }

    public CustomException(SNSShareType snsShareType) {
        super(snsShareType.name());
    }


    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }



}
