package my.random.api.constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.random.api.exception.CustomException;
import my.random.api.util.StringUtil;
import scala.actors.threadpool.Arrays;

public class SupportMember {
	public static final String MEMBER_SSN_HASH_KEY = "SEAOFTHIEVES-NO-KOR";
	public static final String MEMBER_ETC_HASH_KEY = "MONSTER-HUNTER-WORLD-NO-KOR";

	public enum MemberDivEnum {
		NAVER("네이버 회원", "NAVER 아이디로 가입한 회원"), 
		KAKAO("카카오 회원", "KAKAO 아이디로 가입한 회원"), 
		NO_MEMBER("비회원", "회원 가입하지 않음"),
		;
		
		
		private String memberDiv;
		private String descript;

		private MemberDivEnum(String memberDiv, String descript) {
			this.memberDiv = memberDiv;
			this.descript = descript;
		}

		public String getDescript() {
			return descript;
		}
		public String getMemberDiv() {
			return memberDiv;
		}

		public static MemberDivEnum GetMemberDivEnum(String udiv) {
			if (StringUtil.isNotNull(udiv)) {
				for (MemberDivEnum o : MemberDivEnum.values()) {
					if (o.name().equals(udiv.toUpperCase())) {
						return o;
					}
				}
			}
			return null;
		}

	}

	public enum MemberStateEnum {
		ACTIVE("정상"), INACTIVE("비활성 유저(로그인 불가능)"), SLEEP("1년이상 접속하지 않아 휴면 상태"), LOCKBYPW("비밀번호 5회 이상 틀려 잠김"), LEAVE("탈퇴 회원");

		private String descript;

		private MemberStateEnum(String descript) {
			this.descript = descript;
		}

		public String getDescript() {
			return descript;
		}

		public static MemberStateEnum GetMemberState(String state) {
			for (int i = 0; i < MemberStateEnum.values().length; i++) {
				MemberStateEnum o = MemberStateEnum.values()[i];
				if (o.name().equals(state.toUpperCase())) {
					return o;
				}
			}
			throw CustomException.INVALID_CODE;
		}
	}
	public enum EmailEnum{
		UNSELECTE("선택하세요"),
		GMAIL("gmail.com"),
		HANMAIL("hanmail.net"),
		NATE("nate.com"),
		NAVER("naver.com");
		
		String domain;
		private EmailEnum(String domain){
			this.domain = domain;
		}
		public String getDomain() {
			return domain;
		}
		public static List<EmailEnum> EmailList(){
			return Arrays.asList(EmailEnum.values());
		}
	}
	
	public enum PhoneStartEnum{
		BASE("010",				"CELL"),
		OLD1("011",				"CELL"),
		OLD2("016",				"CELL"),
		OLD3("017",				"CELL"),
		OLD4("018",				"CELL"),
		OLD5("019",				"CELL"),
		SEOUL("02",				"TEL"),
		GYEONGGI("031",			"TEL"),
		INCHEON("032",			"TEL"),
		GANGWON("033",			"TEL"),
		CHUNGCHEONGNAM("041",	"TEL"),
		DAEJEON("042",			"TEL"),
		CHUNGCHEONGBUK("043",	"TEL"),
		SEJONG("044",			"TEL"),
		BUSAN("051",			"TEL"),
		ULSAN("052",			"TEL"),
		DAEGU("053",			"TEL"),
		GYEONGSANGBUK("054",	"TEL"),
		GYEONGSANGNAM("055",	"TEL"),
		JEOLLANAM("061",		"TEL"),
		GWANGJU("062",			"TEL"),		
		JEOLLABUK("063",		"TEL"),		
		JEJU("064",				"TEL"),		
		FAX1("050"	,			"FAX"),
		INTERNET("070",			"TEL"),
		
		
		FAX2("080"	,			"FAX"),
		FAX3("090"	,			"FAX"),
		FAX4("0303"	,			"FAX"),
		FAX5("0502"	,			"FAX"),
		FAX6("0503"	,			"FAX"),
		FAX7("0504"	,			"FAX"),
		FAX8("0505"	,			"FAX"),
		FAX9("0506"	,			"FAX"),
		FAX10("0507"	,		"FAX"),
		FAX11("0508"	,		"FAX"),
		
		;
		
