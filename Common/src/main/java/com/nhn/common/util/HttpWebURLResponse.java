package com.nhn.common.util;

import org.json.JSONObject;

public class HttpWebURLResponse {
    private int responseCode;
    private String result;
    private JSONObject resultJson;


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



}
