package com.nhn.common.util;

import java.util.HashMap;

public class BaseRequestInfo {
    String contextPath = "";
    String url = "";
    String uri = "";
    String queryString = "";
    String remoteAddr = "";
    String remoteHost = "";
    String serverName = "";
    HashMap<String, String> cookieMap = new HashMap<String, String>();
    HashMap<String, Object> paramMap = new HashMap<String, Object>();

    public String getContextPath() {
        return contextPath;
    }
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getQueryString() {
        return queryString;
    }
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
    public String getRemoteAddr() {
        return remoteAddr;
    }
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }
    public String getRemoteHost() {
        return remoteHost;
    }
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }
    public String getServerName() {
        return serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public HashMap<String, String> getCookieMap() {
        return cookieMap;
    }
    public void setCookieMap(HashMap<String, String> cookieMap) {
        this.cookieMap = cookieMap;
    }
    public HashMap<String, Object> getParamMap() {
        return paramMap;
    }
    public void setParamMap(HashMap<String, Object> paramMap) {
        this.paramMap = paramMap;
    }


}
