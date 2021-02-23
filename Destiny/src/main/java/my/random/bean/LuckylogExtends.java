package my.random.bean;

import my.random.api.constant.WinnersMessageEnum;
import my.random.api.util.StringUtil;

public class LuckylogExtends extends Luckylog{
	String uname;
	String udiv;
	String maskUname;
	String winnerMessage;
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUdiv() {
		return udiv;
	}
	public void setUdiv(String udiv) {
		this.udiv = udiv;
	}
	public String getMaskUname() {
		return StringUtil.MaskingName(uname);
	}
	public String getWinnerMessage() {
		WinnersMessageEnum winnersMessageEnum = WinnersMessageEnum.GetWinnersMessage(getGrade(), getMessageNo());
		return winnersMessageEnum.getMessage();
	}
	
	
}
