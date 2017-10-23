import java.util.Calendar;
import java.util.Date;

import my.random.api.constant.LottoReader;
import my.random.api.util.DateUtil;


public class ManageTest {

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -6);
        //String aa = LottoReader.getGdate(710);
        //System.err.println(aa);
        int gnum = getGnum(c.getTime());
        System.err.println(getGdate(736));




    }

    public static String getGdate(int gnum){
        Calendar c = Calendar.getInstance();
        Date lottoStart = DateUtil.convertStringToDate("2002.12.07", "yyyy.MM.dd");
        c.setTime(lottoStart);
        c.add(Calendar.DATE, (gnum-1) * 7);
        return DateUtil.convertDateToString(c.getTime(), "yyyy.MM.dd");

    }

    /**
     * 날짜로 게임번호 조회
     * @param today
     * @return
     */
    public static int getGnum(Date today){
        int gnum = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

        String todayStr = DateUtil.convertDateToString(c.getTime(), "yyyy.MM.dd");
        while(gnum < 10000){
            if(LottoReader.getGdate(gnum).equals(todayStr)){
                break;
            }
            gnum++;
        }
        return gnum;
    }

}
