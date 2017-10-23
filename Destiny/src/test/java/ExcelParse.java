import java.util.HashMap;

import my.random.api.constant.LottoReader;
import my.random.bean.WinNumber;

import org.json.JSONObject;



public class ExcelParse {
    public static void main(String[] args) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("method", "allWinExel");
        param.put("drwNoStart", "736");
        param.put("drwNoEnd", "737");


        //List<WinNumber> list = LottoReader.ReadWinnumberResult(param);
        //System.err.println(new JSONArray(list));

        WinNumber winNumber = LottoReader.ReadWinnumberResult(736);
        System.err.println(new JSONObject(winNumber));

    }


}
