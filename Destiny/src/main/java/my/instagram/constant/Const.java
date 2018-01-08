package my.instagram.constant;

import java.util.HashMap;

public class Const {
	public static String GET_AUTH_CODE="https://api.instagram.com/oauth/authorize/";
	public static String GET_ACCESS_TOKEN="https://api.instagram.com/oauth/access_token";
	
	public enum Client{
		ID("ab3d517f97f04663bcfcb97e4db165d8"),
		SECRET("71b20d2b64494cb78a3acbc909b78e89"),
		REDIRECT_URI("http://yourlucky.cafe24.com/instagram/auth/callback");
		
		private String value;
		private Client(String value){
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
	public enum APIs{
		
	}
}
