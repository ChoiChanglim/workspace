package my.random.api.util;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author dklee
 */
public class StringUtil {
	/**
	 * 문자열의 null 여부를 check하여 null일 경우 ""를 리턴한다. null이 아닐 경우에는 문자열의 trim()을 호출한 후
	 * 리턴한다.
	 *
	 * @param comment
	 * @return 공백이나 trim() 결과
	 */
	public static String nullToBlank(Object comment) {
		if (comment == null)
			return "";
		return String.valueOf(comment).trim();
	}

	/**
	 * 문자열의 null 여부를 check하여 null일 경우 ""를 리턴한다. null이 아닐 경우에는 문자열의 trim()을 호출한 후
	 * 리턴한다.
	 *
	 * @param comment
	 * @param def
	 * @return 디폴트 값이나 trim() 결과
	 */
	public static String nullToBlank(Object comment, String def) {
		if (comment == null || String.valueOf(comment).trim().equals(""))
			return def;
		return String.valueOf(comment).trim();
	}

	/**
	 * Collection, Map, Object[] 등 과 같은 객체들의 값을 펼쳐서 String으로 반환한다.
	 *
	 * @param obj
	 * @param delim
	 * @return 구분자로 연결된 문자열
	 */
	public static String expandArray(Object obj, String delim) {
		StringBuffer buff = new StringBuffer();
		if (obj instanceof Map) {
			obj = ((Map<?, ?>) obj).values();
		}
		if (obj instanceof Collection) {
			Iterator<?> iter = ((Collection<?>) obj).iterator();
			while (iter.hasNext()) {
				if (buff.length() > 0)
					buff.append(delim);
				buff.append(String.valueOf(iter.next()));
			}

		} else if (obj instanceof Object[]) {
			Object[] arr = (Object[]) obj;
			for (int i = 0; i < arr.length; i++) {
				if (buff.length() > 0)
					buff.append(delim);
				buff.append(String.valueOf(arr[i]));
			}
		}
		return buff.toString();
	}

	/**
	 * 문자열을 특정 delimiter를 이용하여 분리한다.
	 *
	 * @param buf
	 *            분리할 문자열
	 * @param delim
	 *            delimiter
	 * @return 분리된 문자열의 String[]
	 */
	public static String[] explodeToArray(String buf, String delim) {
		if (buf == null)
			return null;
		StringTokenizer token = new StringTokenizer(buf, delim);
		ArrayList<String> arrList = new ArrayList<String>();
		for (; token.hasMoreTokens();) {
			arrList.add(token.nextToken());
		}
		String retval[] = new String[arrList.size()];
		for (int i = 0; i < retval.length; i++) {
			retval[i] = arrList.get(i);
		}
		return retval;
	}

	/**
	 * 문자열을 특정 delimiter를 이용하여 분리한다.
	 *
	 * @param buf
	 *            분리할 문자열
	 * @param delim
	 *            delimiter
	 *
	 * @return 분리된 문자열의 ArrayList
	 */
	public static List<String> explodeToList(String buf, String delim) {
		if (buf == null)
			return null;
		StringTokenizer token = new StringTokenizer(buf, delim);
		List<String> arrList = new ArrayList<String>();
		while (token.hasMoreTokens()) {
			arrList.add(token.nextToken());
		}
		return arrList;
	}

