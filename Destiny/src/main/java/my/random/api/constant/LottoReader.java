package my.random.api.constant;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import my.random.api.util.DateUtil;
import my.random.api.util.RequestUtil;
import my.random.bean.WinNumber;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class LottoReader {
    private static Logger LOG = LoggerFactory.getLogger(LottoReader.class);

    public static List<WinNumber> ReadWinnumberResult(HashMap<String, String> param) {
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        HtmlParser parser = new HtmlParser();
        try {
            InputStream is = getExcelFileInputStream(param);
            parser.parse(is, handler, metadata);
        } catch (IOException | SAXException | TikaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<WinNumber> list = new ArrayList<WinNumber>();
        String contnets = handler.toString();

        String[] arr = contnets.split("\t");

        int start = Integer.valueOf(param.get("drwNoStart"));
        int end = Integer.valueOf(param.get("drwNoEnd"));
        for (int k = start; k <= end; k++) {
            int gNum = k;
            String thisWeekGDateYmd = getGdate(gNum);

            for (int i = 0; i < arr.length; i++) {
                String txt = arr[i];
                if (String.valueOf(gNum).equals(txt) && thisWeekGDateYmd.equals(arr[i + 1])) {
                    WinNumber winNumber = new WinNumber();
                    String firstCount = arr[i + 2].trim();
                    String firstAmount = arr[i + 3].trim();
                    String secondCount = arr[i + 4].trim();
                    String secondAmount = arr[i + 5].trim();
                    String thirdCount = arr[i + 6].trim();
                    String thirdAmount = arr[i + 7].trim();

                    List<Integer> nums = new ArrayList<Integer>();

                    String no1 = arr[i + 12];
                    String no2 = arr[i + 13];
                    String no3 = arr[i + 14];
                    String no4 = arr[i + 15];
                    String no5 = arr[i + 16];
                    String no6 = arr[i + 17];

                    nums.add(Integer.valueOf(no1.trim()));
                    nums.add(Integer.valueOf(no2.trim()));
                    nums.add(Integer.valueOf(no3.trim()));
                    nums.add(Integer.valueOf(no4.trim()));
                    nums.add(Integer.valueOf(no5.trim()));
                    nums.add(Integer.valueOf(no6.trim()));

                    String bonus = arr[i + 18];
                    winNumber.setGno(gNum);
                    winNumber.setGdate(DateUtil.convertStringToDate(thisWeekGDateYmd, "yyyy.MM.dd"));
                    winNumber.setNums(nums);
                    winNumber.setFirstCount(firstCount);
                    winNumber.setFirstAmount(firstAmount);
                    winNumber.setSecondCount(secondCount);
                    winNumber.setSecondAmount(secondAmount);
                    winNumber.setThirdCount(thirdCount);
                    winNumber.setThirdAmount(thirdAmount);
                    winNumber.setBonus(Integer.valueOf(bonus.trim()));
                    list.add(winNumber);
                }
            }
        }
        return list;
    }

    public static WinNumber ReadWinnumberResult(int gNum) {
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        HtmlParser parser = new HtmlParser();

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("method", "allWinExel");
        param.put("nowPage", "1");
        param.put("drwNoStart", String.valueOf(gNum));
        param.put("drwNoEnd", String.valueOf(gNum));

        try {
            InputStream is = getExcelFileInputStream(param);
            parser.parse(is, handler, metadata);
        } catch (IOException | SAXException | TikaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<WinNumber> list = new ArrayList<WinNumber>();
        String contnets = handler.toString();

        String[] arr = contnets.split("\t");
        // int thisWeekGNum = 2;
        String thisWeekGDateYmd = getGdate(gNum);
        WinNumber winNumber = new WinNumber();
        for (int i = 0; i < arr.length; i++) {
            String txt = arr[i];
            if (String.valueOf(gNum).equals(txt) && thisWeekGDateYmd.equals(arr[i + 1])) {

                String firstCount = arr[i + 2].trim();
                String firstAmount = arr[i + 3].trim();
                String secondCount = arr[i + 4].trim();
                String secondAmount = arr[i + 5].trim();
                String thirdCount = arr[i + 6].trim();
                String thirdAmount = arr[i + 7].trim();

                List<Integer> nums = new ArrayList<Integer>();

                String no1 = arr[i + 12];
                String no2 = arr[i + 13];
                String no3 = arr[i + 14];
                String no4 = arr[i + 15];
                String no5 = arr[i + 16];
                String no6 = arr[i + 17];

                nums.add(Integer.valueOf(no1.trim()));
                nums.add(Integer.valueOf(no2.trim()));
                nums.add(Integer.valueOf(no3.trim()));
                nums.add(Integer.valueOf(no4.trim()));
                nums.add(Integer.valueOf(no5.trim()));
                nums.add(Integer.valueOf(no6.trim()));

                String bonus = arr[i + 18];
                winNumber.setGno(gNum);
                winNumber.setGdate(DateUtil.convertStringToDate(thisWeekGDateYmd, "yyyy.MM.dd"));
                winNumber.setNums(nums);
                winNumber.setFirstCount(firstCount);
                winNumber.setFirstAmount(firstAmount);
                winNumber.setSecondCount(secondCount);
                winNumber.setSecondAmount(secondAmount);
                winNumber.setThirdCount(thirdCount);
                winNumber.setThirdAmount(thirdAmount);
                winNumber.setBonus(Integer.valueOf(bonus.trim()));
            }
        }
        return winNumber;
    }

    private static InputStream getExcelFileInputStream(HashMap<String, String> param) {
    	String downUrl = "https://www.dhlottery.co.kr/gameResult.do";
        String queryString = RequestUtil.getMapToQstr(param);
        downUrl = downUrl + "?" + queryString;
        LOG.info("Excel URL:" +downUrl);
        InputStream is = null;
        try {
            URL url = new URL(downUrl);
            is = url.openStream();
        } catch (Exception e) {
            LOG.error("getExcelFileInputStream EROR");
        }
        return is;
    }

    /**
     * 게임번호로 날짜 조회
     *
     * @param gnum
     * @return
     */
    public static String getGdate(int gnum) {
        Calendar c = Calendar.getInstance();
        Date lottoStart = DateUtil.convertStringToDate("2002.12.07", "yyyy.MM.dd");
        c.setTime(lottoStart);
        c.add(Calendar.DATE, (gnum - 1) * 7);
        return DateUtil.convertDateToString(c.getTime(), "yyyy.MM.dd");

    }

    /**
     *  * 날짜로 게임번호 조회  * @param today  * @return  
     */
    public static int getGnum(Date somedate) {
        int gnum = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(somedate);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

        String todayStr = DateUtil.convertDateToString(c.getTime(), "yyyy.MM.dd");
        while (gnum < 10000) {
            if (LottoReader.getGdate(gnum).equals(todayStr)) {
                break;
            }
            gnum++;
        }
        return gnum;
    }
}