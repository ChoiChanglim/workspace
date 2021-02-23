package my.random.api.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestUtil {

    private static Logger LOG = LoggerFactory.getLogger(RequestUtil.class);

	/**
	 * 쿼리스트링 쪼개서 맵으로
	 * @param qstr
	 * @return
	 */
	public static HashMap<String, Object> qstrSplit(String qstr){
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(qstr == null){
		    return map;
		}
		String[] qstr_arr = qstr.split("[&]"); //쪼개 넣기

	    for(int i=0;i<qstr_arr.length;i++){
	    	String[] keyAndValue = qstr_arr[i].split("[=]");

	    	String key = keyAndValue[0];


	    	String val = "";
	    	if(keyAndValue.length == 1){
	    		val = "";
	    	}else{
	    		val = keyAndValue[1];
	    	}

	    	try{
	    		//System.out.println("key:"+key+"val:"+val);
	    		map.put(key, val);
	    	}catch(Exception e){
	    		System.out.println(e);

	    	}
	    }

		return map;
	}
	/**
	 * 쿼리스트링 맵을 문자열로
	 * @param map
	 * @return
	 */
	public static String getMapToQstr(HashMap<String, String> map){
		StringBuffer sb = new StringBuffer();
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String value = map.get(key);
			sb.append(key+"="+value+"&");
		}
		String qstr = "";
		if(sb.length() > 0){
			qstr = sb.deleteCharAt(sb.length()-1).toString();
		}

		return qstr;
	}

	/**
     * 쿼리스트링 맵을 문자열로
     * @param map
     * @return
     */
    public static String getQstr(HashMap<String, Object> map){
        StringBuffer sb = new StringBuffer();
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            Object value = map.get(key);
            sb.append(key+"="+value+"&");
        }
        String qstr = "";
        if(sb.length() > 0){
            qstr = sb.deleteCharAt(sb.length()-1).toString();
        }

        return qstr;
    }

    public static String getMapToString(HashMap<String, Boolean> newIconMap){
        StringBuffer sb = new StringBuffer();
        Iterator<String> iter = newIconMap.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            Object value = newIconMap.get(key);
            sb.append(key+"="+value+"&");
        }
        String qstr = "";
        if(sb.length() > 0){
            qstr = sb.deleteCharAt(sb.length()-1).toString();
        }

        return qstr;
    }
	/**
	 * 쿼리스트링 수정
	 * @param key
	 * @param obj
	 * @param qstr
	 * @return
	 */
	public static String qstrUpdate(String key, Object obj, String qstr){
		HashMap map = qstrSplit(qstr);
		map.remove(key);
		map.put(key, obj);
		String result = getMapToString(map);
		return result;
	}
	/**
	 * 리퀘스트를 맵으로
	 * @param req
	 * @return
	 */
	public static HashMap<String, Object> reqParamToMap(HttpServletRequest req){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> e = req.getParameterNames();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			Object val = null;
			if(key.contains("[]")){
				val = req.getParameterValues(key);
			}else{
				val = req.getParameter(key).toString();
			}

			//System.out.println(key+" : "+val);
			map.put(key, val);
		}
		return map;
	}

	/**
	 * 인포맵에서 서치파라미터만 따로빼서 다시 맵<S,S>로 만듬
	 * @param info
	 * @return
	 */
	public static HashMap<String, String> getSearchMap(HashMap<String, Object> param) {
		HashMap<String, String> search_info = new HashMap<String, String>();

		if(param.containsKey("searchColumn")){
			search_info.put("searchColumn", (String)param.get("searchColumn"));
		}
		if(param.containsKey("searchWord")){
			search_info.put("searchWord", (String)param.get("searchWord"));
		}

		return search_info;
	}

	/**
	 * 쿠키를 맵<String, String>으로 리턴
	 * @param req
	 * @return
	 */
	public static HashMap<String, String> getCookieMap(HttpServletRequest req){
	    Cookie[] cookies = req.getCookies();
        HashMap<String, String> cookieMap = new HashMap<String, String>();
        if(cookies != null){
            for(int i=0;i<cookies.length;i++){
                try{
                    cookieMap.put(cookies[i].getName(), URLDecoder.decode((cookies[i].getValue()),"utf-8"));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return cookieMap;
	}

	/**
	 * 쿠키 굽기
	 * @param req
	 * @param response
	 */
	public static String setCookie(HttpServletResponse response, String cookieName, String value, int untilDate){
	    try{
    	    Cookie cookie = new Cookie(cookieName, URLEncoder.encode(value,"utf-8"));
            cookie.setMaxAge(60 * 60 * 24 * untilDate);
            cookie.setPath("/");
            response.addCookie(cookie);
	    }catch(Exception e){
	        e.printStackTrace();
	    }
        return value;
    }
	public static String setCookieByMinute(HttpServletResponse response, String cookieName, String value, int minute){
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(60 * minute);
        cookie.setPath("/");
        response.addCookie(cookie);
        return value;
    }



	/**
	 * 아이피 허용 체크
	 * true:허용, flase:접속불가
	 * @param allows {허용할 아이피}
	 * @param userIp 유저아이피
	 * @return
	 */
	public static boolean isAllowByIp(String[] allows, String userIp){
        boolean flag = false;
        for(int i=0;i<allows.length;i++){
            if(userIp.contains(allows[i])){
                flag = true;
            }
        }
        return flag;
    }

	/**
	 * 리퀘스트 헤더를 맵으로리턴
	 * @param req
	 * @return
	 */
	public static HashMap<String, String> HeaderToMap(HttpServletRequest req){
	    HashMap<String, String> map = new HashMap<String, String>();
	    Enumeration<String> e = req.getHeaderNames();
        while(e.hasMoreElements()){
            String key = e.nextElement();
            String value = req.getHeader(key);
            map.put(key, value);
            LOG.debug("HEADER key:"+key+", value:"+value);
        }
        return map;
	}
	/**
	 * 리퀘스트를 쿼리스트링 문자열로
	 * @param req
	 * @return
	 */
	public static String getQstr(HttpServletRequest req){
	    HashMap<String, Object> qmap = reqParamToMap(req);
	    return RequestUtil.getQstr(qmap);
	}
	public static void deleteCookie(HttpServletResponse response, String cookieName){
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
	/**
	 * 키는 소문자, 값이 비었다면 제거.
	 * @param req
	 * @return
	 */
	public static HashMap<String, Object> reqParamToMapOfLowerCaseKeysAndBlankIgnore(HttpServletRequest req){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> e = req.getParameterNames();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			Object val = null;
			if(key.contains("[]")){
				val = req.getParameterValues(key);
			}else{
				val = req.getParameter(key).toString();
			}

			//System.out.println(key+" : "+val);
			if("".equals(StringUtil.nullToBlank(val)) == false){
				map.put(key.toLowerCase(), val);
			}
		}
		return map;
	}

}
