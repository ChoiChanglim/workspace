package my.random.api.exception;

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
        EMPTY_DESTINYINFO,

        MaxUploadSizeExceeded,
        MimeTypeDetectException,
        INVALID_IMAGE_FILE,
        UploadFileSaveException,
        INVALID_VERSION,
        INVALID_EVENT_PERIOD,
        ALREADY_REWARD,
        SYSTEM_FAILURE,
        ALREADY_REGIST,

        HSP_RESPONSE_EXCEPTION, ALREADY_SHARE, IS_NOT_REGIST, IS_NOT_SHARED, INVALID_REWARD_CONDITIONS


    }


    public static enum ParameterWrongType{
        WRONG_PERIOD_UNTIL5("period until 5 days!"),
        WRONG_PERIOD_UNTIL6("period until 6 days!"),
        WRONG_PERIOD_UNTIL7("period until 7 days!"),
        WRONG_PERIOD_UNTIL10("period until 10 days!"),
        WRONG_PERIOD_UNTIL30("period until 30 days!"),

        ManyVersionsOfTheSameHost("There are many versions of the same host"),
        INVELID_SUM_ODDEVEN("ODD, EVEN SUM is not 100"),
        INVELID_PERCENT_RANGE("Percent range exception");

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

}
