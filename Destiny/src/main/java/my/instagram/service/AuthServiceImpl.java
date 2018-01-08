package my.instagram.service;

import java.util.Date;
import java.util.HashMap;

import my.instagram.bean.InstaAccess;
import my.instagram.bean.InstaAccessExample;
import my.instagram.constant.Const;
import my.instagram.constant.Const.Client;
import my.instagram.mapper.InstaAccessMapper;
import my.random.api.exception.CustomException;
import my.random.api.exception.ExceptionEnum;
import my.random.api.exception.ExceptionEnum.ResponseType;
import my.random.api.util.HttpWebURLConnection;
import my.random.api.util.HttpWebURLResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
	private static Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Autowired
	InstaAccessMapper instaAccessMapper;
	
	
	/**
	 * 인스타앱의 AccessToken 획득.
	 */
	@Override
	public String setAccessToken(String code) {
		HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("client_id", Client.ID.getValue());
        param.put("client_secret", Client.SECRET.getValue());
        param.put("grant_type", "authorization_code");
        param.put("redirect_uri", Client.REDIRECT_URI.getValue());
        param.put("code", code);
        
        HttpWebURLConnection httpconn = new HttpWebURLConnection(Const.GET_ACCESS_TOKEN, param);
    	HttpWebURLResponse response = httpconn.sendPost();
    	if(response.getResponseCode() != 200){
    		ExceptionEnum.ResponseType.INSTAGRAM_ACCESS_TOKEN_EXCEPTION.setMessage(response.getResultMap());
    		throw CustomException.INSTAGRAM_ACCESS_TOKEN_EXCEPTION;
    	}
    	String username = setUser(response.getResultMap());
    	return username;
	}

	public String setUser(HashMap<String, Object> responseMap){
		String accessToken = responseMap.get("access_token").toString();
    	JSONObject user = new JSONObject(responseMap.get("user").toString());
    	String userid = user.getString("id");
    	String username = user.getString("username");
    	InstaAccess instaAcess = new InstaAccess();
    	instaAcess.setAccessToken(accessToken);
    	instaAcess.setRegdate(new Date());
    	instaAcess.setUser(user.toString());
    	instaAcess.setUserid(userid);
    	instaAcess.setUsername(username);
    	
    	
    	InstaAccess searchUser = instaAccessMapper.selectByPrimaryKey(userid);
    	if(null == searchUser){
    		instaAccessMapper.insert(instaAcess);
    	}else{
    		instaAccessMapper.updateByPrimaryKeySelective(instaAcess);
    	}
    	return username;
	}
}
