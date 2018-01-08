import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class JsonTest {

	public static void main(String[] args) {
		String source = "{\"INSTAGRAM_API_EXCEPTION\":\"{\\\"error_type\\\": \\\"OAuthException\\\", \\\"code\\\": 400, \\\"error_message\\\": \\\"Matchingcodewasnotfoundorwasalreadyused.\\\"}\"}";
		
		
		HashMap<String, Object> map = JSONSourceToHashMap(source);
		System.err.println(map);
		
	}
	
	public static HashMap<String, Object> JSONSourceToHashMap(String source){
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(source);
		HashMap<String, Object> map = JsonElementToHashMap(element);
		return map;
	}
	
	public static HashMap<String, Object> JsonElementToHashMap(JsonElement element){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Iterator<Map.Entry<String, JsonElement>> iter = element.getAsJsonObject().entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, JsonElement> entry = iter.next();
			String key = entry.getKey();
			JsonElement val = entry.getValue();
			JsonParser parser = new JsonParser();
			if(parser.parse(val.getAsString()).isJsonObject()){
				HashMap<String, Object> valMap = JsonElementToHashMap(parser.parse(val.getAsString()));
				map.put(key, valMap);
			}else{
				map.put(key, val);
			}
		}
		return map;
	}

}
