package my.random.api.util;

import java.io.UnsupportedEncodingException;  
import java.security.GeneralSecurityException;  
import java.security.Key;  
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;  
import javax.crypto.spec.IvParameterSpec;  
import javax.crypto.spec.SecretKeySpec;

import my.random.api.exception.CustomException;

import org.apache.commons.codec.binary.Base64;
public class AesCipher {  
    private String iv;
    private Key keySpec;

    /**
     * 16자리의 키값을 입력하여 객체를 생성한다.
     * @param key 암/복호화를 위한 키값
     * @throws UnsupportedEncodingException 키값의 길이가 16이하일 경우 발생
     */
    public AesCipher(String key) {
    	if(key.length() < 16){
    		key = StringUtil.fillString(key, "-", 16);
    	}
        this.iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b;
		try {
			b = key.getBytes("UTF-8");
			int len = b.length;
	        if(len > keyBytes.length){
	            len = keyBytes.length;
	        }
	        System.arraycopy(b, 0, keyBytes, 0, len);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.keySpec = keySpec;
    }

    /**
     * AES256 으로 암호화 한다.
     * @param str 암호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */

    public String encrypt(String str){
    	try{
	        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
	        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
	        String enStr = new String(Base64.encodeBase64String(encrypted));
	        return enStr;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        throw CustomException.AES_ENCRYPT_EXCEPTION;
    }

    /**
     * AES256으로 암호화된 txt 를 복호화한다.
     * @param str 복호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String decrypt(String str){
    	str = str.replaceAll(" ", "+");
    	try{
	        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
	        byte[] byteStr = Base64.decodeBase64(str.getBytes());
	        return new String(c.doFinal(byteStr), "UTF-8");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        throw CustomException.AES_DECRYPT_EXCEPTION;
    }
}