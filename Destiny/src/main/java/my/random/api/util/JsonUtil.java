package my.random.api.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	/**
	 * json string to hashmap
	 * 
	 * @param source
	 * @return
	 */
	public static HashMap<String, Object> ExceptionJSONSourceToHashMap(String source) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(source);
		if (element.isJsonObject() == false) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(source, "No description.");
			return map;
		}
		HashMap<String, Object> map = JsonElementToHashMap(element);

		return map;
	}
	
	public static HashMap<String, Object> JSONSourceToHashMap(String source) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(source);
		if (element.isJsonObject() == false) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			return map;
		}
		HashMap<String, Object> map = JsonElementToHashMap(element);

		return map;
	}

	public static boolean isJSONValid(String source) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(source);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static HashMap<String, Object> JsonElementToHashMap(JsonElement element) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Iterator<Map.Entry<String, JsonElement>> iter = element
				.getAsJsonObject().entrySet().iterator();
		JsonParser parser = new JsonParser();
		while (iter.hasNext()) {
			Map.Entry<String, JsonElement> entry = iter.next();
			String key = entry.getKey();
			JsonElement val = entry.getValue();
			if (parser.parse(val.toString()).isJsonObject()) {
				HashMap<String, Object> valMap = JsonElementToHashMap(parser.parse(val.toString()));
				map.put(key, valMap);
			} else {
				map.put(key, val.getAsString());
			}
		}
		return map;
	}
}