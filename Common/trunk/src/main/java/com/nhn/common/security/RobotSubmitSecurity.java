package com.nhn.common.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhn.common.exception.CustomException;
import com.nhn.common.util.AesCipher;
import com.nhn.common.util.RequestUtil;
import com.nhn.common.util.StringUtil;

public class RobotSubmitSecurity {
    private static Logger LOG = LoggerFactory.getLogger(RobotSubmitSecurity.class);

    private static final String SecretKey="studio629";                              //내부 비밀 키
    public static final String ClientAuthorizationTokenHeaderKey = "authorization"; //클라이언트 헤더의 보안 키값
    public static final String EncryptAuthorizationTokenCookieKey = "actn";         //쿠키로 구을 키값

    /**
     * 체크포인트 초기화
     * @param response
     * @param secretKey
     * @return
     */
    public static String CheckPointInitialization(HttpServletResponse response) {
        String checkPoint = String.valueOf(System.currentTimeMillis());
        AesCipher cipher = new AesCipher(SecretKey);    //내부 비밀키로 AesCipher 생성
        String encryptCheckPoint = cipher.encryptString(checkPoint);
        RequestUtil.setCookie(response, EncryptAuthorizationTokenCookieKey, encryptCheckPoint, 1);  //체크포인트값을 암호화 하여 쿠키에저장
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.valueOf(checkPoint));
        //LOG.info("## CheckPointInitialization: "+ "CheckPoint:"+checkPoint + ", "+ c.getTime()+ ", enc:" + cipher.encryptString(checkPoint));
        return encryptCheckPoint;
    }

    /**
     * 체크포인트 시간은?
     * @return
     */
    public static long GetCheckPointTimeStamp(HttpServletRequest request){
        AesCipher cipher  = new AesCipher(SecretKey);
        String encryptAuthorizationTokenCookieValue = cipher.decryptString(RequestUtil.getCookieMap(request).get(EncryptAuthorizationTokenCookieKey));
        if(StringUtil.isDigit(encryptAuthorizationTokenCookieValue) == false){
            LOG.warn(CustomException.FLOWTIME_HACK_COOKIE_TOKEN.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }
        return Long.valueOf(encryptAuthorizationTokenCookieValue);
    }


    /**
     * 로봇인지 판별
     * 1. 레퍼러 체크
     * 2. 암호화쿠키 및 헤더정보 체크
     * 3. 서버의 현재시간과, 서버에서 예측한 클라이언트시간의 오차 확인 체크
     * @param request
     * @param refererHost
     * @return
     */
    public static boolean isRobotClient(HttpServletRequest request, long AllowTimeRange, String refererHost){
        HashMap<String, String> headerMap = RequestUtil.HeaderToMap(request);
        //레퍼러 체크
        if(headerMap.containsKey("referer") == false){
            LOG.warn(CustomException.EMPTY_HEADER_REFERER.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }
        if(headerMap.get("referer").contains(refererHost) == false){
            LOG.warn(CustomException.INVALID_HEADER_REFERER.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }

        //암호화 쿠키 있는지 확인
        if(headerMap.containsKey(ClientAuthorizationTokenHeaderKey) == false){
            LOG.warn(CustomException.EMPTY_HEADER_TOKEN.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }
        if(RequestUtil.getCookieMap(request).containsKey(EncryptAuthorizationTokenCookieKey) == false){
            LOG.warn(CustomException.EMPTY_COOKIE_TOKEN.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }


        AesCipher cipher  = new AesCipher(SecretKey);
        String encryptAuthorizationTokenCookieValue = cipher.decryptString(RequestUtil.getCookieMap(request).get(EncryptAuthorizationTokenCookieKey));
        if(StringUtil.isDigit(encryptAuthorizationTokenCookieValue) == false){
            LOG.warn(CustomException.FLOWTIME_HACK_COOKIE_TOKEN.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }

        if(StringUtil.isDigit(headerMap.get(ClientAuthorizationTokenHeaderKey)) == false){
            LOG.warn(CustomException.FLOWTIME_HACK_HEADER_TOKEN.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }
        long checkPoint = Long.valueOf(encryptAuthorizationTokenCookieValue);
        long clientFlowTime = Long.valueOf(headerMap.get(ClientAuthorizationTokenHeaderKey));



        long clientPredictionTime = checkPoint + clientFlowTime;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(clientPredictionTime);
        long nowServerTime = System.currentTimeMillis();
        //LOG.info("## isRobotClient: "+ "checkPoint:"+checkPoint +", clientPredictionTime:"+clientPredictionTime+", serverTime:"+ nowServerTime + ", "+c.getTime());

        /**
         * 서버에서 예측한 클라시간이 서버 현재 시간보다 크면 안됨
         * 요청/응답 네트워크 시간은 반영이안되어진 상태
         * 무조건 서버에서 예측한 클라이언트 시간이 더 큼
         */
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(nowServerTime);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        LOG.debug("AllowTimeRange:"+AllowTimeRange+", clientPredictionTime:"+clientPredictionTime+", nowServerTime:"+nowServerTime);
        if(clientPredictionTime > nowServerTime){
            LOG.warn(CustomException.FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME.getMessage()+": "+request.getRemoteAddr());
            LOG.warn("## "+ "checkPoint:"+checkPoint +", clientFlowTime:+"+clientFlowTime+", clientPredictionTime:"+clientPredictionTime+"("+sdf.format(c.getTime())+"), serverTime:"+ nowServerTime +"("+sdf.format(c2.getTime())+"), result:"+(nowServerTime - clientPredictionTime));
            throw CustomException.NOT_ALLOWED_USER;
        }
        if((nowServerTime - clientPredictionTime) > AllowTimeRange){
            LOG.warn(CustomException.FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER.getMessage()+": "+request.getRemoteAddr());
            LOG.warn("## "+ "checkPoint:"+checkPoint +", clientFlowTime:+"+clientFlowTime+", clientPredictionTime:"+clientPredictionTime+"("+sdf.format(c.getTime())+"), serverTime:"+ nowServerTime +"("+sdf.format(c2.getTime())+"), result:"+(nowServerTime - clientPredictionTime));
            throw CustomException.NOT_ALLOWED_USER;
        }

        return false;
    }

    /**
     * 레퍼러 체크 제외
     * @param request
     * @return
     */
    public static boolean isRobotClient(HttpServletRequest request, long AllowTimeRange){
        HashMap<String, String> headerMap = RequestUtil.HeaderToMap(request);
        //암호화 쿠키 있는지 확인
        if(headerMap.containsKey(ClientAuthorizationTokenHeaderKey) == false){
            LOG.warn(CustomException.EMPTY_HEADER_TOKEN.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }
        if(RequestUtil.getCookieMap(request).containsKey(EncryptAuthorizationTokenCookieKey) == false){
            LOG.warn(CustomException.EMPTY_COOKIE_TOKEN.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }


        AesCipher cipher  = new AesCipher(SecretKey);
        String encryptAuthorizationTokenCookieValue = cipher.decryptString(RequestUtil.getCookieMap(request).get(EncryptAuthorizationTokenCookieKey));
        if(StringUtil.isDigit(encryptAuthorizationTokenCookieValue) == false){
            LOG.warn(CustomException.FLOWTIME_HACK_COOKIE_TOKEN.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }

        if(StringUtil.isDigit(headerMap.get(ClientAuthorizationTokenHeaderKey)) == false){
            LOG.warn(CustomException.FLOWTIME_HACK_HEADER_TOKEN.getMessage()+": "+request.getRemoteAddr());
            throw CustomException.NOT_ALLOWED_USER;
        }
        long checkPoint = Long.valueOf(encryptAuthorizationTokenCookieValue);
        long clientFlowTime = Long.valueOf(headerMap.get(ClientAuthorizationTokenHeaderKey));



        long clientPredictionTime = checkPoint + clientFlowTime;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(clientPredictionTime);
        long nowServerTime = System.currentTimeMillis();
        //LOG.info("## isRobotClient: "+ "checkPoint:"+checkPoint +", clientPredictionTime:"+clientPredictionTime+", serverTime:"+ nowServerTime + ", "+c.getTime());

        /**
         * 서버에서 예측한 클라시간이 서버 현재 시간보다 크면 안됨
         * 요청/응답 네트워크 시간은 반영이안되어진 상태
         * 무조건 서버에서 예측한 클라이언트 시간이 더 큼
         */
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(nowServerTime);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        if(clientPredictionTime > nowServerTime){
            LOG.warn(CustomException.FLOWTIME_HACK_BY_CLIENTPREDICTIONTIME_OVER_THE_SERVERTIME.getMessage()+": "+request.getRemoteAddr());
            LOG.warn("## "+ "checkPoint:"+checkPoint +", clientFlowTime:+"+clientFlowTime+", clientPredictionTime:"+clientPredictionTime+"("+sdf.format(c.getTime())+"), serverTime:"+ nowServerTime +"("+sdf.format(c2.getTime())+"), result:"+(nowServerTime - clientPredictionTime));
            throw CustomException.NOT_ALLOWED_USER;
        }
        if((nowServerTime - clientPredictionTime) > AllowTimeRange){
            LOG.warn(CustomException.FLOWTIME_HACK_BY_ALLOWTIMERANGE_OVER.getMessage()+": "+request.getRemoteAddr());
            LOG.warn("## "+ "checkPoint:"+checkPoint +", clientFlowTime:+"+clientFlowTime+", clientPredictionTime:"+clientPredictionTime+"("+sdf.format(c.getTime())+"), serverTime:"+ nowServerTime +"("+sdf.format(c2.getTime())+"), result:"+(nowServerTime - clientPredictionTime));
            throw CustomException.NOT_ALLOWED_USER;
        }

        return false;
    }


}
