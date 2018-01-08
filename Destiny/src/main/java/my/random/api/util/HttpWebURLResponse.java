package my.random.api.util;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

public class HttpWebURLResponse {
    private int responseCode;
    private String result;

    public int getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    public JSONObject getResultJson() {
        return new JSONObject(this.result);
    }

    public HashMap<String, Object> getResultMap(){
    	HashMap<String, Object> tomap = new HashMap<String, Object>();
    	
    	Iterator<String> iter = getResultJson().keySet().iterator();
    	while(iter.hasNext()){
    		String key = iter.next();
    		Object val = getResultJson().get(key);
    		tomap.put(key, val);
    	}
    	return tomap;
    }

}
