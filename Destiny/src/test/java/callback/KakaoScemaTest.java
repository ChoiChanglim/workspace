package callback;

import org.json.JSONObject;

public class KakaoScemaTest {

	public static void main(String[] args) {
		JSONObject schema = new JSONObject(); 
		schema.accumulate("전화번호", "010-3166-7992");
		schema.accumulate("최근구매일", "2020-07-24T16:35");
		schema.accumulate("응모일", "2020-07-25T21:00");
		
		
		System.err.println(schema);
	}
	
}
