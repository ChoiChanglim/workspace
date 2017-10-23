package com.nhn.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author 박근희
 */
public class DateUtils {
	public static final int TIMESTAMP_FOR_A_DAY = 1000 * 60 * 60 * 24;

	public static Date convertUTCtoLocalTime(Date utcdate) {
		TimeZone tz = TimeZone.getDefault();
		long milliseconds = utcdate.getTime();
		int offset = tz.getOffset(milliseconds);
		Date localDate = new Date(milliseconds + offset);

		return localDate;
	}

	public static Date convertLocalTimetoUTC(Date localdate) {
		TimeZone tz = TimeZone.getDefault();
		long milliseconds = localdate.getTime();
		int offset = tz.getOffset(milliseconds);

		Date utcDate = new Date(milliseconds + offset);

		return utcDate;
	}

	/**
	 * 새로운 date formatter 적용하기
	 */
	public static String applyNewForamt(String date, SimpleDateFormat oldFormatter, SimpleDateFormat newFormatter) {
		Calendar cal = Calendar.getInstance();

		try {
			cal.setTime(oldFormatter.parse(date));
			String result = newFormatter.format(cal.getTime());
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 8자리 날짜를 정규화된 날짜로 변경. 20121013 -> 2012-10-13 00:00:00
	 */
	public static String convert8FormatToRegular(String oldDate) {
		return applyNewForamt(oldDate, new SimpleDateFormat("yyyyMMdd"), new SimpleDateFormat("yyyy-MM-dd 00:00:00"));
	}

	/**
	 * 8자리 날짜를 정규화된 날짜로 변경. 20121013 -> 2012-10-13 00:00:00
	 */
	public static String convert8FormatToRegularEndDate(String oldDate) {
		return convert8FormatToRegular(addDate(oldDate, new SimpleDateFormat("yyyyMMdd"), 1));
	}

	/**
	 * yyyy-MM-dd 17:00:00 형식으로 입력된 date를 timestamp로 변환하여 return 함
	 */
	public static long getTimestampFromDate(String dateFormat) { //yyyy-MM-dd 17:00:00
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long result = 0;
		try {
			Date date1 = format.parse(dateFormat);
			Timestamp timestamp1 = new Timestamp(date1.getTime());
			result = timestamp1.getTime();
		} catch (Exception e) {
			return 0;
		}

		return result;
	}

	/**
	 * 10자리 unix timestamp를 일반 date format으로 return
	 */
	public static String convertTimestamp10DigitToDate(long time) {
		String result = "";

		if (time < 1) {
			return "";
		}

		try {
			Date date = new Date();
			date.setTime(time * 1000);

			SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = newFormat.format(date);
		} catch (Exception e) {
			return "";
		}

		return result;
	}

	/**
	 * 10자리 UTC timestamp를 일반 날짜로 변환. 1352967428 -> 2012-11-15 08:17:08
	 */
	public static String convertTimestamp10DigitUtcToDate(long time) {
		String result = "";

		if (time < 1) {
			return "";
		}

		time = time * 1000;

		try {
			TimeZone tz = TimeZone.getDefault();
			int offset = tz.getOffset(time);

			//Date date = new Date(time - offset);
			Date date = new Date(time);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = format.format(date);
		} catch (Exception e) {
			return "";
		}

		return result;
	}

	/**
	 * 2012-10-11 11:22:33 형식의 날짜를 UTC 10자리 timestamp로 변환
	 */
	public static long convertNormalDateToTimestamp10DigitUtc(String date) {
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long result = 0;
		try {
			//dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
			result = dfm.parse(date).getTime();
			result = result / 1000;
		} catch (Exception e) {
			return 0;
		}

		return result;
	}

	/**
	 * 현재 unix time stamp return
	 */
	public static long getCurrentUnixTimestamp() {
		long result = System.currentTimeMillis() / 1000;
		return result;
	}

	/**
	 * 기준시간에서 day만큼 더한 날짜를 return 함
	 */
	public static String addDate(String date, SimpleDateFormat format, int dayCnt) {
		Date oldDate = new Date();
		long curTimeStamp = 0;

		try {
			oldDate = format.parse(date);
		} catch (ParseException e) {
			return date;
		}

		curTimeStamp = oldDate.getTime();
		curTimeStamp = curTimeStamp + (TIMESTAMP_FOR_A_DAY * dayCnt);

		String result = "";
		try {
			result = format.format(new Date(curTimeStamp));
		} catch (Exception e) {
			return date;
		}

		return result;
	}

	/**
	 * 현재시간을 원하는 스트링으로 변환
	 * @param pattern
	 * @param adjustMonth
	 * @return adjustMonth이 0이면 "" 리턴
	 */
	public static String convertFormaterByCurrentTime(String pattern, int adjustMonth) {
		if (adjustMonth == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, adjustMonth);
		try {
			String result = sdf.format(cal.getTime());
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	public static String getYearOfMonday() {
		Calendar rightNow = Calendar.getInstance(Locale.getDefault());
		rightNow.setFirstDayOfWeek(Calendar.MONDAY);
	 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(rightNow.getTime());
	}

	public static int getWeekOfYear(){
		Calendar rightNow = Calendar.getInstance(Locale.getDefault());
		rightNow.setFirstDayOfWeek(Calendar.MONDAY);
		int week_of_year = rightNow.get(Calendar.WEEK_OF_YEAR);
		return week_of_year;
	}


}
