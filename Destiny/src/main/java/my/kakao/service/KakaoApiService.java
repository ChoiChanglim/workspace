package my.kakao.service;

import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.kakao.constant.KakaoApiConstant;
import my.kakao.constant.KakaoApiConstant.KakaoApi;
import my.kakao.constant.KakaoApiConstant.KakaoResponseBody;
import my.random.api.exception.CustomException;
import my.random.api.util.HttpWebURLConnection;
import my.random.api.util.HttpWebURLResponse;

public class KakaoApiService {
	private static Logger LOG = LoggerFactory.getLogger(KakaoApiService.class);
	/**
     * 카카오 Accesstoken 획득
     */
    public static String getKakaoAccessToken(String code, String redirect_uri) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("grant_type", "authorization_code");
        param.put("client_id", KakaoApiConstant.KakaoAppkey);
        param.put("redirect_uri", redirect_uri);
        param.put("code", code);

        String dest = KakaoApi.GET_TOKEN.getOauthApi();
        String kakaoAccessToken = "";
        HttpWebURLConnection conn = new HttpWebURLConnection(dest, param);
        try {
            HttpWebURLResponse result =  conn.sendPost();
            if(200 == result.getResponseCode()){
                kakaoAccessToken = result.getResultJson().getString("access_token");
            }
        } catch (Exception e) {
            throw CustomException.HTTP_WEB_CALL_EXCEPTION;
        }
        return kakaoAccessToken;
    }

    /**
     * 카카오 스토리 유저 확인
     * @param kakaoAccessToken
     * @return
     */
    public static int kakaoStoryProfile(String kakaoAccessToken){
        HashMap<String, Object> param = new HashMap<String, Object>();
        HttpWebURLConnection conn = new HttpWebURLConnection(KakaoApi.STORY_PROFILE.getStoryApi(), param);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Bearer "+kakaoAccessToken);
        conn.setHeader(headerMap);
        try {
            HttpWebURLResponse result = conn.sendPost();
            return result.getResponseCode();
        } catch (Exception e) {
        	LOG.error("Kakao api exception!"+KakaoApi.STORY_PROFILE.getStoryApi()+", "+new JSONObject(headerMap)+", "+new JSONObject(param));
        }

        return KakaoResponseBody.Systemfail.getCode();
    }

    /**
     * 카카오 스토리 글 포스팅
     * @return
     */
    public static int kakaoStoryPosting(String kakaoAccessToken, String message){
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("content", message);

        HttpWebURLConnection conn = new HttpWebURLConnection(KakaoApi.STORY_POSTING_NOTE.getStoryApi(), param);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Bearer "+kakaoAccessToken);
        conn.setHeader(headerMap);
        try {
            HttpWebURLResponse result = conn.sendPost();
            System.err.println(new JSONObject(result.getResultMap()));
            return result.getResponseCode();
        } catch (Exception e) {
        	LOG.error("Kakao api exception!"+KakaoApi.STORY_POSTING_NOTE.getStoryApi()+", "+new JSONObject(headerMap)+", "+new JSONObject(param));
        }

        return KakaoResponseBody.Systemfail.getCode();


    }
    
   
    /**
     * 예시로 메시지보내는 이벤트 
     * @param kakaoAccessToken
     * @param reqmap
     */
	public static String EventByProcess(String kakaoAccessToken, HashMap<String, Object> reqmap) {
		String msg = (String)reqmap.get("msg");

        int profileResponseCode =kakaoStoryProfile(kakaoAccessToken);
        if(200 == profileResponseCode){
            int postResponseCode = kakaoStoryPosting(kakaoAccessToken, msg);
            System.err.println("postResponseCode:"+postResponseCode);
            if(200 == postResponseCode){
                //insertReply(userkey, "kakao", LanguageEnum.KR, msg);
            	return "success";
            }else{
                return KakaoResponseBody.GetKakaoResponseBody(profileResponseCode).name();
            }
        }else{
        	return KakaoResponseBody.GetKakaoResponseBody(profileResponseCode).name();
        }
		
	}
}
