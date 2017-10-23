package my.random.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 날짜와 시간에 관련된 유틸리티 클래스
 * 
 * @version 1.0
 */
public class DateUtil {

    /** 1일을 밀리초로 환산한 값(86400000 = 24*60*60*1000) */
    public static final long MILLISECOND_OF_DAY = 24 * 60 * 60 * 1000;
    /** 현재로케일에서 그리니치기준시와의 차이를 밀리세컨드로 환산한 값. */
    public static final long GMT_RAW_OFFSET = TimeZone.getDefault().getRawOffset();
    /** 기본형식(yyyyMMddHHmmss) */
    public static final String DEFAULT_FORMAT = "yyyyMMddHHmmss";
    /** 날짜 기본형식(yyyyMMdd) */
    public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";
    /** 시간 기본형식(HHmmss) */
    public static final String DEFAULT_TIME_FORMAT = "HHmmss";
    /** 초제외 시간 기본형식(HHmm) */
    public static final String DEFAULT_HHMM_FORMAT = "HHmm";
    /** 초기일자("0001-01-01") */
    public static final String MINIMUM_DATE = "00010101";
    /** 최종일자("9999-12-31") */
    public static final String MAXIMUM_DATE = "99991231";
    /** 초기일자("01-01") */
    public static final String MINIMUN_MMDD = "0101";
    /** 최종일자("12-31") */
    public static final String MAXIMUM_MMDD = "1231";
    /** 년도 포맷 */
    public static final String YEAR_FORMAT = "yyyy";
    /** 월 포멧 */
    public static final String MONTH_FORMAT = "MM";
    /** 일 포멧 */
    public static final String DATE_FORMAT = "dd";
    /** 요일(한글명) */
    private static final String[] DAY_OF_WEEKS_KOREAN = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
    /** 요일(축약 한글명) */
    private static final String[] DAY_OF_WEEKS_SHORT_KOREAN = { "일", "월", "화", "수", "목", "금", "토" };
    /** 요일(영문명) */
    private static final String[] DAY_OF_WEEKS_ENGLISH = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

    private DateUtil() {
    }

    /**
     * 기본 포멧(yyyyMMdd)으로 된 현재 날짜를 <code>java.lang.String</code>으로 조회한다.
     * <p>
     * 수정전 : currentDateString(), getToday()
     * 
     * @return
     */
    public static String getCurrentDateString() {
        return getCurrentDateString(DEFAULT_DATE_FORMAT);
    }

    /**
     * 입력 포멧으로 된 현재 날짜를 <code>java.lang.String</code>으로 조회한다.
     * <p>
     * 수정전 : currentDateStringWithFormat(String afterFormat)
     * 
     * @param dateFormat
     *            변경할 포멧
     * @return
     */
    public static String getCurrentDateString(String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(new Date());
    }

    /**
     * 기본 포멧으로 된 현재 날짜를 <code>java.util.Date</code>으로 조회한다.
     * 
     * @return
     */
    public static Date getCurrentDate() {
        return getCurrentDate(DEFAULT_DATE_FORMAT);
    }

    /**
     * 입력 포멧으로 된 현재 날짜를 <code>java.util.Date</code>으로 조회한다.
     * 
     * @return
     */
    public static Date getCurrentDate(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        df.setCalendar(calendar);
        return calendar.getTime();
    }

    /**
     * 기본 포멧으로 된 현재 날짜를 <code>java.lang.String</code>으로 조회한다.
     * <p>
     * 수정전 : getCurrentDateTime()
     * 
     * @return
     */
    public static String getCurrentStringDateTime() {
        return getCurrentStringDateTime(DEFAULT_FORMAT);
    }

    /**
     * 입력 포멧으로 된 현재 날짜를 <code>java.lang.String</code>으로 조회한다.
     * 
     * @return
     */
    public static String getCurrentStringDateTime(String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(new Date());
    }

    /**
     * 기본 포멧으로 된 현재 날짜와시간을 <code>java.util.Date</code>으로 조회한다.
     * 
     * @return
     */
    public static Date getCurrentDateTime() {
        return getCurrentDateTime(DEFAULT_FORMAT);
    }

    /**
     * 입력 포멧으로 된 현재 날짜와시간을 <code>java.util.Date</code>으로 조회한다.
     * 
     * @return
     */
    public static Date getCurrentDateTime(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        df.setCalendar(calendar);
        return calendar.getTime();
    }

    /**
     * 기본 포멧으로 된 현재 시간을 java.lang.String으로 조회한다.
     * 
     * @return
     */
    public static String getCurrentTimeString() {
        Date toDate = getCurrentDateTime(DEFAULT_TIME_FORMAT);
        return convertDateToString(toDate, DEFAULT_TIME_FORMAT);
    }

    /**
     * HH:mm 형식의 현재 시간을 조회한다.
     * 
     * @return
     */
    public static String getCurrentHHmmString() {
        Date toDate = getCurrentDateTime(DEFAULT_HHMM_FORMAT);
        return convertDateToString(toDate, DEFAULT_HHMM_FORMAT);
    }

    /**
     * 기준일에서 년수를 증감한다. 입력일자가 말일자이면 증감년일자도 말일자로 설정한다.
     * 
     * @param date
     *            기준일자
     * @param nYears
     *            증감(+ or -) 년수
     * @return
     */
    public static Date addYear(Date date, int nYears) {
        return addYear(date, nYears, true);
    }

    /**
     * 기준일에서 년수를 증감한다. 입력일자가 말일자이면 증감년일자도 말일자로 설정한다.
     * 
     * @param date
     *            기준일자
     * @param dateFormat
     *            날짜포멧
     * @param nYears
     *            증감(+ or -) 년수
     * @return
     */
    public static String addYear(String currentDate, String dateFormat, int nYears) {
        Date date = convertStringToDate(currentDate, dateFormat);
        Date toDate = addYear(date, nYears, true);
        return convertDateToString(toDate, dateFormat);
    }

    /**
     * 기준일에서 년수를 증감한다. 입력일자가 말일자이면 증감년일자도 말일자로 설정한다.
     * 
     * @param currentDate
     *            기준일자
     * @param nYears
     *            증감(+ or -) 년수
     * @return
     */
    public static String addYear(String currentDate, int nYears) {
        return addYear(currentDate, DEFAULT_DATE_FORMAT, nYears);
    }

