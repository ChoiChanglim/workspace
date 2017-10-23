package com.nhn.common.util;
import java.io.UnsupportedEncodingException;

public class CharEncoding {

    public static String encode(String str, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] key_source = str.getBytes(charset);
            for(byte b : key_source) {
                String hex = String.format("%02x", b).toUpperCase();
                sb.append("%");
                sb.append(hex);
            }
        } catch(UnsupportedEncodingException e) { }//Exception
        return sb.toString();
    }

    public static String decode(String hex, String charset) {
        byte[] bytes = new byte[hex.length()/3];
        int len = hex.length();
        for(int i = 0 ; i < len ;) {
            int pos = hex.substring(i).indexOf("%");
            if(pos == 0) {
                String hex_code = hex.substring(i+1, i+3);
                bytes[i/3] = (byte)Integer.parseInt(hex_code, 16);
                i += 3;
            } else {
                i += pos;
            }
        }
        try {
            return new String(bytes, charset);
        } catch(UnsupportedEncodingException e) { }//Exception
        return "";
    }

    public static String changeCharset(String str, String charset) {
        try {
            byte[] bytes = str.getBytes(charset);
            return new String(bytes, charset);
        } catch(UnsupportedEncodingException e) { }//Exception
        return "";
    }
}