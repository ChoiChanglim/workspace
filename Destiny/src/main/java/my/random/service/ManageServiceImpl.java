package my.random.service;

import java.util.Date;

import my.random.api.constant.LottoReader;
import my.random.api.exception.CustomException;
import my.random.api.exception.ExceptionEnum;
import my.random.api.util.DateUtil;
import my.random.bean.DestinyInfo;
import my.random.bean.WinNumber;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;




public class ManageServiceImpl implements ManageService{
    private static Logger logger = LoggerFactory.getLogger(ManageService.class);

    @Autowired
    DestinyService destinyService;

    @Override
    public void crawling(){
        logger.info("crawling() 호출 - "+DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        DestinyInfo lastDestinyInfo = destinyService.getLastDestinyInfo();
        if(null == lastDestinyInfo){
            logger.error("LastDestinyInfo is null!");
    		throw CustomException.LOTTO_CRAWLING_EXCEPTION;
        }
        
        String drawingDate = DateUtil.convertDateToString(new Date(), "yyyyMMdd");
        String gdateByLastGameDate = DateUtil.convertDateToString(lastDestinyInfo.getGdate(), "yyyyMMdd"); //yyyyMMdd
        
        if(gdateByLastGameDate.equals(drawingDate) == false){//오늘 크롤링이 아직 안되었다면(당첨정보 업데이트가 아직 안되었을때)
            int gno = lastDestinyInfo.getGno() + 1;
            WinNumber winNumber = LottoReader.ReadWinnumberResult(gno);
            if("0".equals(winNumber.getFirstCount()) 
            		|| "0".equals(winNumber.getSecondCount())
            		|| "0".equals(winNumber.getThirdCount())
                		){
                	return;
                }
                if(winNumber.getGno() == gno ){
                DestinyInfo destinyInfo = new DestinyInfo();
                destinyInfo.setGno(gno);
                destinyInfo.setNo1(winNumber.getNums().get(0));
                destinyInfo.setNo2(winNumber.getNums().get(1));
                destinyInfo.setNo3(winNumber.getNums().get(2));
                destinyInfo.setNo4(winNumber.getNums().get(3));
                destinyInfo.setNo5(winNumber.getNums().get(4));
                destinyInfo.setNo6(winNumber.getNums().get(5));
                destinyInfo.setBonus(winNumber.getBonus());
                destinyInfo.setFirstCount(winNumber.getFirstCount());
                destinyInfo.setFirstAmount(winNumber.getFirstAmount());
                destinyInfo.setSecondCoount(winNumber.getSecondCount());
                destinyInfo.setSecondAmount(winNumber.getSecondAmount());
                destinyInfo.setThirdCount(winNumber.getThirdCount());
                destinyInfo.setThirdAmount(winNumber.getThirdAmount());
                destinyInfo.setGdate(new Date());
                
                int insert = destinyService.insert(destinyInfo);
                if(insert > 0){
                	destinyService.updateGrade(gno);
                }

                logger.info("crawling() "+gno+"회차 정보 업데이트 완료! - "+new JSONObject(destinyInfo));
            }
        }

    }
}