    /**
     * 기준일에서 년수를 증감한다. 말일자검사여부가 true이고 입력일자가 말일자이면 증감년일자도 말일자로 설정한다.
     * 
     * @param date
     *            기준일자
     * @param nYears
     *            증감(+ or -) 년수
     * @param lastDayCheckupYn
     *            말일자검사여부
     * @return
     */
    public static Date addYear(Date date, int nYears, boolean lastDayCheckupYn) {
        // 당해년도이면 입력된 기준일자를 반환한다.
        if (0 == nYears)
            return (Date) date.clone();

        // 말일자검사여부가 true이고 입력일자가 말일자이면 증감년일자도 말일자로 설정한다.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (lastDayCheckupYn && isLastDay(calendar)) {
            calendar.add(Calendar.YEAR, nYears);
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        } else {
            calendar.add(Calendar.YEAR, nYears);
        }

        // 서머타임제 적용을 이유로 시간을 0으로 설정
        //calendar.set(Calendar.HOUR_OF_DAY, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 기준일에서 월수를 증감한다.
     * 
     * @param date
     *            기준일자
     * @param nMonths
     *            증감(+ or -) 월수
     * @return
     */
    public static Date addMonth(Date date, int nMonths) {
        return addMonth(date, nMonths, false);
    }

    /**
     * 기준일에서 년수를 증감한다. 입력일자가 말일자이면 증감년일자도 말일자로 설정한다.
     * 
     * @param currentDate
     *            기준일자
     * @param dateFormat
     *            날짜포멧
     * @param nYears
     *            증감(+ or -) 년수
     * @return
     */
    public static String addMonth(String currentDate, String dateFormat, int nYears) {
        Date date = convertStringToDate(currentDate, dateFormat);
        Date toDate = addMonth(date, nYears, true);
        return convertDateToString(toDate, dateFormat);
    }

    /**
     * 기준일에서 년수를 증감한다. 입력일자가 말일자이면 증감년일자도 말일자로 설정한다.
     * 
     * @param currentDate
     *            기준일자
     * @param nYears
     *            증감(+ or -) 년수
     * @return
     */
    public static String addMonth(String currentDate, int nYears) {
        return addMonth(currentDate, DEFAULT_DATE_FORMAT, nYears);
    }

    /**
     * 기준일에서 월수를 증감한다. 말일자검사여부가 true이고 입력일자가 말일자이면 증감월일자도 말일자로 설정한다.
     * 
     * @param date
     *            기준일자
     * @param nMonths
     *            증감(+ or -) 월수
     * @param lastDayCheckupYn
     *            말일자검사여부
     * @return
     */
    public static Date addMonth(Date date, int nMonths, boolean lastDayCheckupYn) {
        // 당월이면 입력된 기준일자를 반환한다.
        if (0 == nMonths)
            return (Date) date.clone();

        // 말일자검사여부가 true이고 입력일자가 말일자이면 증감월일자도 말일자로 설정한다.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (lastDayCheckupYn && isLastDay(calendar)) {
            calendar.add(Calendar.MONTH, nMonths);
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        } else {
            calendar.add(Calendar.MONTH, nMonths);
        }

        // 서머타임제 적용을 이유로 시간을 0으로 설정
        //calendar.set(Calendar.HOUR_OF_DAY, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 날짜 추가
     * 
     * @param date
     *            : 바꿀날짜
     * @param nDays
     *            : 추가일자
     * @return Date
     */
    public static Date addDate(Date date, int nDays) {
        // 당일이면 기준일자를 반환한다.
        if (0 == nDays)
            return (Date) date.clone();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, nDays);

        // 서머타임제 적용을 이유로 시간을 0으로 설정
        //calendar.set(Calendar.HOUR_OF_DAY, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 날짜 추가
     * 
     * @param date
     *            : 바꿀날짜
     * @param dateFormat
     *            : 입력 포멧
     * @param nDays
     *            : 추가일자
     * @return Date
     */
    public static Date addDate(Date date, String dateFormat, int nDays) {
        String toDate = convertFormatDateToString(date, dateFormat);

        return addDate(convertStringToDate(toDate, dateFormat), nDays);
    }

    /**
     * 날짜 추가
     * 
     * @param date
     *            : 바꿀날짜
     * @param dateFormat
     *            : 입력 포멧
     * @param nDays
     *            : 추가일자
     * @return String
     */
    public static String addDate(String date, String dateFormat, int nDays) {
        Date toDate = addDate(convertStringToDate(date, dateFormat), nDays);
        return convertDateToString(toDate, dateFormat);
    }

    /**
     * 기본 포멧을 이요한 날짜 추가
     * 
     * @param date
     * @param nDays
     * @return
     */
    public static String addDate(String date, int nDays) {
        return addDate(date, DEFAULT_DATE_FORMAT, nDays);
    }

