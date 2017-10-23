package com.nhn.common.security;

public class ServerKeys {
    public enum ServerSecurityEnum{
        FOR_CGP("xb1scorpio");

        private String key;
        private ServerSecurityEnum(String key){
            this.key = key;
        }
        public String getKey(){
            return key;
        }
    }
}
