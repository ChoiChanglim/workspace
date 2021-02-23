package my.random.service;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;

import my.random.api.constant.SupportKakao.PlusFriendsStatusEnum;
import my.random.api.exception.CustomException;
import my.random.api.exception.RedirectException;
import my.random.api.util.HttpWebURLConnection;
import my.random.api.util.HttpWebURLResponse;
import my.random.api.util.JsonUtil;
import my.random.api.util.RequestUtil;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KakaoApiService {
	private static Logger LOG = LoggerFactory.getLogger(KakaoApiService.class);
	public static String STATE_COOKIE_NAME = "kstat";
	public static final String KakaoAccessTokenCookieName = "kact";
	public static final String KakaoRefreshTokenCookieName = "krt";
    public static final String KakaoQstrAtOauthCookieName = "koqs";
    public static final String KakaoAppkey = "1c609e50b97b24c94790abc584e3b6c3";
    public static final String KakaoAppSecret = "lYp8KftQoWzxqjFAWoKtL3UXBdMfWIA7";
    public static final String KakaoCallbackUri = "/kakao/oauth";    
    public static String REDIRECT_URI_LOGIN="/api/kakao/redirect_login"; 
    public static final String KakaoServerCipherKey = "XBOXONEXSONYPLAYSTATION4";
    public static final String KakaoChannelID = "_gXmsC";
    public static final String KakaoAdminKey = "5b8b58b7ebe6e569203ca14cddd6926f";
    
    public enum KakaoHost{
    	OAUTH("https://kauth.kakao.com"),
    	KAPI("https://kapi.kakao.com"),
    	DAPI("https://dapi.kakao.com");
    	
    	private String host;
    	private KakaoHost(String host){
    		this.host = host;
    	}
		public String getHost() {
			return host;
		}
    	
    	
    }
 
    public enum KakaoApi {
        OAUTH_GET_CODE("/oauth/authorize"),
        OAUTH_GET_TOKEN("/oauth/token"),
        KAPI_ACCESS_TOKEN_INFO("/v1/user/access_token_info"),
        KAPI_KAKAO_USER_PROFILE("/v2/user/me"),
        KAPI_STORY_PROFILE("/v1/api/story/profile"),
        KAPI_STORY_POSTING_NOTE("/v1/api/story/post/note"),
        KAPI_TALK_SENDME("/v2/api/talk/memo/default/send"),
        KAPI_PLUSFRIENDS("/v1/api/talk/plusfriends"),
        KAPI_TARGET_USER_FILE("/v1/talkchannel/create/target_user_file"),
        
        ;
        private String uri;
        private KakaoApi(String uri){
            this.uri = uri;
        }
		public String getUri() {
			return uri;
		}
        public String getURL(){
        	for(int i=0;i<KakaoHost.values().length;i++){
        		KakaoHost khost = KakaoHost.values()[i];
        		String[] arr = this.name().split("[_]");
        		if(khost.name().equals(arr[0])){
        			return khost.getHost() + this.getUri();
        		}
        	}
        	return null;
        }
        /**
		 * state 토큰 생성
		 * @return
		 */
		public static String GetgenerateState() {
		    SecureRandom random = new SecureRandom();
		    return new BigInteger(130, random).toString(32);
		}
     }

    public enum KakaoResponseBody {
        NotExistKakaoTalkUser(-501, "카카오톡 미가입 사용자가 카카오톡 API를 호출 하였을 경우"),
        NotExistKakaoStoryUser(-601, "카카오스토리 미가입 사용자가 카카오스토리 API를 호출 하였을 경우"),
        Unkown(-999, "알수없는 오류 개발자 가이드가서 확인해보시오"),
        Systemfail(-9999, "카카오 api 호출 실패");

        private int code;
        private String desc;
        private KakaoResponseBody(int code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public int getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }

        public static KakaoResponseBody GetKakaoResponseBody(int code){
            for(int i=0; i<KakaoResponseBody.values().length;i++){
                KakaoResponseBody o = KakaoResponseBody.values()[i];
                if(o.getCode() == code){
                    return o;
                }
            }
            return KakaoResponseBody.Unkown;
        }
    }
    
	/**
     * 카카오 Accesstoken 획득
     */
    public static HashMap<String, Object> getKakaoAccessToken(String code, String domain) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("grant_type", "authorization_code");
        param.put("client_id", KakaoAppkey);
        param.put("redirect_uri", domain+REDIRECT_URI_LOGIN);
        param.put("code", code);
        param.put("client_secret", KakaoAppSecret);

        String dest = KakaoApi.OAUTH_GET_TOKEN.getURL() + "?" + RequestUtil.getQstr(param);
        HttpWebURLConnection conn = new HttpWebURLConnection(dest, param);
        HttpWebURLResponse result =  conn.sendPostByParamTypeJson();
        if(200 == result.getResponseCode()){
        	return JsonUtil.JSONSourceToHashMap(result.getResult());
        }
    	LOG.error("Kakao Access Token 생성 과정 중  responseCode:"+result.getResponseCode());
    	throw CustomException.HTTP_WEB_CALL_EXCEPTION;
    }
    
    /**
     * 카카오 Accesstoken refresh
     * @param refreshToken
     * @return
     */
    public static HashMap<String, Object> GetRefreshKakaoAccessToken(String refreshToken) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("grant_type", "refresh_token");
        param.put("client_id", KakaoAppkey);
        param.put("refresh_token", refreshToken);

        String dest = KakaoApi.OAUTH_GET_TOKEN.getURL() + "?" + RequestUtil.getQstr(param);
        HttpWebURLConnection conn = new HttpWebURLConnection(dest, param);
        HttpWebURLResponse result =  conn.sendPostByParamTypeJson();
        if(200 == result.getResponseCode()){
        	return JsonUtil.JSONSourceToHashMap(result.getResult());
        }
    	LOG.error("Kakao Access Token 생성 과정 중  responseCode:"+result.getResponseCode());
    	throw CustomException.HTTP_WEB_CALL_EXCEPTION;
    }

    /**
     * 카카오 유저 조회
     * @param kakaoAccessToken
     * @return
     */
    public static HashMap<String, Object> KakaoGetUserProfile(String kakaoAccessToken){
        HashMap<String, Object> param = new HashMap<String, Object>();
        HttpWebURLConnection conn = new HttpWebURLConnection(KakaoApi.KAPI_KAKAO_USER_PROFILE.getURL(), param);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Bearer "+kakaoAccessToken);
        conn.setHeader(headerMap);
        HttpWebURLResponse result = conn.sendPost();
        if(200 == result.getResponseCode()){
        	return JsonUtil.JSONSourceToHashMap(result.getResult());
        }

        LOG.error("Kakao api exception!"+KakaoApi.KAPI_KAKAO_USER_PROFILE.getURL()+", "+new JSONObject(headerMap)+", "+new JSONObject(param));
        throw RedirectException.LOGIN_EXCEPTION;
    }

    /**
     * 카카오 스토리 글 포스팅
     * @return
     */
    public static int kakaoStoryPosting(String kakaoAccessToken, String message){
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("content", message);

        HttpWebURLConnection conn = new HttpWebURLConnection(KakaoApi.KAPI_STORY_POSTING_NOTE.getURL(), param);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Bearer "+kakaoAccessToken);
        conn.setHeader(headerMap);
        try {
            HttpWebURLResponse result = conn.sendPost();
            System.err.println(new JSONObject(result.getResultMap()));
            return result.getResponseCode();
        } catch (Exception e) {
        	LOG.error("Kakao api exception!"+KakaoApi.KAPI_STORY_POSTING_NOTE.getURL()+", "+new JSONObject(headerMap)+", "+new JSONObject(param));
        }

        return KakaoResponseBody.Systemfail.getCode();


    }
    
   
	
	/**
	 * 카카오 계정으로 로그인 
	 * @param state
	 * @param domain
	 * @param expt
	 * @return
	 */
	public static String GetKakaoAuthUrl(String state, String domain, String expt) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("client_id", KakaoAppkey);		
        param.put("redirect_uri", URLEncoder.encode(domain+REDIRECT_URI_LOGIN));
        param.put("response_type", "code");
        param.put("state", state);
        param.put("encode_state", "n");
        

        String dest = KakaoApi.OAUTH_GET_CODE.getURL();
        return KakaoApi.OAUTH_GET_CODE.getURL() + "?" + RequestUtil.getMapToQstr(param);
	}
	
	/**
	 * 토큰 유효성 확인
	 * @param kakaoAccessToken
	 * @return
	 */
	public static boolean HasValidToken(String kakaoAccessToken){
		HashMap<String, Object> param = new HashMap<String, Object>();
		HttpWebURLConnection conn = new HttpWebURLConnection(KakaoApi.KAPI_ACCESS_TOKEN_INFO.getURL(), param);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Bearer "+kakaoAccessToken);
        conn.setHeader(headerMap);
        try {
            HttpWebURLResponse result = conn.sendGet();
            //System.err.println(new JSONObject(result.getResultMap()));
            HashMap<String, Object> resultMap = result.getResultMap();
            boolean isContains = resultMap.containsKey("expires_in");
            if(isContains == true){
            	int expiresIn = (Integer)resultMap.get("expires_in");
            	if(expiresIn > 0){
            		return true;
            	}
            }
        } catch (Exception e) {
        	LOG.error("Kakao api exception!"+KakaoApi.KAPI_ACCESS_TOKEN_INFO.getURL()+", "+new JSONObject(headerMap)+", "+new JSONObject(param));
        }
        
        return false;
	}
	
	/**
	 * 채널관계 확인 (비즈니스 신청 후 별도의권한이 필요함)
	 * @param kakaoAccessToken
	 * @return
	 */
	public static PlusFriendsStatusEnum GetPlusFriendsStatus(String kakaoAccessToken){
		HashMap<String, Object> param = new HashMap<String, Object>();
		HttpWebURLConnection conn = new HttpWebURLConnection(KakaoApi.KAPI_PLUSFRIENDS.getURL(), param);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Bearer "+kakaoAccessToken);
        conn.setHeader(headerMap);
        try {
            HttpWebURLResponse result = conn.sendGet();
            System.err.println(new JSONObject(result.getResultMap()));
        } catch (Exception e) {
        	LOG.error("Kakao api exception!"+KakaoApi.KAPI_PLUSFRIENDS.getURL()+", "+new JSONObject(headerMap)+", "+new JSONObject(param));
        }
        
        return PlusFriendsStatusEnum.NONE;
	}
	
	
	
	public static boolean UploadTargetUserFile(){
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("channel_public_id", KakaoChannelID);
		param.put("file_name", "WINNER_20200725");
		
		JSONObject schema = new JSONObject(); 
		schema.accumulate("전화번호", "010-3166-7992");
		schema.accumulate("응모일", "2020-07-25T21:00");
		
		param.put("schema", schema);
		
		HttpWebURLConnection conn = new HttpWebURLConnection(KakaoApi.KAPI_TARGET_USER_FILE.getURL(), param);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "KakaoAK "+KakaoAdminKey);
        conn.setHeader(headerMap);
        try {
            HttpWebURLResponse result = conn.sendPostByParamTypeJson();
            System.err.println(new JSONObject(result.getResultMap()));
        } catch (Exception e) {
        	LOG.error("Kakao api exception!"+KakaoApi.KAPI_TARGET_USER_FILE.getURL()+", "+new JSONObject(headerMap)+", "+new JSONObject(param));
        }
        
        return false;
	}
	
}