    /**
     * 월,날일 추가 및 말일자 여부가 true 이면 추가개월을 말일자를 적용
     * 
     * @param date
     *            : 바꿀날짜
     * @param nMonth
     *            : 추가개월
     * @param nDate
     *            : 추가일자
     * @param isLastDay
     *            : 말일자 여부
     * @return Date
     */
    public static Date addMonth(Date date, int nMonth, int nDate, boolean isLastDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (isLastDay && isLastDay(calendar)) {
            calendar.add(Calendar.MONTH, nMonth);
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else {
            calendar.add(Calendar.MONTH, nMonth);
            calendar.add(Calendar.DATE, nDate);
        }

        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 월,날일 추가 및 말일자 여부가 true 이면 추가개월을 말일자를 적용
     * 
     * @param date
     *            : 바꿀날짜
     * @param basisDate
     *            : 기존날짜
     * @param nMonth
     *            : 추가개월
     * @param nDate
     *            : 추가일자
     * @param isLastDay
     *            : 말일자 여부
     * @return Date
     */
    public static Date addMonth(Date date, Date basisDate, int nMonth, int nDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (isLastDay(basisDate)) {
            calendar.add(Calendar.MONTH, nMonth);
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else {
            calendar.add(Calendar.MONTH, nMonth);
            calendar.add(Calendar.DATE, nDate);
        }

        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 기준일자가 해당월의 말일자인지 여부를 반환한다.
     * 
     * @param basisDate
     *            기준일자
     * @return 기준일자가 해당월의 말일자인지 여부를 반환한다.
     */
    public static boolean isLastDay(Date basisDate) {
        Calendar basisCal = Calendar.getInstance();
        basisCal.setTime(basisDate);
        return isLastDay(basisCal);
    }

    /**
     * 기준일자가 해당월의 말일자인지 여부를 반환한다.
     * 
     * @param basisCal
     *            기준달력
     * @return 기준일자가 해당월의 말일자인지 여부를 반환한다.
     */
    public static boolean isLastDay(Calendar basisCal) {
        return (basisCal.get(Calendar.DATE) == basisCal.getActualMaximum(Calendar.DATE));
    }

    /**
     * 말일자로 셋팅하여 반환
     * 
     * @param date
     * @return Date 말일자
     */
    public static Date setLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 말일자로 셋팅하여 반환
     * 
     * @param date
     * @param format
     *            입력포멧
     * @return String 말일자
     */
    public static String setLastDay(String date, String format) {
        Date toDate = convertStringToDate(date, format);
        Date lastDate = setLastDay(toDate);
        return convertDateToString(lastDate, format);
    }

    /**
     * 말일자로 셋팅하여 반환
     * 
     * @param date
     * @param format
     *            입력포멧
     * @return String 말일자
     */
    public static String setLastDay(String date) {
        Date toDate = convertStringToDate(date, DEFAULT_DATE_FORMAT);
        Date lastDate = setLastDay(toDate);
        return convertDateToString(lastDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 말일자로 셋팅하여 반환
     * 
     * @param date
     * @return String 말일자
     */
    public static String setLastDayByYearMonth(String date) {
        Date toDate = convertStringToDate(date, YEAR_FORMAT + MONTH_FORMAT);
        Date lastDate = setLastDay(toDate);
        return convertDateToString(lastDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 초일자로 셋팅하여 반환
     * 
     * @param date
     * @return Date 말일자
     */
    public static Date setFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 초일자 셋팅하여 반환
     * 
     * @param date
     * @param format
     *            입력포멧
     * @return String 말일자
     */
    public static String setFirstDay(String date, String format) {
        Date toDate = convertStringToDate(date, format);
        Date lastDate = setFirstDay(toDate);
        return convertDateToString(lastDate, format);
    }

    /**
     * 초일자로 셋팅하여 반환
     * 
     * @param date
     * @return String 말일자
     */
    public static String setFirstDayByYearMonth(String date) {
        Date toDate = convertStringToDate(date, YEAR_FORMAT + MONTH_FORMAT);
        Date lastDate = setFirstDay(toDate);
        return convertDateToString(lastDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 초일자 셋팅하여 기본포멧으로 반환
     * 
     * @param date
     * @return String 말일자
     */
    public static String setFirstDay(String date) {
        Date toDate = convertStringToDate(date, DEFAULT_DATE_FORMAT);
        Date lastDate = setFirstDay(toDate);
        return convertDateToString(lastDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 입력된 날짜가 '0001-01-01'와 같은 날짜인지 비교한다.
     * 
     * @param date
     *            비교할날짜
     * @return
     */
    public static boolean isFirstDate(String date) {
        return MINIMUM_DATE.equals(date);
    }

    /**
     * 기본포멧으로 날짜 포멧 변경
     * 
     * @param date
     * @return
     */
    public static String convertFormat(Date date) {
        return convertFormat(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 기본포멧으로 날짜 포멧 변경
     * 
     * @param date
     * @return
     */
    public static String convertFormat(String date, String beforeFormat) {
        Date toDate = convertStringToDate(date, beforeFormat);
        return convertFormat(toDate);
    }

    /**
     * 날짜 포멧 변경 수정전 : converDateString(String date, beforeFormat, afterFormat)
     * 
     * @param date
     * @param afterFormat
     * @return String
     */
    public static String convertFormat(String date, String beforeFormat, String afterFormat) {
        if (beforeFormat == null || beforeFormat.equals(afterFormat))
            return date;

        Date toDate = convertStringToDate(date, beforeFormat);
        SimpleDateFormat df = new SimpleDateFormat(afterFormat);
        return df.format(toDate);
    }

    /**
     * 날짜 포멧 변경 수정전 : converDateString(String date, afterFormat)
     * 
     * @param date
     * @param afterFormat
     * @return String
     */
    public static String convertFormat(Date date, String afterFormat) {
        SimpleDateFormat df = new SimpleDateFormat(afterFormat);
        return df.format(date);
    }

    /**
     * <code>java.util.Date</code> 에서 <code>java.lang.String</code>으로 날짜 포멧 변경
     * 
     * @param date
     * @param afterFormat
     * @return String
     */
    private static String convertFormatDateToString(Date date, String afterFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat(afterFormat);

        return df.format(calendar.getTime());
    }

    /**
     * 날짜 포멧 변경
     * 
     * @param date
     * @param beforeFormat
     * @return java.util.Date
     */
    public static Date convertFormatDate(Date date, String beforeFormat) {
        String toString = convertFormatDateToString(date, beforeFormat);
        return convertStringToDate(toString, beforeFormat);
    }

    /**
     * <code>java.util.Date</code> 에서 <code>java.lang.String</code>으로 날짜 포멧 변경
     * 
     * @param date
     * @param afterFormat
     * @return String
     */
    // public static String convertFormatString(String date, String afterFormat)
    // {
    // Date toDate = convertStringToDate(date, afterFormat);
    // SimpleDateFormat df = new SimpleDateFormat();
    // return df.format(toDate);
    // }

    /**
     * format형태인 String데이타를 java.util.Date로 변환
     * 
     * @param strDate
     *            : 변경대상이 되는 date
     * @param format
     *            : String의 포맷
     * @return java.util.Date
     */
    public static Date convertStringToDate(String strDate, String format) {
        if (strDate == null || strDate.length() == 0) {
            return null;
        }
        // if (strDate.length() != format.length()) {
        // return null;
        // }
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(format);
            return fmt.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 기본format형태인 String데이타를 java.util.Date로 변환
     * 
     * @param strDate
     *            : 변경대상이 되는 date
     * @return java.util.Date
     */
    public static Date convertStringToTime(String strDate) {
        if (strDate == null || strDate.equals("")) {
            return null;
        }
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(DEFAULT_FORMAT);
            return fmt.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 기본format형태인 String데이타를 java.util.Date로 변환
     * 
     * @param strDate
     *            : 변경대상이 되는 date
     * @return java.util.Date
     */
    public static Date convertStringToDate(String strDate) {
        if (strDate == null || strDate.equals("")) {
            return null;
        }
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            return fmt.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <code>java.util.Date</code>인 데이타를 format에 맞게
     * <code>java.lang.String</code>으로 변환
     * 
     * @param date
     *            변경대상이 되는 date
     * @param format
     *            String의 포맷
     * @return
     */
    public static String convertDateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 입력한 날짜가 오늘날짜 기준 이전날짜인지 여부 (오늘날짜 포함 안함)
     * 
     * @param date
     *            날짜
     * @return 여부
     */
    public static boolean isBeforeDate(Date date) {
        String currentDate = getCurrentDateString(DEFAULT_DATE_FORMAT);
        String compareDate = convertDateToString(date, DEFAULT_DATE_FORMAT);
        int compare = currentDate.compareTo(compareDate);
        if (compare > 0)
            return true;
        return false;
    }

    public static boolean isBeforeDate(String compareDate) {
        String currentDate = getCurrentDateString(DEFAULT_DATE_FORMAT);
        int compare = currentDate.compareTo(compareDate);
        if (compare > 0)
            return true;
        return false;
    }

    /**
     * 기준일자를 기준으로 비교대상일자가 이전 일자인지 여부 (오늘날짜 포함 안함)
     * 
     * @param baseDate
     *            기준일자
     * @param compareDate
     *            비교대상 일자
     * @return 이전 일자 여부
     */
    public static boolean isBeforeDate(Date baseDate, Date compareDate) {
        int compareResult = compareDate(baseDate, compareDate);
        return compareResult > 0 ? true : false;
    }

    /**
     * 기준일자를 기준으로 비교대상일자가 이전 일자인지 여부 (오늘날짜 포함 안함)
     * 
     * @param baseDate
     *            기준일자
     * @param compareDate
     *            비교대상 일자
     * @return 이전 일자 여부
     */
    public static boolean isBeforeDate(String baseDate, String compareDate) {
        int compareResult = compareDate(baseDate, compareDate);
        return compareResult > 0 ? true : false;
    }

    /**
     * 입력한 날짜가 오늘날짜 기준 이후 날짜인지 여부 (오늘날짜 포함 안함)
     * 
     * @param date
     *            날짜
     * @return 여부
     */
    public static boolean isAfterDate(Date date) {
        String currentDate = getCurrentDateString(DEFAULT_DATE_FORMAT);
        String compareDate = convertDateToString(date, DEFAULT_DATE_FORMAT);
        int compare = currentDate.compareTo(compareDate);
        if (compare < 0)
            return true;
        return false;
    }

    /**
     * 입력한 날짜가 오늘날짜 기준 이후 날짜인지 여부 (오늘날짜 포함 안함)
     * 
     * @param date
     *            날짜
     * @return 여부
     */
    public static boolean isAfterDate(String date) {
        String currentDate = getCurrentDateString(DEFAULT_DATE_FORMAT);
        int compare = currentDate.compareTo(date);
        if (compare < 0)
            return true;
        return false;
    }

    /**
     * 오늘날짜인지 확인
     * 
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        String inputDate = convertDateToString(date, DEFAULT_DATE_FORMAT);
        return isToday(inputDate);
    }

    /**
     * 오늘날짜인지 확인
     * 
     * @param date
     * @return
     */
    public static boolean isToday(String date) {
        String systemDate = getCurrentDateString();
        return date.equals(systemDate) ? true : false;
    }

    /**
     * 기준일자를 기준으로 비교대상일자가 이후 일자인지 여부 (오늘날짜 포함 안함)
     * 
     * @param baseDate
     *            기준일자
     * @param compareDate
     *            비교대상 일자
     * @return 이후 일자 여부
     */
    public static boolean isAfterDate(Date baseDate, Date compareDate) {
        int compareResult = compareDate(baseDate, compareDate);
        return compareResult < 0 ? true : false;
    }

    /**
     * 기준일자를 기준으로 비교대상일자가 이후 일자인지 여부 (오늘날짜 포함 안함)
     * 
     * @param baseDate
     *            기준일자
     * @param compareDate
     *            비교대상 일자
     * @return 이후 일자 여부
     */
    public static boolean isAfterDate(String baseDate, String compareDate) {
        int compareResult = compareDate(baseDate, compareDate);
        return compareResult < 0 ? true : false;
    }

    /**
     * 두 일자를 비교한다.
     * 
     * @param baseDate
     *            기준일자
     * @param compareDate
     *            비교일자
     * @return 동일 : 0, 이전 일자이면 &gt;0, 이후 일자이면 &lt;0
     */
    public static int compareDate(Date baseDate, Date compareDate) {
        String standard = convertDateToString(baseDate, DEFAULT_DATE_FORMAT);
        String compare = convertDateToString(compareDate, DEFAULT_DATE_FORMAT);
        return standard.compareTo(compare);
    }

    /**
     * 두 일자를 비교한다.
     * 
     * @param baseDate
     *            기준일자
     * @param compareDate
     *            비교일자
     * @return 동일 : 0, 이전 일자이면 &gt;0, 이후 일자이면 &lt;0
     */
    public static int compareDate(String baseDate, String compareDate) {
        return baseDate.compareTo(compareDate);
    }

    /**
     * 현재일자의 영문요일명을 조회한다.
     * <p>
     * 수정전 : getDayStringOfWeek(String date)
     * 
     * @param dateString
     * @return
     */
    public static String getDayOfWeekEnglish(String dateString) {
        int dayOfWeek = getDayOfWeek(dateString);
        return DAY_OF_WEEKS_ENGLISH[dayOfWeek - 1];
    }

    /**
     * 현재일자의 한글요일명을 조회한다.
     * <p>
     * 수정전 : getDayStringOfWeek(String date)
     * 
     * @param dateString
     * @return
     */
    public static String getDayOfWeekKorean(String dateString) {
        int dayOfWeek = getDayOfWeek(dateString);
        return DAY_OF_WEEKS_KOREAN[dayOfWeek - 1];
    }

    /**
     * 현재일자의 축약형 한글요일명을 조회한다.
     * <p>
     * 수정전 : getDayStringOfWeek(String date)
     * 
     * @param dateString
     * @return
     */
    public static String getDayOfWeekShortKorean(String dateString) {
        int dayOfWeek = getDayOfWeek(dateString);
        return DAY_OF_WEEKS_SHORT_KOREAN[dayOfWeek - 1];
    }

    /**
     * 날짜의 요일을 리턴한다. "20080203(일요일) ==> 1 "20080209(토요일) ==> 7
     * 
     * @param dateString
     * @return
     */
    public static int getDayOfWeek(String dateString) {
        int result = 0;
        Date date = convertStringToDate(dateString, DEFAULT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance(Locale.KOREAN);
        calendar.setTime(date);
        result = calendar.get(Calendar.DAY_OF_WEEK);

        return result;
    }

    /**
     * 시작일에서 종료일까지 목록으로 조회
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getDateStrings(String startDate, String endDate) {
        List<String> dayList = new ArrayList<String>();
        int days = getBetweenDays(startDate, endDate, DEFAULT_DATE_FORMAT);
        for (int i = 0; i < days; i++) {
            dayList.add(addDate(startDate, i));
        }
        return dayList;
    }

    /**
     * 시작일에서 종료일까지 목록으로 조회
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getDateIncludeLastDayStrings(String startDate, String endDate) {
        List<String> dayList = new ArrayList<String>();
        int days = getBetweenDays(startDate, endDate, DEFAULT_DATE_FORMAT);
        days++; // 마지막일을 포함
        for (int i = 0; i < days; i++) {
            dayList.add(addDate(startDate, i));
        }
        return dayList;
    }

    /**
     * 특정기간의 특정 요일에 해당하는 날짜 목록 조회
     * 
     * @param startDate
     * @param endDate
     * @param weekDays
     * @return
     */
    public static List<String> getDateStringsInWeekDays(String startDate, String endDate, int[] weekDays) {
        List<String> weekDayStrs = new ArrayList<String>();

        int dayCount = getBetweenDays(startDate, endDate);
        int startWeek = getDayOfWeek(startDate);

        for (int i = 0; i < dayCount; i++) {
            int dayWeek = (startWeek + i) % 7;
            if (dayWeek == 0)
                dayWeek = 7;
            for (int j = 0; j < weekDays.length; j++) {
                if (dayWeek == weekDays[j]) {
                    weekDayStrs.add(DateUtil.addDate(startDate, i));
                }
            }
        }

        return weekDayStrs;
    }

    /**
     * 년월 추출
     * 
     * @param date
     * @return int
     */
    public static int getYearMonth(Date date) {
        String dateStr = convertDateToString(date, DEFAULT_DATE_FORMAT);
        return Integer.parseInt(dateStr.substring(0, 6));
    }

    /**
     * 연도 추출
     * 
     * @param date
     * @return int
     */
    public static int getYear(Date date) {
        return getYear(convertDateToString(date, DEFAULT_DATE_FORMAT));
    }

    /**
     * 연도 추출
     * 
     * @param date
     * @return int
     */
    public static int getYear(String date) {
        return Integer.parseInt(date.substring(0, 4));
    }

    /**
     * 월 추출
     * 
     * @param date
     * @return int
     */
    public static int getMonth(Date date) {
        return getMonth(convertDateToString(date, DEFAULT_DATE_FORMAT));
    }

    /**
     * 월 추출
     * 
     * @param date
     * @return int
     */
    public static int getMonth(String date) {
        return Integer.parseInt(date.substring(4, 6));
    }

    /**
     * 일수 추출
     * 
     * @param date
     * @return int
     */
    public static int getDay(Date date) {
        return getDay(convertDateToString(date, DEFAULT_DATE_FORMAT));
    }

    /**
     * 일 추출
     * 
     * @param date
     * @return int
     */
    public static int getDay(String date) {
        return Integer.parseInt(date.substring(6));
    }

    /**
     * 동일한 날짜 비교
     * 
     * @param oneDate
     * @param twoDate
     * @return
     */
    public static boolean isSameDate(Date oneDate, Date twoDate) {
        return oneDate.getTime() == twoDate.getTime() ? true : false;
    }

    /**
     * 동일한 날짜 비교
     * 
     * @param oneDate
     * @param twoDate
     * @return
     */
    public static boolean isSameDate(String oneDate, String twoDate) {
        return oneDate.equals(twoDate);
    }

    /**
     * 시작일시와 종료일시의 날짜 차이를 초로 계산
     * 
     * @return 종료일 - 시작일
     */
    public static int getBetweenTimes(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일자, 종료일자는 null이 아니어야합니다.");
        }

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();

        int secs = (int) (endTime / 1000) - (int) (startTime / 1000);

        return secs;
    }

    /**
     * 시작일시와 종료일시의 날짜 차이를 초로 계산
     * 
     * @return 종료일 - 시작일
     */
    public static int getBetweenTimes(String startDate, String endDate) {

        Date start = convertStringToTime(startDate);
        Date end = convertStringToTime(endDate);

        return getBetweenTimes(start, end);
    }

    /**
     * 시작일시와 종료일시의 날짜 차이를 일수로 계산
     * 
     * @return 종료일 - 시작일
     */
    public static int getBetweenDays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일자, 종료일자는 null이 아니어야합니다.");
        }

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();

        int days = (int) (endTime / MILLISECOND_OF_DAY) - (int) (startTime / MILLISECOND_OF_DAY);

        return days;
    }

    /**
     * 시작일시와 종료일시의 날짜 차이를 일수로 계산 날짜 포멧이 null일 경우 기본 포멧(yyyyMMdd)로 간주함
     * 
     * @param startDate
     *            시작날짜
     * @param endDate
     *            종료날짜
     * @param dateFormat
     *            날짜포멧
     * @return 종료일 - 시작일
     */
    public static int getBetweenDays(String startDate, String endDate, String dateFormat) {
        int betweenDays = 0;
        if (dateFormat == null) {
            betweenDays = getBetweenDays(convertStringToDate(startDate, dateFormat), convertStringToDate(endDate, dateFormat));
        } else {
            betweenDays = getBetweenDays(convertStringToDate(startDate, dateFormat), convertStringToDate(endDate, dateFormat));
        }

        return betweenDays;
    }

    /**
     * 시작일시와 종료일시의 날짜 차이를 일수로 계산 날짜 포멧이 null일 경우 기본 포멧(yyyyMMdd)로 간주함
     * 
     * @param startDate
     *            시작날짜
     * @param endDate
     *            종료날짜
     * @return 종료일 - 시작일
     */
    public static int getBetweenDays(String startDate, String endDate) {
        return getBetweenDays(startDate, endDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 시작일시와 종료일시의 날짜 차이를 월수로 계산
     * 
     * @param startDate
     * @param endDate
     * @return 종료일 - 시작일
     */
    public static int getBetweenMonths(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일자, 종료일자는 null이 아니어야합니다.");
        }

        String startDateStr = convertDateToString(startDate, DEFAULT_DATE_FORMAT);
        String endDateStr = convertDateToString(endDate, DEFAULT_DATE_FORMAT);

        return getBetweenMonths(startDateStr, endDateStr);
    }

    /**
     * 시작일시와 종료일시의 날짜 차이를 월수로 계산
     * 
     * @param startDate
     * @param endDate
     * @return 종료일 - 시작일
     */
    public static int getBetweenMonths(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일자, 종료일자는 null이 아니어야합니다.");
        }

        // 년수 계산
        int years = getBetweenYears(startDate, endDate);

        int startMonth = getMonth(startDate);
        int endMonth = getMonth(endDate);

        return (years * 12) + (endMonth - startMonth);
    }

    /**
     * 시작일시와 종료일시의 날짜 차이를 년수로 계산
     * 
     * @return 종료일 - 시작일
     */
    public static int getBetweenYears(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일자, 종료일자는 null이 아니어야합니다.");
        }

        String startDateStr = convertDateToString(startDate, DEFAULT_DATE_FORMAT);
        String endDateStr = convertDateToString(endDate, DEFAULT_DATE_FORMAT);

        return getBetweenYears(startDateStr, endDateStr);
    }

    /**
     * 시작일시와 종료일시의 날짜 차이를 년수로 계산
     * 
     * @return 종료일 - 시작일
     */
    public static int getBetweenYears(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일자, 종료일자는 null이 아니어야합니다.");
        }

        int startYear = getYear(startDate);
        int endYear = getYear(endDate);

        return endYear - startYear;
    }

    /**
     * 해당일과 오늘날짜와의 비교
     * 
     * @param date
     * @param format
     * @return 오늘 이전일이면 0보다 작고, 오늘이면 0, 오늘 이후일이면 0보다 큼. 해당일 정보나 포맷정보 혹은 잘못된 날짜인
     *         경우 Integer.MIN_VALUE 값을 리턴
     */
    public static long compareWithToday(String date, String format) {
        if (date == null || date.trim().length() <= 0)
            return Integer.MIN_VALUE;
        if (format == null || format.trim().length() <= 0)
            return Integer.MIN_VALUE;

        Date comparedDate = convertStringToDate(date, format);
        if (comparedDate == null)
            return Integer.MIN_VALUE;

        Calendar nowCal = Calendar.getInstance();
        Calendar comparedCal = Calendar.getInstance();
        comparedCal.setTime(comparedDate);

        int yearDiff = comparedCal.get(Calendar.YEAR) - nowCal.get(Calendar.YEAR);
        int monDiff = comparedCal.get(Calendar.MONTH) - nowCal.get(Calendar.MONTH);
        int dayDiff = comparedCal.get(Calendar.DAY_OF_MONTH) - nowCal.get(Calendar.DAY_OF_MONTH);

        if (yearDiff == 0 && monDiff == 0 && dayDiff == 0)
            return 0;

        final int MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;
        double dayUnitDiff = (comparedCal.getTimeInMillis() - nowCal.getTimeInMillis()) / (double) (MILLISECONDS_PER_DAY);

        return (long) dayUnitDiff;
    }

    /**
     * 해당일과 특정날짜와의 비교
     * 
     * @param date
     * @param format
     * @return 특정일 이전일이면 0보다 작고, 특정일이면 0, 특정일 이후일이면 0보다 큼. 해당일 정보나 포맷정보 혹은 잘못된
     *         날짜인 경우 Integer.MIN_VALUE 값을 리턴
     */
    public static long compareWithAnotherDay(String date, String format, String anotherDay, String anotherFormat) {
        if (date == null || date.trim().length() <= 0)
            return Integer.MIN_VALUE;
        if (format == null || format.trim().length() <= 0)
            return Integer.MIN_VALUE;

        Date comparedDate = convertStringToDate(date, format);
        if (comparedDate == null)
            return Integer.MIN_VALUE;
        Date anotherDate = convertStringToDate(anotherDay, anotherFormat);

        Calendar anotherCal = Calendar.getInstance();
        anotherCal.setTime(anotherDate);
        Calendar comparedCal = Calendar.getInstance();
        comparedCal.setTime(comparedDate);

        int yearDiff = comparedCal.get(Calendar.YEAR) - anotherCal.get(Calendar.YEAR);
        int monDiff = comparedCal.get(Calendar.MONTH) - anotherCal.get(Calendar.MONTH);
        int dayDiff = comparedCal.get(Calendar.DAY_OF_MONTH) - anotherCal.get(Calendar.DAY_OF_MONTH);

        if (yearDiff == 0 && monDiff == 0 && dayDiff == 0)
            return 0;

        double dayUnitDiff = (comparedCal.getTimeInMillis() - anotherCal.getTimeInMillis()) / (double) (MILLISECOND_OF_DAY);

        return (long) dayUnitDiff;
    }

    /**
     * 날짜 추가
     * 
     * @param date
     * @param y
     *            추가할 년
     * @param m
     *            추가할 월
     * @param d
     *            추가할 일
     * @return
     */
    public static String addDate(String date, int nYears, int nMonths, int nDays) {
        String toDate = new String();
        toDate = addDate(date, nDays);
        toDate = addMonth(toDate, nMonths);
        toDate = addYear(toDate, nYears);

        return toDate;
    }

    /**
     * 시간 비교<br/>
     * preTimeFmt 형식의 preTimeOperand 시간에서 postTimeFmt 형식의 postTimeOperand 시간을 뺌.
     * 
     * @param preTimeOperand
     * @param preTimeFmt
     * @param postTimeOperand
     * @param postTimeFmt
     * @return 0보다 크면 preTimeOperand 시간이 크고, 0과 같으면 시간이 동일
     */
    public static long compareStringTime(String preTimeOperand, String preTimeFmt, String postTimeOperand, String postTimeFmt) {
        Date preTimeDate = null;
        Date postTimeDate = null;
        if (preTimeFmt.equals(postTimeFmt)) {
            SimpleDateFormat format = new SimpleDateFormat(preTimeFmt);
            try {
                preTimeDate = format.parse(preTimeOperand);
                postTimeDate = format.parse(postTimeOperand);
            } catch (ParseException e) {
            }
        } else {
            SimpleDateFormat preFormat = new SimpleDateFormat(preTimeFmt);
            SimpleDateFormat postFormat = new SimpleDateFormat(postTimeFmt);
            try {
                preTimeDate = preFormat.parse(preTimeOperand);
                postTimeDate = postFormat.parse(postTimeOperand);
            } catch (ParseException e) {
            }
        }

        return preTimeDate.getTime() - postTimeDate.getTime();
    }

    /**
     * 시간에 대한 차이를 translatedFmt 형태의 문자열로 변환
     * 
     * @param preTimeOperand
     * @param preTimeFmt
     * @param postTimeOperand
     * @param postTimeFmt
     * @param translatedFmt
     * @return
     */
    public static String compareStringTime(String preTimeOperand, String preTimeFmt, String postTimeOperand, String postTimeFmt, String translatedFmt) {
        long timeDiff = compareStringTime(preTimeOperand, preTimeFmt, postTimeOperand, postTimeFmt);
        Calendar cal = Calendar.getInstance();
        cal.set(1, 1, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, (int) timeDiff);

        SimpleDateFormat preFormat = new SimpleDateFormat(translatedFmt);
        return preFormat.format(cal.getTime());
    }

    /**
     * 특정일시에 원하는 분 를 더한 data String을 리턴한다. ex) 20040803123212일에서 30분 앞으로 가려면
     * addTime("20040803123212","yyyyMMddHHmmss",-30), 30분 후로 가려면
     * addTime("20040803123212","yyyyMMddHHmmss",30)
     * 
     * @param date
     *            일시분초
     * @param pattern
     *            형식
     * @param amount
     *            더하고자 하는 분
     * @return
     */
    public static String addMinute(String date, String pattern, int amount) {
        String strArithmeticDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date aDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(aDate);

            calendar.add(Calendar.MINUTE, amount);
            Date newDate = calendar.getTime();
            strArithmeticDate = sdf.format(newDate);

        } catch (Exception e) {

        }
        return strArithmeticDate;

    }

    /**
     * 해당 시간과 현재 시간과의 비교
     * 
     * @param time
     * @param format
     * @return 시간 간격(분), 현재시간 이전이면 0보다 작고, 현재시간 이후이면 0보다 큼.
     */
    public static int compareWithTodayTime(String time, String format) {
        if (time == null || time.trim().length() <= 0)
            return Integer.MIN_VALUE;
        if (format == null || format.trim().length() <= 0)
            return Integer.MIN_VALUE;

        Date comparedTime = convertStringToDate(time, format);
        if (comparedTime == null)
            return Integer.MIN_VALUE;

        Calendar comparedCal = Calendar.getInstance();
        comparedCal.setTime(comparedTime);

        Calendar nowCal = Calendar.getInstance();

        int timeDiff = 0;

        int comparedHour = comparedCal.get(Calendar.HOUR_OF_DAY);
        int nowHour = nowCal.get(Calendar.HOUR_OF_DAY);
        timeDiff += (comparedHour - nowHour) * 60;

        int comparedMin = comparedCal.get(Calendar.MINUTE);
        int nowMin = nowCal.get(Calendar.MINUTE);
        timeDiff += (comparedMin - nowMin);

        return timeDiff;
    }

    /**
     * 시작일자에서 종료일자까지의 경과년수를 반환한다.
     * <p>
     * 내부에서 {@link #getProgressMonths(Date, Date, boolean)
     * getProgressMonths(startDate, endDate, true)}를 호출하여 총경과월수를 구한뒤 12로 나눈몫을
     * 경과년수로 설정하여 반환한다.
     * 
     * @param startDate
     *            시작일자
     * @param endDate
     *            종료일자
     * @return 경과년수
     * @see #getProgressMonths(Date, Date, boolean)
     */
    public static int getProgressYears(Date startDate, Date endDate) {
        int progressMonths = getProgressMonths(startDate, endDate, true);
        int progressYears = progressMonths / 12;
        return progressYears;
    }

    /**
     * 시작일자에서 종료일자까지의 경과월수를 반환한다.
     * <p>
     * 경과월수 계산식
     * 
     * <pre>
     * startYear, startMonth, startDay // 시작년,월,일
     * endYear, endMonth, endDay //종료년,월,일
     * 
     * 경과월수 = (endYear - startYear) * 12 + (endMonth - startMonth)
     * if((startDay > endDay ) && (종료일자 != 말일자)) {
     *     경과월수 = 경과월수 - 1;
     * }
     * 
     * if((만한달 경과 체크여부 != true) && (시작일자 <= 종료일자)) {
     *     경과월수 = 경과월수 + 1;
     * }
     * </pre>
     * 
     * 예를 들어 시작일자가 "1999-10-31"이고 종료일자가 "2000-03-30"이면
     * 
     * <pre>
     * startYear = 1999, startMonth = 10, startDay  = 31
     * endYear = 2000, endMonth = 3, endDay = 30
     * 
     * 경과월수 = (2000 - 1999) * 12 + (3 - 10) = 1*12 - 7 = 5
     * </pre>
     * 
     * 이고 종료일이 시작일보다 작고(30 < 31) 종료일자가 말일자가 아니므로(2000-03-30 != 2000-03-31) 경과월수는
     * 만한달경과 체크여부가 true인경우는 4개월, 아닌경우는 5개월이 된다.
     * <p>
     * 만한달경과 체크여부 = false인경우
     * 
     * <pre>
     *        시작일자      종료일자      경과월수  비고
     *   =================================================
     *   1.   2005-01-01   2005-07-02  7       일반
     *   2.   2005-01-31   2005-02-28  2       말일자기준
     *   3.   2000-01-31   2000-02-28  1       윤달말일자기준
     *   4.   2000-01-31   2000-02-29  2       윤달말일자기준
     *   5.   2000-02-29   2000-03-28  1       윤달말일자기준
     *   6.   2000-02-29   2000-03-29  2       윤달말일자기준
     * </pre>
     * 
     * 만한달경과 체크여부 = true인경우
     * 
     * <pre>
     *        시작일자      종료일자      경과월수  비고
     *   =================================================
     *   1.   2005-01-01   2005-07-02  6       일반
     *   2.   2005-01-31   2005-02-28  1       말일자기준
     *   3.   2000-01-31   2000-02-28  0       윤달말일자기준
     *   4.   2000-01-31   2000-02-29  1       윤달말일자기준
     *   5.   2000-02-29   2000-03-28  0       윤달말일자기준
     *   6.   2000-02-29   2000-03-29  1       윤달말일자기준
     * </pre>
     * 
     * @param startDate
     *            시작일자
     * @param endDate
     *            종료일자
     * @param isFull
     *            만한달 경과 체크여부
     * @return 경과월수
     */
    public static int getProgressMonths(Date startDate, Date endDate, boolean isFull) {
        int progressMonths = 0;

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        if (startTime < endTime) {
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(startDate);
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH);
            int startDay = calendar.get(Calendar.DATE);
            boolean isLastDayOfStartDate = (startDay == calendar.getActualMaximum(Calendar.DATE));

            calendar.setTime(endDate);
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH);
            int endDay = calendar.get(Calendar.DATE);
            boolean isLastDayOfEndDate = (endDay == calendar.getActualMaximum(Calendar.DATE));

            progressMonths = (endYear - startYear) * 12 + (endMonth - startMonth);

            // 2003-02-28 ~ 2004-02-28 일자의 계산오류로 변경. Modified by 임병인 :
            // 2006.09.07
            // 시작일이 종료일보다 크고 종료일자가 말일자가 아니면서 시작일자 또는 종료일자가 말일자가 아니면 1개월을 뺀다.
            if (((startDay > endDay) && !isLastDayOfEndDate) && (!isLastDayOfStartDate || !isLastDayOfEndDate)) {
                progressMonths--;
            }
        }

        // 만경과월수를 구하는게 아니고 시작일자가 종료일자보다 작거나 같을 경우 한달을 추가한다.
        if (!isFull && (startTime <= endTime)) {
            progressMonths++;
        }

        return progressMonths;
    }

    /**
     * 주차수를 구한다. 20080104 (금요일) ==> 1 20080107 (월요일) ==> 2
     * 
     * @param dateString
     * @return
     */
    public static int getWeekOfYear(String dateString) {
        int result = 0;
        Date date = convertStringToDate(dateString, DEFAULT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance(Locale.KOREAN);
        calendar.setTime(date);
        result = calendar.get(Calendar.WEEK_OF_YEAR);

        return result;
    }

    /**
     * 해당년도에 속하는 기준날짜와 같은 주차수/요일을 리턴한다.
     * 
     * @param year
     *            구하고자하는 해당년도
     * @param baseDate
     *            기준날짜
     * @return
     */
    public static String getSameWeekDate(String year, String baseDate) {
        int degree = DateUtil.getWeekOfYear(baseDate);
        int dayOfWeek = DateUtil.getDayOfWeek(baseDate);
        String firstDate = year + "0101";
        int baseDateDayOfWeek = DateUtil.getDayOfWeek(firstDate);
        String firstSaturday = DateUtil.addDate(firstDate, Calendar.SATURDAY - baseDateDayOfWeek);
        // 주차수 적용
        String degreeSaturday = DateUtil.addDate(firstSaturday, (degree - 1) * 7);
        // 해당요일날짜 구하기
        String result = DateUtil.addDate(degreeSaturday, -(Calendar.SATURDAY - dayOfWeek));
        return result;
    }

    /**
     * 해당날짜의 연도 첫날을 리턴한다. ex)20081229를 넣으면 20080101을 리턴한다.
     * 
     * @param dateString
     *            해당날짜
     * @param dateFormat
     *            해당포맷
     * @return 해당날짜의 연도 첫날
     * @throws ParseException
     *             date 변환 오류
     */
    public static String getYearFirstDate(String dateString, String dateFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date = format.parse(dateString);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);

        Date resultDate = cal.getTime();
        return format.format(resultDate);
    }

    /**
     * 해당날짜의 분기 첫날을 리턴한다.<br>
     * ex)<br>
     * 20081229를 넣으면 20081001을 리턴한다.<br>
     * 20080811을 넣으면 20080701을 리턴한다.
     * 
     * @param dateString
     *            해당날짜
     * @param dateFormat
     *            해당포맷
     * @return 해당날짜의 분기 첫날
     * @throws ParseException
     *             date 변환 오류
     */
    public static String getQuarterYearDate(String dateString, String dateFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date = format.parse(dateString);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH) / 3 * 3;

        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, 1);

        Date resultDate = cal.getTime();
        return format.format(resultDate);
    }

    /**
     * 해당날짜의 월의 1일을 리턴한다.<br>
     * 20081229를 넣으면 20081201을 리턴한다.
     * 
     * @param dateString
     *            해당날짜
     * @param dateFormat
     *            해당포맷
     * @return 해당날짜의 월의 1일
     * @throws ParseException
     *             date 변환 오류
     */
    public static String getMonthFirstDate(String dateString, String dateFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date = format.parse(dateString);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        cal.set(Calendar.DATE, 1);

        Date resultDate = cal.getTime();
        return format.format(resultDate);
    }

    /**
     * 해당날짜(기본포멧yyyyMMdd)의 월의 말일을 리턴한다.<br>
     * 
     * @param baseDate
     *            기준일(20080106)
     * @return 해당날짜의 월의 말일 (예:31)
     */
    public static String getMonthLastDate(String baseDate) {
        String baseDateInLastDay = setLastDayByYearMonth(baseDate);
        return baseDateInLastDay.substring(6, 8);
    }

    public static void main(String args[]) {
        // login - stamina
        long diffTime = DateUtil.getBetweenTimes("20140209132500", "20140209142500");

        System.out.println("diffTime: " + diffTime);
    }
}