	/**
	 * key,value의 목록으로 구성된 문자열을 분리하여 Map으로 만든다.<br>
	 * 사용예)<br>
	 * <code>
	 *  // key,value의 구분자를 '='를 사용하고, 각항목은 '|'로 구분하는 문자열
	 *  String buf = "key1=value1|key2=value2|key3=value3";
	 *  String itemDelim = "|";
	 *  String keyDelim = "=";
	 *
	 *  // result에는 key1,key2,key3 가 key로 구성되고, 각각의 값이,
	 *  // value1,value2, value3가 되는 Map을 반환한다.
	 *  Map result = StringUtil.explodeToMap(buf, itemDelim, keyDelim);
	 * </code>
	 *
	 *
	 * @param buf
	 *            분리할 문자열
	 * @param itemDelim
	 *            각 항목들을 분리할 delimiter
	 * @param keyDelim
	 *            key,value를 분리할 delimiter
	 * @return
	 */
	public static Map<String, String> explodeToMap(String buf, String itemDelim, String keyDelim) {
		if (buf == null)
			return null;
		StringTokenizer token = new StringTokenizer(buf, itemDelim);
		Map<String, String> map = new HashMap<String, String>();
		while (token.hasMoreTokens()) {
			StringTokenizer mapToken = new StringTokenizer(token.nextToken(), keyDelim);
			String key = null;
			String value = null;
			if (mapToken.hasMoreTokens())
				key = mapToken.nextToken();
			if (mapToken.hasMoreTokens())
				value = mapToken.nextToken();
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 문자열을 치환한다.
	 *
	 * @param src
	 *            원본 String
	 * @param oldstr
	 *            원본 String 내의 바꾸기 전 문자열
	 * @param newstr
	 *            바꾼 후 문자열
	 * @return String 치환이 끝난 문자열
	 */
	public static String replaceStr(String src, String oldstr, String newstr) {
		if (src == null) {
			return null;
		}
		StringBuffer dest = new StringBuffer("");
		int len = oldstr.length();
		int srclen = src.length();
		int pos = 0;
		int oldpos = 0;

		while ((pos = src.indexOf(oldstr, oldpos)) >= 0) {
			dest.append(src.substring(oldpos, pos));
			dest.append(newstr);
			oldpos = pos + len;
		}

		if (oldpos < srclen) {
			dest.append(src.substring(oldpos, srclen));
		}
		return dest.toString();
	}

	/**
	 * htmlString을 브자우저에서 볼수 있는 형대로 변경한다.
	 *
	 * @param strText
	 * @return 브라우져에서 볼수 있는 html
	 */
	public static String toHTML(String strText) {
		String strInput;
		StringBuffer strOutput = new StringBuffer("");
		String convert;
		char strTmp;
		int nCount;

		if (strText == null) {
			strText = "";
		}

		strInput = strText;
		nCount = strInput.length();

		for (int i = 0; i < nCount; i++) {

			strTmp = strInput.charAt(i);

			if (strTmp == '<')
				strOutput.append("&lt;");
			else if (strTmp == '>')
				strOutput.append("&gt;");
			else if (strTmp == '&')
				strOutput.append("&amp;");
			else if (strTmp == (char) 37)
				strOutput.append("&#37;");
			else if (strTmp == (char) 34)
				strOutput.append("&quot;");
			else if (strTmp == (char) 39)
				strOutput.append("&#39;");
			else if (strTmp == '#')
				strOutput.append("&#35;");
			else if (strTmp == '\n')
				strOutput.append("<br>");
			else
				strOutput.append(strTmp);

		}

		convert = strOutput.toString();
		return convert;
	}

	/**
	 * 입력된 값을 지정된 글자수만큼 fillChar를 채워서 반환
	 *
	 * @param data
	 * @param size
	 * @return 자리숫를 채운 문자열
	 */
	public static String fillString(String data, String fillChar, int size) {
		if (data == null)
			data = "";
		StringBuffer buff = new StringBuffer();
		String str = data.trim();
		for (int i = 0; i < size - str.length(); i++) {
			buff.append(fillChar);
		}
		buff.append(str);
		return buff.toString();
	}

	/**
	 * 입력된 Idnfr 에 plus(+) 1을 해준다. 항상 처음 4자리는 식별자이여야 한다. 예)
	 * getNextIdnfr("MA0100000002") ==> "MA0100000003"
	 *
	 * @param Idnfr
	 * @return 1증가된 문자열
	 */
	public static String getNextIdnfr(String Idnfr) {
		String result = null;
		if (Idnfr == null || Idnfr.length() == 0) {
			return result;
		}

		int size = Idnfr.length();
		if (Idnfr.length() > 4) {
			String preStr = Idnfr.substring(0, 4);
			String postStr = Idnfr.substring(4);
			int value = Integer.parseInt(postStr) + 1;
			result = preStr + fillString(value + "", "0", size);
		}
		return result;
	}

	/**
	 * 입력된 maxKey에 plus 1을 해준다. 예) getNextSeq("002") ==> "003"
	 *
	 * @param maxKey
	 * @return 1증가된 문자열
	 */
	public static String getNextSeq(String maxKey) {
		String result = null;
		if (maxKey == null || maxKey.length() == 0) {
			return result;
		}

		int size = maxKey.length();
		int value = Integer.parseInt(maxKey) + 1;
		result = fillString(value + "", "0", size);

		return result;
	}

	/**
	 * double 형태의 데이터를 String 으로 변환해준다.
	 *
	 * @param d
	 * @param inx
	 * @return 컴마를 추가한 문자열
	 * @throws Exception
	 */
	public static String commaMark(double d, int inx) throws Exception {
		try {
			String inxcomma = "";
			for (int i = 0; i < inx; i++) {
				if (i == 0)
					inxcomma += ".";
				inxcomma += "0";
			}
			java.text.DecimalFormat df = new DecimalFormat("#,###,###,###,###,###,###,###,##0" + inxcomma);

			return df.format(d);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 소숫점없는 double 형태의 데이터를 String 으로 변환해준다.
	 *
	 * @param d
	 * @return 컴마를 추가한 문자열
	 * @throws Exception
	 */
	public static String commaMark(Double d) throws Exception {
		try {
			return commaMark(d.doubleValue(), 0);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 해당 객체의 세부 내용을 조회할 경우 사용하는 메서드
	 *
	 * @param obj
	 * @return 객체의 내용을 보여주는 문자열
	 */
	public static String describe(Object obj) {
		if (obj == null)
			return null;
		String descrb = null;
		try {
			descrb = BeanUtils.describe(obj).toString();
		} catch (Exception e) {
			return obj.toString();
		}
		return descrb;
	}

	/**
	 * 숫자로 구성된 문자열 체크
	 *
	 * @param str
	 * @return 숫자 문자열 여부
	 */
	public static boolean isDigit(String str) {
		boolean digit = false;

		try {
			@SuppressWarnings("unused")
			long i = Long.parseLong(str);
			digit = true;
		} catch (NumberFormatException e) {
			digit = false;
		}
		return digit;
	}

	/**
	 * Indicates whether the specified Unicode character matches the Digit
	 * production.
	 */
	@SuppressWarnings("unused")
	private static boolean isDigit(int c) {
		return ((c >= 0x0030 && c <= 0x0039) || (c >= 0x0660 && c <= 0x0669) || (c >= 0x06F0 && c <= 0x06F9) || (c >= 0x0966 && c <= 0x096F)
				|| (c >= 0x09E6 && c <= 0x09EF) || (c >= 0x0A66 && c <= 0x0A6F) || (c >= 0x0AE6 && c <= 0x0AEF) || (c >= 0x0B66 && c <= 0x0B6F)
				|| (c >= 0x0BE7 && c <= 0x0BEF) || (c >= 0x0C66 && c <= 0x0C6F) || (c >= 0x0CE6 && c <= 0x0CEF) || (c >= 0x0D66 && c <= 0x0D6F)
				|| (c >= 0x0E50 && c <= 0x0E59) || (c >= 0x0ED0 && c <= 0x0ED9) || (c >= 0x0F20 && c <= 0x0F29));
	}

	/**
	 * 입력된 값을 지정된 글자수만큼 0을 채워서 반환
	 *
	 * @param data
	 * @param size
	 * @return
	 */
	public static String fillZeroString(String data, int size) {
		if (data == null)
			data = "";
		StringBuffer buff = new StringBuffer();
		String str = data.trim();
		for (int i = 0; i < size - str.length(); i++) {
			buff.append('0');
		}
		buff.append(str);
		return buff.toString();
	}

	/**
	 * String[] 에 특정 값이 존재하는지 check
	 */
	public static boolean contains(String[] array, String value) {
		if (array == null || value == null)
			return false;
		boolean containFlag = false;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && value.trim().equals(array[i].trim())) {
				containFlag = true;
			}
		}
		return containFlag;
	}

	/**
	 * 부호비트에 값이 있는 경우는 2바이트 문자로 간주하여 처리한다.
	 *
	 * @param in_Str
	 *            잘려져야 할 스트링
	 * @param in_CutPos
	 *            잘려져야 할 인덱스.
	 * @return 잘려진 스트링.
	 */
	public String cutByteLength(String in_Str, int in_CutPos) {
		byte[] byteStr = in_Str.getBytes();

		// 부호비트의 값이 0 이 아닌 경우는 2바이트 문자로 간주.
		if ((byteStr[in_CutPos] & 0x80) == 0) {
			return new String(byteStr, 0, in_CutPos);

		} else {
			return new String(byteStr, 0, in_CutPos + 1);
		}
	}

	/**
	 * 문자열의 개행문자를 <BR>
	 * 로 변환한다. 만약 문자열 안에 <HTML>이라는 문자가 존재한다면 변환하지 않는다. 또한 공백문자 ' '를 &nbsp;로
	 * 변환한다.('<','>' 문자 안에 없을 경우)
	 */
	public static String convertBr(String comment) {
		String temp = comment;
		int pos;

		pos = 0;
		while ((pos = temp.indexOf("\n")) != -1) {

			String left = temp.substring(0, pos);
			String right = temp.substring(pos + 1, temp.length());
			temp = left + "<br>" + right;

		}

		pos = 0;
		while ((pos = temp.indexOf("  ", pos)) != -1) {
			String left = temp.substring(0, pos);
			String right = temp.substring(pos + 2, temp.length());
			temp = left + "&nbsp;&nbsp;" + right;
			pos += 4;
		}
		return temp;

	}

	/**
	 * 특정문자열 사이를 삭제한다. example)
	 * deleteInString("<font color='red'>TEST123</font>", "<", ">"); --> result는
	 * TEST123
	 *
	 * @param s
	 *            원본 문자열
	 * @param from
	 *            삭제할 문자열의 시작 패턴
	 * @param to
	 *            삭제할 문자열의 끝 패턴
	 */
	public static String deleteInString(String s, String from, String to) {

		boolean inToken = false;
		StringBuffer returnBuf = new StringBuffer();
		int lastFromPosition = 0;

		int s_len = s.length();
		int from_len = from.length();
		int to_len = to.length();

		for (int i = 0; i < s_len; i++) {
			if (inToken) {
				if ((s_len >= i + to_len) && s.substring(i, i + to_len).equalsIgnoreCase(to)) {
					inToken = false;
					i += (to_len - 1);
				}
			} else {
				if ((s_len >= i + from_len) && s.substring(i, i + from_len).equalsIgnoreCase(from)) {
					lastFromPosition = i;
					i += (from_len - 1);
					inToken = true;
				} else {
					returnBuf.append(s.charAt(i));
				}
			}
		}

		if (inToken) {
			returnBuf.append(s.substring(lastFromPosition));
		}

		return returnBuf.toString();
	}

	/**
	 * Escape Character 를 변환 한다.
	 *
	 * @param comment
	 * @return
	 */
	public static String escape(Object comment) {
		if (comment == null)
			return "";
		String result = String.valueOf(comment).trim();
		result = replaceInString(result, "\'", "＇");
		result = replaceInString(result, "\"", "“");
		result = replaceInString(result, "\n", "\\n");
		result = result.replace('\r', ' ');
		return result;
	}

	/**
	 * 문자열을 특정 delimiter를 이용하여 분리한다.
	 *
	 * @param delim
	 *            delimiter
	 * @param buf
	 *            분리할 문자열
	 * @return 분리된 문자열의 String[]
	 */
	public static String[] explode(String delim, String buf) {
		StringTokenizer token = new StringTokenizer(buf, delim);
		ArrayList<String> arrList = new ArrayList<String>();
		for (; token.hasMoreTokens();) {
			arrList.add(token.nextToken());
		}
		String retval[] = new String[arrList.size()];
		for (int i = 0; i < retval.length; i++) {
			retval[i] = arrList.get(i);
		}
		return retval;
	}

	/**
	 * 일정길이까지 문자를 자른다
	 *
	 * @param data
	 * @param len
	 * @return
	 */
	public static String limitLength(String data, int len) {
		return limitLength(data, len, "...");
	}

	/**
	 * 시작인덱스와 끝 인덱스 만큼 자른다.
	 *
	 * @param data
	 * @param startIdx
	 * @param endIdx
	 * @return
	 */
	public static String cutString(String data, int startIdx, int endIdx) {
		String result = "";
		if (data != null) {
			result = data.substring(startIdx, endIdx);
		}
		return result;
	}

	/**
	 * 일정 길이 까지 문자를 자른다.
	 *
	 * @param data
	 * @param len
	 * @param suffix
	 * @return
	 */
	public static String limitLength(String data, int len, String suffix) {
		if (data == null)
			return "";
		if (data.length() >= len) {
			data = data.substring(0, len - 3);
			data += suffix;
		}
		return data;
	}

	/**
	 * 특정 문자열을 원하는 pattern으로 바꾸어준다.
	 *
	 * @param str
	 *            원본 문자열
	 * @param oldStr
	 *            대체될 문자열 패턴
	 * @param newStr
	 *            대체할 문자열 패턴
	 * @return 결과 문자열
	 */
	public static String replaceInString(String str, String oldStr, String newStr) {
		if (str == null || oldStr == null || newStr == null) {
			return str;
		}

		int stdIdx = 0;
		int endIdx = str.indexOf(oldStr, stdIdx);
		if (endIdx < 0)
			return str; // 대체하고자 하는 문자열이 없을 경우
		StringBuffer strbuf = new StringBuffer();
		while (endIdx >= 0) {
			strbuf.append(str.substring(stdIdx, endIdx));
			strbuf.append(newStr);
			stdIdx = endIdx + oldStr.length();
			endIdx = str.indexOf(oldStr, stdIdx);
		}
		strbuf.append(str.substring(stdIdx));
		return strbuf.toString();
	}

	/**
	 * 특정 문자열 배열을 원하는 문자열 배열로 바꾸어준다.
	 *
	 * @param strbuff
	 *            원문 StringBuffer
	 * @param oldStr
	 *            원본 문자열 배열들
	 * @param newStr
	 *            바꾸는 문자열 배열들
	 */
	public static void replaceInString(StringBuffer strbuff, String[] oldStr, String[] newStr) {
		if (strbuff == null || oldStr == null || newStr == null) {
			return;
		}
		for (int i = 0; i < oldStr.length; i++) {
			int k = (newStr.length <= i) ? newStr.length - 1 : i;
			int sIdx = 0;
			int eIdx = strbuff.indexOf(oldStr[i], sIdx);
			while (eIdx >= 0) {
				strbuff.replace(eIdx, eIdx + oldStr[i].length(), newStr[k]);
				sIdx = eIdx + oldStr[i].length();
				eIdx = strbuff.indexOf(oldStr[i], sIdx);
			}
		}
	}

	/**
	 * Kilo Byte형태로 보여준다. 사용된 pattern은 #,##0.## 임(소수점 1째자리까지 나타나고 3자리마다 ,를 붙인다.)
	 */
	public static String showKb(Object obj) {
		if (obj == null)
			return "0";
		String str = String.valueOf(obj);
		if (str.equals("") || str.equals("0"))
			return "0";
		try {
			return toNumberString(String.valueOf(((Double.valueOf(str)).doubleValue()) / (1024)), "#,##0.##");
		} catch (NumberFormatException e) {
			System.err.println("StrUtil.showKb(" + obj + ") " + e.getMessage());
			return "0";
		}
	}

	/**
	 * Mega Byte형태로 보여준다. 사용된 pattern은 #,##0.## 임(소수점 2째자리까지 나타나고 3자리마다 ,를 붙인다.)
	 */
	public static String showMb(Object obj) {
		if (obj == null)
			return "0";
		String str = String.valueOf(obj);
		if (str.equals("") || str.equals("0"))
			return "0";
		try {
			return toNumberString(String.valueOf(((Double.valueOf(str)).doubleValue()) / (1024 * 1024)), "#,##0.##");
		} catch (NumberFormatException e) {
			System.err.println("StrUtil.showMb(" + obj + ") " + e.getMessage());
			return "0";
		}
	}

	/**
	 * 숫자를 세자리마다 ,를 붙여서 보여준다.
	 */
	public static String showNum(Object numO) {
		if (numO == null)
			return "0";
		String num = String.valueOf(numO);
		if (num.trim().equals(""))
			return "0";
		return toNumberString(num, null);
	}

	/**
	 * Object를 int로 변환
	 *
	 * @param data
	 * @return
	 */
	public static int toInt(Object data) {
		return toInt(data, 0);
	}

	/**
	 * Object를 int로 변환
	 *
	 * @param data
	 * @param defaultValue
	 * @return
	 */
	public static int toInt(Object data, int defaultValue) {
		int ret = defaultValue;
		if (data == null)
			return defaultValue;
		String value = String.valueOf(data).trim();
		if (value.length() == 0)
			return defaultValue;
		try {
			ret = Integer.parseInt(value);
		} catch (Exception e) {
			System.err.println("StrUtil.toInt(" + data + ", " + defaultValue + ") " + e);
		}
		return ret;
	}

	/**
	 * Object를 long으로 변환
	 *
	 * @param data
	 * @return
	 */
	public static long toLong(Object data) {
		return toLong(data, 0);
	}

	/**
	 * Object를 long으로 변환
	 *
	 * @param data
	 * @param defaultValue
	 * @return
	 */
	public static long toLong(Object data, long defaultValue) {
		long ret = defaultValue;
		if (data == null)
			return defaultValue;
		String value = String.valueOf(data).trim();
		if (value.length() == 0)
			return defaultValue;
		try {
			ret = Long.parseLong(value);
		} catch (Exception e) {
			System.err.println("StrUtil.toLong(" + data + ", " + defaultValue + ") " + e);
		}
		return ret;
	}

	/**
	 * Object를 double으로 변환
	 *
	 * @param data
	 * @return
	 */
	public static double toDouble(Object data) {
		return toDouble(data, 0);
	}

	/**
	 * Object를 double으로 변환
	 *
	 * @param data
	 * @param defaultValue
	 * @return
	 */
	public static double toDouble(Object data, double defaultValue) {
		double ret = defaultValue;
		if (data == null)
			return defaultValue;
		String value = String.valueOf(data).trim();
		if (value.length() == 0)
			return defaultValue;
		try {
			ret = Double.parseDouble(value);
		} catch (Exception e) {
			System.err.println("StrUtil.toDouble(" + data + ", " + defaultValue + ") " + e);
		}
		return ret;
	}

	/**
	 * 일정 길이만큼 문자를 나눔, 어절 단위로 일정길이 안쪽에서 짜름
	 */
	public static String[] subStrByBlk(String str, int len) {
		Vector<StringBuffer> tmpStrVec = new Vector<StringBuffer>();
		String tmpStr = null;
		StringBuffer strbuff = new StringBuffer();
		int cntLength = 0;

		StringTokenizer strToken = new StringTokenizer(str, " ");
		while (strToken.hasMoreTokens()) {
			tmpStr = strToken.nextToken();
			cntLength += tmpStr.length() + 1;

			if (cntLength <= len) {
				strbuff.append(tmpStr + " ");
			} else {
				tmpStrVec.add(strbuff);
				strbuff = new StringBuffer();
				strbuff.append(tmpStr + " ");
				cntLength = tmpStr.length() + 1;
			}
		}
		// 마지막 문자열을 삽입
		tmpStrVec.add(strbuff);

		String[] retStr = new String[tmpStrVec.size()];
		for (int i = 0; i < tmpStrVec.size(); i++) {
			retStr[i] = tmpStrVec.get(i).toString();
		}

		return retStr;
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat();

	/**
	 * Converts a String to a Date, using the specified pattern. (see
	 * java.text.SimpleDateFormat for pattern description)
	 *
	 * @param dateString
	 *            the String to convert
	 * @param dateFormatPattern
	 *            the pattern
	 * @return the corresponding Date
	 * @exception ParseException
	 *                , if the String doesn't match the pattern
	 */
	public static Date toDate(String dateString, String dateFormatPattern) throws ParseException {
		Date date = null;
		if (dateFormatPattern == null) {
			dateFormatPattern = "yyyy-MM-dd";
		}
		synchronized (dateFormat) {
			dateFormat.applyPattern(dateFormatPattern);
			dateFormat.setLenient(false);
			date = dateFormat.parse(dateString);
		}
		return date;
	}

	/**
	 * 일정한 패턴으로 날짜를 변환한다. example) toDateString("200205111230",
	 * "yyyy년 MM월 dd일 HH시 mm분"); --> result는 2002년 05월11일 12시 30분
	 *
	 * @param data
	 *            변환할 날짜 (반드시 공백없이 8~14자리 숫자로만 되어야한다.)
	 * @param format
	 *            변환할 날짜 패턴 형식
	 * @return 변환된 형식의 값
	 */
	public static String toDateString(String data, String format) {
		String returnVal = "";

		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		if (data == null || data.trim().length() < 8)
			return "";
		data = data.trim();

		try {
			Calendar cal = null;
			if (data.length() >= 14) {
				cal = new GregorianCalendar(Integer.parseInt(data.substring(0, 4)), Integer.parseInt(data.substring(4, 6)) - 1, Integer.parseInt(data
						.substring(6, 8)), Integer.parseInt(data.substring(8, 10)), Integer.parseInt(data.substring(10, 12)), Integer.parseInt(data.substring(
						12, 14)));

			} else if (data.length() >= 12) {
				cal = new GregorianCalendar(Integer.parseInt(data.substring(0, 4)), Integer.parseInt(data.substring(4, 6)) - 1, Integer.parseInt(data
						.substring(6, 8)), Integer.parseInt(data.substring(8, 10)), Integer.parseInt(data.substring(10, 12)));

			} else if (data.length() >= 10) {
				cal = new GregorianCalendar(Integer.parseInt(data.substring(0, 4)), Integer.parseInt(data.substring(4, 6)) - 1, Integer.parseInt(data
						.substring(6, 8)), Integer.parseInt(data.substring(8, 10)), 0);

			} else {
				cal = new GregorianCalendar(Integer.parseInt(data.substring(0, 4)), Integer.parseInt(data.substring(4, 6)) - 1, Integer.parseInt(data
						.substring(6, 8)));
			}

			SimpleDateFormat sdf = new SimpleDateFormat(format);
			returnVal = sdf.format(cal.getTime());
		} catch (Exception e) {
			System.err.println("StrUtil.toDateString() " + e.getMessage());
		}

		return returnVal;
	}

	/**
	 * 문자열을 html 형식으로 변환한다. ' --> &#39; " --> &#34; \n --> <BR>
	 * \n \t --> &nbsp;&nbsp;&nbsp;&nbsp; < --> &lt; > --> &gt; & --> &amp;
	 */
	public static String toHTMLString(String in) {
		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'') {
				out.append("&#39;");
			} else if (c == '\"') {
				out.append("&#34;");
			} else if (c == '\n') {
				out.append("<BR>\n");
			} else if (c == '\t') {
				out.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			} else if (c == '<') {
				out.append("&lt;");
			} else if (c == '>') {
				out.append("&gt;");
			} else if (c == '&') {
				out.append("&amp;");
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	private static DecimalFormat numberFormat = new DecimalFormat();

	/**
	 * Converts a String to a Number, using the specified pattern. (see
	 * java.text.NumberFormat for pattern description)
	 *
	 * @param numString
	 *            the String to convert
	 * @param numFormatPattern
	 *            the pattern
	 * @return the corresponding Number
	 * @exception ParseException
	 *                , if the String doesn't match the pattern
	 */
	public static Number toNumber(String numString, String numFormatPattern) throws ParseException {
		Number number = null;
		if (numFormatPattern == null) {
			numFormatPattern = "######.##";
		}
		synchronized (numberFormat) {
			numberFormat.applyPattern(numFormatPattern);
			number = numberFormat.parse(numString);
		}
		return number;
	}

	/**
	 * 일정한 패턴으로 숫자를 변환 한다. example) toNumberString("12345.6789", "#,##.##"); -->
	 * result는 1,23,45.68
	 *
	 * @param data
	 *            변환할 수
	 * @param format
	 *            변환할 패턴 형식
	 * @return 변환된 형식의 값
	 */
	public static String toNumberString(String data, String format) {
		String returnVal = "";

		if (format == null)
			format = "#,###";
		if (data == null || data.trim().equals(""))
			return "";
		try {
			DecimalFormat df = new DecimalFormat(format);
			if (Double.parseDouble(data) == 0)
				returnVal = df.format(Math.abs(Double.parseDouble(data)));
			else
				returnVal = df.format(Double.parseDouble(data));
		} catch (Exception e) {
			System.err.println("StrUtil.toNumberString() " + e.getMessage());
		}

		return returnVal;
	}

	/**
	 * content를 일정한 길이로... example) toNumberString("12345.6789", "#,##.##"); -->
	 * result는 1,23,45.68
	 *
	 * @param str
	 * @param columnSize
	 * @return 변환된 형식의 값
	 */
	public static final String[] toStringArray(String str, int columnSize) {
		StringBuffer sb = new StringBuffer();
		ArrayList<String> al = new ArrayList<String>();
		String c = null;
		int size = 0;
		int len = 0;
		for (int i = 0; i < str.length(); i++) {
			c = String.valueOf(str.charAt(i));
			len = c.getBytes().length;
			if (len == 2)
				size += 2;
			else
				size++;
			if (size > columnSize) {
				al.add(sb.toString());
				sb = new StringBuffer();
				size = len;
			}
			sb.append(c);
		}

		if (sb.length() > 0)
			al.add(sb.toString());
		String re[] = new String[al.size()];
		for (int i = 0; i < re.length; i++)
			re[i] = al.get(i).toString();

		return re;
	}

	/**
	 * 이메일 템플릿의 url content html을 읽어 온다 작성 날짜: (2004-05-30 오전 12:45:58)
	 *
	 * @exception java.lang.Exception
	 *                예외 설명.
	 */
	public static String readURL(String urlpath) {
		StringBuffer buff = new StringBuffer();
		try {
			URL url = new URL(urlpath);
			InputStream stream = url.openStream();
			InputStreamReader input = new InputStreamReader(stream, "euc-kr");
			BufferedReader in = new BufferedReader(input);

			String line;

			for (; (line = in.readLine()) != null;) {
				buff.append(line);
			}

		} catch (MalformedURLException ex2) {
			ex2.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return buff.toString();
	}

	public static String round(String number, String decimalLength) {
		double input = Double.parseDouble(number);
		double length = Double.parseDouble(decimalLength);
		double result = Math.round(input * Math.pow(10, length)) / Math.pow(10, length);
		if (length == 0) {
			return String.valueOf(new Double(result).intValue());
		} else {
			return String.valueOf(result);
		}
	}

	/**
	 * 소수점 없이 정수 반올림한다.
	 *
	 * @param denominator
	 *            분자
	 * @param nominator
	 *            분모
	 * @return
	 */
	public static long round(long denominator, long nominator) {
		if (nominator == 0)
			return 0;
		long result = Math.round(denominator / (double) nominator);
		return result;
	}

	/**
	 * YN을 true나 false로 변환 true나 false를 YN으로 변환
	 *
	 * @param String
	 * @return String
	 */
	public static String convertYesOrNo(String yesOrNo) {
		if (yesOrNo == null)
			return "";
		else if (yesOrNo.equals(""))
			return "N";
		else if (yesOrNo.equals("Y"))
			return "true";
		else if (yesOrNo.equals("N"))
			return "false";
		else if (yesOrNo.equals("true"))
			return "Y";
		else if (yesOrNo.equals("false"))
			return "N";
		else
			return "";
	}

	/**
	 * 문자열의 Null 여부 체크
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		boolean strIsNull = false;

		if (str == null || str.length() < 1) {
			strIsNull = true;
		}

		return strIsNull;
	}

	public static boolean isNotNull(String str) {
		boolean strIsNotNull = false;

		if (str == null || str.length() < 1) {
			strIsNotNull = false;
		} else {
			strIsNotNull = true;
		}

		return strIsNotNull;
	}

	public static int toInteger(String source) {
		int target = 0;
		try {
			target = Integer.valueOf(source);
		} catch (NumberFormatException e) {
		}

		return target;
	}

	public static long toLong(String source) {
		long target = 0;
		try {
			target = Long.valueOf(source);
		} catch (NumberFormatException e) {
		}

		return target;
	}

	public static String removeJsonNotation(String src) {
		src = StringUtil.replaceInString(src, "{", "");
		src = StringUtil.replaceInString(src, "}", "");
		src = StringUtil.replaceInString(src, "\\", "");
		src = StringUtil.replaceInString(src, "\"", "");

		return src;
	}

	public static String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
	public static String getRandomString(int length){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, length);
    }
}