		String no;
		String div;
		private PhoneStartEnum(String no, String div){
			this.no = no;
			this.div = div;
		}
		public String getNo() {
			return no;
		}
		public String getDiv() {
			return div;
		}
		public static List<PhoneStartEnum> PhoneStartList(){
			List<PhoneStartEnum> list = new ArrayList<PhoneStartEnum>();
			for(PhoneStartEnum o:PhoneStartEnum.values()){
				if("CELL".equals(o.getDiv()) || "TEL".equals(o.getDiv())){
					list.add(o);
				}
			}
			return list;
		}
		public static List<PhoneStartEnum> CellPhoneStartList(){
			List<PhoneStartEnum> list = new ArrayList<PhoneStartEnum>();
			for(PhoneStartEnum o:PhoneStartEnum.values()){
				if("CELL".equals(o.getDiv())){
					list.add(o);
				}
			}
			return list;
		}
		public static List<PhoneStartEnum> TelStartList(){
			List<PhoneStartEnum> list = new ArrayList<PhoneStartEnum>();
			for(PhoneStartEnum o:PhoneStartEnum.values()){
				if("TEL".equals(o.getDiv()) || "FAX".equals(o.getDiv())){
					list.add(o);
				}
			}
			return list;
		}
		
	}
	public enum MemberReasonEnum {
		INTERNET("인터넷"), TV("TV"), POSTBOX("우편물"), RADIO("라디오"), PAPERNEWS("뉴스"), FRIENDS("주변권유"), STREET_CAMPAIGN("거리 캠페인"), ETC("기타"),

		NO_MEMBER_SPONSOR("비회원 후원");

		private String descript;

		private MemberReasonEnum(String descript) {
			this.descript = descript;
		}

		public String getDescript() {
			return descript;
		}

		public static MemberReasonEnum GetMemberReason(String reason) {
			for (int i = 0; i < MemberReasonEnum.values().length; i++) {
				MemberReasonEnum o = MemberReasonEnum.values()[i];
				if (o.name().equals(reason.toUpperCase())) {
					return o;
				}
			}
			return MemberReasonEnum.ETC;
		}
	}
	public enum VerificateMethodEnum{
		SMS("휴대폰 본인 인증"),
		EMAIL("이메일 본인 인증"),
		BANK_INFO("계좌 정보 인증"),
		CREDIT_CARD_INFO("카드 정보 인증");
		
		String descript;
		private VerificateMethodEnum(String descript){
			this.descript = descript;
		}
		public String getDescript() {
			return descript;
		}
		public static VerificateMethodEnum GetVerificateMethodEnum(String code){
			for(VerificateMethodEnum o: VerificateMethodEnum.values()){
				if(o.name().equals(code.toUpperCase())){
					return o;
				}
			}
			return null;
		}
	}
	
	public enum PrivateInfoNotiEnum{
		ADDRESS("주소"),
		EMAIL("이메일"),
		PHONE("연락처"),
		EMAIL_NEWS("소식지[이메일]"),
		POST_NEWS("소식지[우편]");
		
		String descript;
		private PrivateInfoNotiEnum(String descript){
			this.descript = descript;
		}
		public String getDescript() {
			return descript;
		}
		public static PrivateInfoNotiEnum GetPrivateInfoNotiEnum(String code){
			for(PrivateInfoNotiEnum o: PrivateInfoNotiEnum.values()){
				if(o.name().equals(code.toUpperCase())){
					return o;
				}
			}
			return null;
		}
	}


	//public static int getAge(int birthYear, int birthMonth, int birthDay) {
	public static int getAge(String birth8, String birth6) {
		if(StringUtil.isNull(birth8) && StringUtil.isNull(birth6) ){
			return 0;
		}
		if(StringUtil.isNull(birth8) && StringUtil.isNotNull(birth6) ){
			if(birth6.startsWith("0") || birth6.startsWith("1")){
				birth8 = "20"+birth6;
			}else{
				birth8 = "19"+birth6;
			}
		}
		
		int birthYear =  Integer.valueOf(birth8.substring(0, 4));
		int birthMonth =  Integer.valueOf(birth8.substring(4, 6));
		int birthDay =  Integer.valueOf(birth8.substring(6, 8));
		
		Calendar current = Calendar.getInstance();
		int currentYear = current.get(Calendar.YEAR);
		int currentMonth = current.get(Calendar.MONTH) + 1;
		int currentDay = current.get(Calendar.DAY_OF_MONTH);

		int age = currentYear - birthYear;
		// 생일 안 지난 경우 -1
		if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay)
			age--;

		return age;
	}
	
	
}
