package my.random.api.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpWebURLConnection {
    private static Logger LOG = LoggerFactory.getLogger(HttpWebURLConnection.class);
	private final String USER_AGENT = "Mozilla/5.0";
	private String dest;
	private HashMap<String, Object> param = new HashMap<String, Object>();
	private HashMap<String, String> header = new HashMap<String, String>();

	public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public HashMap<String, Object> getParam() {
        return param;
    }

    public void setParam(HashMap<String, Object> param) {
        this.param = param;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public HttpWebURLConnection(String dest, HashMap<String, Object> param) {
        super();
        this.dest = dest;
        this.param = param;
    }

    // HTTP GET request
    public HttpWebURLResponse sendGet() {
    	HttpWebURLResponse httpWebURLResponse = new HttpWebURLResponse();
		String url = this.dest + "?" + RequestUtil.getQstr(this.param);
		LOG.info("HTTP GET - "+url);
		try{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			// optional default is GET
			con.setRequestMethod("GET");
	
			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			Iterator<String> iter = this.header.keySet().iterator();
		    while(iter.hasNext()){
		        String key = iter.next();
		        String value = this.header.get(key);
		        con.setRequestProperty(key, value);
		    }
		    
			int responseCode = con.getResponseCode();
			LOG.debug("\nSending 'GET' request to URL : " + url);
			LOG.debug("Response Code : " + responseCode);
	
			BufferedReader in = null;
	        String inputLine = "";
	        StringBuffer response = new StringBuffer();
	        if (200 <= responseCode && responseCode <= 299) {
	            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	        } else {
	            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	        }
	
			in.close();
	
			//print result
			LOG.debug(response.toString());
	
			
			httpWebURLResponse.setResponseCode(responseCode);
			httpWebURLResponse.setResult(response.toString());
		}catch(Exception e){
			LOG.error(e.toString());
		}
		return httpWebURLResponse;

	}

	// HTTP POST request
	public HttpWebURLResponse sendPost() {
		HttpWebURLResponse httpWebURLResponse = new HttpWebURLResponse();
		try{
			URL obj = new URL(dest);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
	
		    Iterator<String> iter = this.header.keySet().iterator();
		    while(iter.hasNext()){
		        String key = iter.next();
		        String value = this.header.get(key);
		        con.setRequestProperty(key, value);
		    }
	
			String urlParameters = RequestUtil.getQstr(this.param);
	
			// Send post request
			con.setDoOutput(true);
			con.setDoInput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			//wr.writeBytes(urlParameters);
			wr.write(urlParameters.getBytes("utf-8"));
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
	
	
			LOG.debug("\nSending 'POST' request to URL : " + dest);
			LOG.debug("Post parameters : " + urlParameters);
			LOG.debug("Response Code : " + responseCode);
	
	
			BufferedReader in = null;
			String inputLine = "";
	        StringBuffer response = new StringBuffer();
			if (200 <= responseCode && responseCode <= 299) {
			    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			    while ((inputLine = in.readLine()) != null) {
		            response.append(inputLine);
		        }
			} else {
			    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			    while ((inputLine = in.readLine()) != null) {
		            response.append(inputLine);
		        }
			}
	
	
			in.close();
			//print result
			LOG.debug("Response:" +response.toString());
	
			
	        httpWebURLResponse.setResponseCode(responseCode);
	        httpWebURLResponse.setResult(response.toString());
		}catch(Exception e){
			
		}

		

        return httpWebURLResponse;
	}

	// HTTP POST request
    public HttpWebURLResponse sendPostByParamTypeJson() {
    	HttpWebURLResponse httpWebURLResponse = new HttpWebURLResponse();
    	try{
	        URL obj = new URL(dest);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
	        //add reuqest header
	        con.setRequestMethod("POST");
	        con.setRequestProperty("User-Agent", USER_AGENT);
	        con.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
	
	        Iterator<String> iter = this.header.keySet().iterator();
	        LOG.debug("Header:"+new JSONObject(this.header));
	        while(iter.hasNext()){
	            String key = iter.next();
	            String value = this.header.get(key);
	            con.setRequestProperty(key, value);
	        }
	
	        String urlParameters = new JSONObject(this.param).toString();
	
	        // Send post request
	        con.setDoOutput(true);
	        con.setDoInput(true);
	        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	        wr.write(urlParameters.getBytes("utf-8"));
	        wr.flush();
	        wr.close();
	
	        int responseCode = con.getResponseCode();
	
	
	        LOG.debug("\nSending 'POST' request to URL : " + dest);
	        LOG.debug("Post parameters : " + urlParameters);
	        LOG.debug("Response Code : " + responseCode);
	
	
	        BufferedReader in = null;
	        String inputLine = "";
	        StringBuffer response = new StringBuffer();
	        if (200 <= responseCode && responseCode <= 299) {
	            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	        } else {
	            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	        }
	
	
	        in.close();
	        //print result
	        LOG.debug("Response:" +response.toString());
	
	        
	        httpWebURLResponse.setResponseCode(responseCode);
	        httpWebURLResponse.setResult(response.toString());
    	}catch(Exception e){
    		LOG.error(e.toString());
    	}
        return httpWebURLResponse;

    }


    // HTTP POST request
    public HttpWebURLResponse sendPostByParamTypeString() throws Exception {
        String url = this.dest + "?" + RequestUtil.getQstr(this.param);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");

        Iterator<String> iter = this.header.keySet().iterator();
        LOG.debug("Header:"+new JSONObject(this.header));
        while(iter.hasNext()){
            String key = iter.next();
            String value = this.header.get(key);
            con.setRequestProperty(key, value);
        }

        String urlParameters = new JSONObject(this.param).toString();

        // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(urlParameters.getBytes("utf-8"));
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();


        LOG.debug("\nSending 'POST' request to URL : " + dest);
        LOG.debug("Post parameters : " + urlParameters);
        LOG.debug("Response Code : " + responseCode);


        BufferedReader in = null;
        String inputLine = "";
        StringBuffer response = new StringBuffer();
        if (200 <= responseCode && responseCode <= 299) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } else {
            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }


        in.close();
        //print result
        LOG.debug("Response:" +response.toString());

        HttpWebURLResponse httpWebURLResponse = new HttpWebURLResponse();
        httpWebURLResponse.setResponseCode(responseCode);
        httpWebURLResponse.setResult(response.toString());

        return httpWebURLResponse;

    }
}