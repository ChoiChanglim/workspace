/*
 * @(#)CommonUtil.java
 *
 * Copyright 2007 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.nhn.common.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 박근희
 */
public class CommonUtil {
	private static Log LOG = LogFactory.getLog(CommonUtil.class);
	/**
	 * Calc offset.
	 */
	public static int calcOffset(int page, int pageSize) {
		return (page - 1) * pageSize;
	}

	/**
	 * 스트링 늘리기
	 * @param target 늘리 스트릴
	 * @param length 몇자리로?
	 * @param appdendChar 늘린 뒤 채울 공간의 스트링은?
	 * @return
	 */
	public static String addStringByLength(String target, int length, String appdendChar) {
		int addCharCount = length - target.length();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < addCharCount; i++) {
			buf.append(appdendChar);
		}
		buf.append(target);

		return buf.toString();
	}

	/**
	 * 원하는 부분에서의 스트링을 int로 변경
	 * @param target
	 * @param length
	 * @return
	 */
	public static int parseIntByString(String target, int offset, int length) {
		int resultValue = 1;
		try {
			String value = target.substring(offset, length);
			resultValue = Integer.parseInt(value);
		} catch (Exception e) {
			resultValue = 1;
		}
		return resultValue;
	}

	/**
	 * 원하는 부분에서의 스트링을 int로 변경
	 * @param target
	 * @param length
	 * @return
	 */
	public static int parseIntByString(String target, int offset) {
		int resultValue = 1;
		try {
			String value = target.substring(offset);
			resultValue = Integer.parseInt(value);
		} catch (Exception e) {
			resultValue = 1;
		}
		return resultValue;
	}


	/**
	 * data를 jsp로 디스패치하는게 아니라 직접 writer함
	 * @param response
	 * @param data
	 * @throws Exception
	 */
	public static void writerResponse(HttpServletResponse response, String data) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(data);
		writer.flush();
	}

	/**
	 * xdr 스트링을 byte[]로 변환
	 *
	 * @param inputXDR the input xdr
	 * @return the byte array
	 */
	public static byte[] getByteArray(String inputXDR) {
		byte[] inputByte = inputXDR.getBytes();
		byte[] byteArray = new byte[inputXDR.length() / 2];
		int m = 0;
		LOG.debug("inputByte[0] :" + inputByte[0]);
		LOG.debug("inputXDR.length() :" + inputXDR.length());
		LOG.debug("inputByte.length :" + inputByte.length);
		for (int n = 0; n < byteArray.length; n++) {
			byteArray[n] = (byte)((inputByte[m] - 48) * 16 + (inputByte[m + 1] - 48));
			m += 2;
		}
		return byteArray;
	}

	/**
	 * 바이트를 스트링으로 표현
	 * @param saveByte
	 * @return
	 */
	public static String convertStringByteArray(byte[] saveByte) {
		byte[] byteArray = new byte[saveByte.length * 2]; // 1024
		int m = 0;
		for (int n = 0; n < byteArray.length; n = n + 2) {
			byteArray[n] = (byte)(saveByte[m] / 16 + 48);
			byteArray[n + 1] = (byte)(saveByte[m] % 16 + 48);
			m += 1;
		}
		return new String(byteArray);
	}

}
