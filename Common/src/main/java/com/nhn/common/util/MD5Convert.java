package com.nhn.common.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 *  ------------------------------------------------------------------------------------------------
 *   Class Id		: MD5Convert
 *   Introduction	: md5
 *   Parameter		:
 *  ------------------------------------------------------------------------------------------------
 *   Version		Date				SE				Description
 *  ------------------------------------------------------------------------------------------------
 *   1.0.0	2006.05.15			Lee Yi Suk
 *  ------------------------------------------------------------------------------------------------
 * </pre>
 */

public class MD5Convert {

	public MD5Convert() {
	}

	public static String Md5(Object o) {
	    StringBuffer sb = new StringBuffer();
		byte[] digest = null;
        try {
            digest = MessageDigest.getInstance("MD5").digest(o.toString().getBytes());


            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
                sb.append(Integer.toString(digest[i] & 0x0f, 16));
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return sb.toString();
	}

	public static String SHA1(String s) {
	    StringBuffer sb = new StringBuffer();
		byte[] digest = null;
        try {
            digest = MessageDigest.getInstance("SHA1").digest(s.getBytes());
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
                sb.append(Integer.toString(digest[i] & 0x0f, 16));
            }

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return sb.toString();
	}

}