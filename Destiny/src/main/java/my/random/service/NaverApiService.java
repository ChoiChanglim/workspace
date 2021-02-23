package my.random.service;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.exception.CustomException;
import my.random.api.exception.RedirectException;
import my.random.api.util.AesCipher;
import my.random.api.util.HttpWebURLConnection;
import my.random.api.util.HttpWebURLResponse;
import my.random.api.util.JsonUtil;
import my.random.api.util.RequestUtil;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class NaverApiService {
	private static Logger LOG = LoggerFactory.getLogger(NaverApiService.class);
	public static String STATE_COOKIE_NAME = "nstat";
	public static final String ACCESS_TOKEN_COOKIE_NAME = "nact";
	public static final String REFRESH_TOKEN_COOKIE_NAME = "nrct";
	public static String REDIRECT_URI_LOGIN="/api/naver/redirect_login"; 
	public static final String NaverServerCipherKey = "IWANTSEAOFTHIEVESKRLOCALE";
	
	
	public enum NaverApp{
		WEB("UR1wMwU0n4xqIhHJEvuZ", "X592B4L4no");
		
		private String id;
		private String secret;
		private NaverApp(String id, String secret){
			this.id = id;
			this.secret = secret;
		}
		public String getId() {
			return id;
		}
		public String getSecret() {
			return secret;
		}
		
	}

	public enum NaverApi{
		GET_AUTHORIZE_URL("https://nid.naver.com/oauth2.0/authorize", "네이버 아이디로 로그인 인증 요청문 생성", "{client_id, response_type, redirect_uri, state}"),
		GET_ACCESSTOKEN("https://nid.naver.com/oauth2.0/token", "네이버 아이디로 로그인 접근 토큰 요청", "{client_id, client_secret, grant_type, redirect_uri, state, code}"),
		REFRESH_ACCESSTOKEN("https://nid.naver.com/oauth2.0/token", "네이버 아이디로 로그인 접근 토큰 갱신 요청", "{client_id, client_secret, grant_type, redirect_uri, state, code}"),
		DELETE_ACCESSTOKEN("https://nid.naver.com/oauth2.0/token", "네이버 아이디로 로그인 접근 토큰 갱신 요청", "{client_id, client_secret, grant_type, redirect_uri, state, code}"),
		GET_PROFILE("https://openapi.naver.com/v1/nid/me", "네이버 유저 프로필 조회", "Authorization: Bearer {access_token}");
		
		private String url;
		private String descript;
		private String params;
		
		private NaverApi(String url, String descript, String params){
			this.url = url;
			this.descript = descript;
			this.params = params;
		}

		public String getUrl() {
			return url;
		}

		public String getDescript() {
			return descript;
		}

		public String getParams() {
			return params;
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
	/**
	 * 네이버 아이디로 로그인 인증 요청문 생성
	 * @param state
	 * @return
	 */
	public static String GetNaverAuthUrl(String state, String domain, String expt){
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("client_id", NaverApp.WEB.getId()); 
		param.put("response_type", "code"); 
		param.put("redirect_uri", URLEncoder.encode(domain+REDIRECT_URI_LOGIN));				
		param.put("state", state); 
		if("NO_REQUIRED_PARAMETERS".equals(expt)){
			param.put("auth_type", "reprompt");
		}
		return NaverApi.GET_AUTHORIZE_URL.getUrl() + "?" + RequestUtil.getMapToQstr(param);
	}
	/**
     * access token 생성.
     */
    public static HashMap<String, Object> GetNaverAccessToken(String state, String code) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("client_id", NaverApp.WEB.getId());
        param.put("client_secret", NaverApp.WEB.getSecret());
        param.put("grant_type", "authorization_code");
        param.put("state", state);
        param.put("code", code);

        String dest = NaverApi.GET_ACCESSTOKEN.getUrl() + "?" + RequestUtil.getQstr(param);
        HttpWebURLConnection conn = new HttpWebURLConnection(dest, param);
        HttpWebURLResponse result =  conn.sendPostByParamTypeJson();
        if(200 == result.getResponseCode()){
        	return JsonUtil.JSONSourceToHashMap(result.getResult());
        }
    	LOG.error("Naver Access Token 생성 과정 중  responseCode:"+result.getResponseCode());
        throw CustomException.HTTP_WEB_CALL_EXCEPTION;
    }
    /**
     * access token 갱신
     * @param refreshToken
     * @return
     */
    public static HashMap<String, Object> RefreshNaverAccessToken(String refreshToken) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("client_id", NaverApp.WEB.getId());
        param.put("client_secret", NaverApp.WEB.getSecret());
        param.put("grant_type", "refresh_token");
        param.put("refresh_token", refreshToken);

        String dest = NaverApi.GET_ACCESSTOKEN.getUrl() + "?" + RequestUtil.getQstr(param);
        HttpWebURLConnection conn = new HttpWebURLConnection(dest, param);
        HttpWebURLResponse result =  conn.sendPost();
        if(200 == result.getResponseCode()){
        	return JsonUtil.JSONSourceToHashMap(result.getResult());
        }
    	LOG.error("Naver Access Token 갱신 과정 중  responseCode:"+result.getResponseCode());
        throw CustomException.HTTP_WEB_CALL_EXCEPTION;
    }
    /**
     * access token 삭제 (로그아웃)
     * @param accessToken
     * @return
     */
    public static HashMap<String, Object> DeleteNaverAccessToken(String accessToken) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("client_id", NaverApp.WEB.getId());
        param.put("client_secret", NaverApp.WEB.getSecret());
        param.put("grant_type", "delete");
        param.put("access_token", accessToken);
        param.put("service_provider", "NAVER");

        String dest = NaverApi.GET_ACCESSTOKEN.getUrl() + "?" + RequestUtil.getQstr(param);
        HttpWebURLConnection conn = new HttpWebURLConnection(dest, param);
        HttpWebURLResponse result =  conn.sendPost();
        if(200 == result.getResponseCode()){
        	return JsonUtil.JSONSourceToHashMap(result.getResult());
        }
    	LOG.error("Naver Access Token 삭제 과정 중  responseCode:"+result.getResponseCode());
        throw CustomException.HTTP_WEB_CALL_EXCEPTION;
    }
    
    /**
     * 네이버 AcceessToken을 어떻게든 구해서 가져오기.
     * @param req
     * @param res
     * @param cipher
     * @return
     */
    public static String GetAccessToken(HttpServletRequest req, HttpServletResponse res, AesCipher cipher){
    	//access token이 만료되었을때
    	if(RequestUtil.getCookieMap(req).containsKey(NaverApiService.ACCESS_TOKEN_COOKIE_NAME) == false){
    		
    		if(RequestUtil.getCookieMap(req).containsKey(NaverApiService.REFRESH_TOKEN_COOKIE_NAME) == false){
    			LOG.error("Naver 인증 정보의 access token이 없고 refresh token도 없다. 둘다 없다. 로그인 페이지로 보내자");
    			throw RedirectException.REDIRECT_LOGIN_PAGE;
    		}
    		
			String refreshToken = cipher.decrypt(RequestUtil.getCookieMap(req).get(NaverApiService.REFRESH_TOKEN_COOKIE_NAME));
			HashMap<String, Object> tokenResultMap = NaverApiService.RefreshNaverAccessToken(refreshToken);
			String accessToken = (String)tokenResultMap.get("access_token");
			//쿠키에 다시 구어준다.
	        RequestUtil.setCookieByMinute(res, NaverApiService.ACCESS_TOKEN_COOKIE_NAME, cipher.encrypt(accessToken), 55);
			return accessToken;
		}
		String accessToken = cipher.decrypt(RequestUtil.getCookieMap(req).get(NaverApiService.ACCESS_TOKEN_COOKIE_NAME));
		return accessToken;
    }
    
    
    /**
     * 네이버 유저 조회
     * @param Naver 유저 Profile 조회
     * @return
     */
    public static HashMap<String, Object> NaverGetUserProfile(String naverAccessToken){
        HashMap<String, Object> param = new HashMap<String, Object>();
        HttpWebURLConnection conn = new HttpWebURLConnection(NaverApi.GET_PROFILE.getUrl(), param);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Bearer "+naverAccessToken);
        conn.setHeader(headerMap);
        HttpWebURLResponse result = conn.sendPost();
        if("success".equals(result.getResultMap().get("message"))){
        	return JsonUtil.JSONSourceToHashMap(result.getResultMap().get("response").toString());
        }
            
        LOG.error("Naver api exception!"+NaverApi.GET_PROFILE.getUrl()+", "+new JSONObject(headerMap)+", "+new JSONObject(param));
        throw RedirectException.LOGIN_EXCEPTION;
    }
}
