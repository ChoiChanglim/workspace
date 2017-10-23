package com.nhn.common.exception;

public class ExceptionEnum {

    public static enum ResponseType{
        NOT_ALLOWED_USER,

        INVALID_PARAMETER,
        INVALID_TOKEN,
        INVALID_SNO,
        INVALID_COUNTRY,
        INVALID_LANGUAGE,
        INVALID_USER,
        INVALID_EVENT_INFO,
        INVALID_EMAIL,
        INVALID_NICK,
        INVALID_HEADER_TOKEN,
        INVALID_HEADER_REFERER,
        INVALID_COOKIE_TOKEN,
        INVALID_USN,
        INVALID_CODE,
        INVALID_TYPE,
        INVALID_BANNER_INFO,

        EMPTY_ATTACH_FILE,
        EMPTY_LANGUAGE,

        EMPTY_PARAMETER,
        EMPTY_TOKEN,
        EMPTY_SNO,
        EMPTY_COUNTRY,
        EMPTY_USER,
        EMPTY_EMAIL,
        EMPTY_NICK,
        EMPTY_HEADER_TOKEN,
        EMPTY_HEADER_REFERER,
        EMPTY_COOKIE_TOKEN,
        EMPTY_PROMOTION_BEFORE,
        EMPTY_OSINFO,
        EMPTY_RANKING_INFO,

        MaxUploadSizeExceeded,
        MimeTypeDetectException,
        INVALID_IMAGE_FILE,
        UploadFileSaveException,
        INVALID_VERSION,
        INVALID_EVENT_PERIOD,
        INVALID_PROMOTION,
        ALREADY_REWARD,
        SYSTEM_FAILURE,
        ALREADY_REGIST,
        ALREADY_CROSS_MISSION,
        ALREADY_PHONE,
        ALREADY_USN,
        INVALID_OSINFO,

        HSP_RESPONSE_EXCEPTION, ALREADY_SHARE, IS_NOT_REGIST, IS_NOT_SHARED, INVALID_REWARD_CONDITIONS, NOT_ENOUGH_SCORE, SOLD_OUT, TIME_OVER, Maintenance, INVALID_KEY_OR_VERSION, HTTP_WEB_CALL_EXCEPTION


    }


    public static enum ParameterWrongType{
        WRONG_PERIOD_UNTIL5("period until 5 days!"),
        WRONG_PERIOD_UNTIL6("period until 6 days!"),
        WRONG_PERIOD_UNTIL7("period until 7 days!"),
        WRONG_PERIOD_UNTIL10("period until 10 days!"),
        WRONG_PERIOD_UNTIL30("period until 30 days!"),

        ManyVersionsOfTheSameHost("There are many versions of the same host");

        private String message;

        private ParameterWrongType(String msg){
            this.message = msg;
        }

        public String getMessage(){

            return message;
        }
    }

    public static enum CouponType{
        COUPON_INVALID_PERIOD,
        COUPON_EMPTY_SERIAL,
        COUPON_INVALID_SERIAL,
        COUPON_INVALID_LENGTH,
        COUPON_ALREADY_USED,
        COUPON_INVALID_HGB_ITEMCODE

    }

    public static enum HackingType{
        FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME,
        FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER,
        FLOWTIME_HACK_COOKIE_TOKEN,
        FLOWTIME_HACK_HEADER_TOKEN

    }

    public static enum AngryAdminApiType{
        ANGRY_ADMIN_API_RESPONSE_EXCEPTION, ANGRY_ADMIN_API_REQUEST_URL_EXCEPTION

    }

    public static enum SNSShareType{
        INVALID_TWITTER_SHARE_CODE, INVALID_FACEBOOK_ACCESS_TOKEN


    }

}
