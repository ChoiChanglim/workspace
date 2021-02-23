package my.random.api.constant;

import my.random.api.exception.CustomException;
import my.random.api.util.NumberUtil;

public enum WinnersMessageEnum {
	NOP("더 좋은 날이 올거에요", 0),
	
	NORMAL_1("당첨을 축하드립니다!", 1),
	NORMAL_2("당첨 되셨습니다.", 2),
	NORMAL_3("축하해요", 3),
	NORMAL_4("축하합니다", 4),
	NORMAL_5("어쨋든 로또 당첨자입니다.", 5),
	NORMAL_6("운수 약간 좋은날 입니다.", 6),
	NORMAL_7("기분 좋은 날 입니다", 7),
	NORMAL_8("다음엔 더 큰 행운이 있길..", 8),
	NORMAL_9("언젠간 1등 될꺼야!", 9),
	NORMAL_10("아쉽지만 그래도 당첨^^", 10),
	
	LITTLE_1("삼겹살 정도는 쌉가능!", 1),
	LITTLE_2("많이 부럽네유", 2),
	LITTLE_3("헐 정말 축하합니다.", 3),
	LITTLE_4("로또 당첨자십니다", 4),
	LITTLE_5("부러워~!", 4),
	
	LUCK_1("당첨! 얼떨떨 할껄?", 1),
	LUCK_2("축하해요~! 끼야호~!", 2),
	LUCK_3("좋은꿈을 꾸셨나봄", 3),
	
	GREAT_1("세상을 가진기분 입니까?", 1),
	GREAT_2("소리질러 기뻐 하세요", 2),
	GREAT_3("착한일 많이 하셨나봄", 3),
	
	VERY_VERY_1("집사러 가십쇼~", 1),
	VERY_VERY_2("전생에 큰거하나 구하셨나봄", 2),
	VERY_VERY_3("인생 역전입니까?", 3),
	VERY_VERY_4("헤헤~! 한푼만 줍슈~", 4),
	VERY_VERY_5("심호흡하고 진정하시기 바랍니다", 5),
	
	;
	private String message;
	private int no;
	
	private WinnersMessageEnum(String message, int no){
		this.message = message;
		this.no = no;
	}

	public String getMessage() {
		return message;
	}

	public int getNo() {
		return no;
	}
	public static WinnersMessageEnum GetWinnersMessageEnum(String startToken, int no){
		for(int i=0;i<WinnersMessageEnum.values().length;i++){
			WinnersMessageEnum o = WinnersMessageEnum.values()[i];
			if(o.name().startsWith(startToken) && o.no == no){
				return o;
			}
		}
		throw CustomException.INVALID_NUMBER;
	}
	
	public static String GetGradeToken(int grade){
		String gradeToken = "NOP";
		if(grade == 5){
			gradeToken = "NORMAL";
		}
		
		if(grade == 4){
			gradeToken = "LITTLE";
			
		}
		
		if(grade == 3){
			gradeToken = "LUCK";
		}
		
		if(grade == 2){
			gradeToken = "GREAT";
		}
		
		if(grade == 1){
			gradeToken = "VERY";
		}
		
		return gradeToken;
	}
	public static WinnersMessageEnum GetWinnersMessage(int grade){
		String GetGradeToken = GetGradeToken(grade);
		if(grade == 5){
			int no = NumberUtil.randomRange(1, 10);
			WinnersMessageEnum winnersMessageEnum = GetWinnersMessageEnum(GetGradeToken, no);
			return winnersMessageEnum;
			
		}
		
		if(grade == 4){
			int no = NumberUtil.randomRange(1, 5);
			WinnersMessageEnum winnersMessageEnum = GetWinnersMessageEnum(GetGradeToken, no);
			return winnersMessageEnum;
			
		}
		
		if(grade == 3){
			int no = NumberUtil.randomRange(1, 3);
			WinnersMessageEnum winnersMessageEnum = GetWinnersMessageEnum(GetGradeToken, no);
			return winnersMessageEnum;
		}
		
		if(grade == 2){
			int no = NumberUtil.randomRange(1, 3);
			WinnersMessageEnum winnersMessageEnum = GetWinnersMessageEnum(GetGradeToken, no);
			return winnersMessageEnum;
		}
		
		if(grade == 1){
			int no = NumberUtil.randomRange(1, 5);
			WinnersMessageEnum winnersMessageEnum = GetWinnersMessageEnum(GetGradeToken, no);
			return winnersMessageEnum;
		}
		
		return WinnersMessageEnum.NOP;
	}
	
	public static WinnersMessageEnum GetWinnersMessage(int grade, int messageNo){
		System.err.println("messageNo:"+messageNo);
		if(messageNo == 0){
			return WinnersMessageEnum.NOP;
		}
		for(int i=0;i<WinnersMessageEnum.values().length;i++){
			WinnersMessageEnum o = WinnersMessageEnum.values()[i];
			String gradeToken = GetGradeToken(grade);
			if(o.name().startsWith(gradeToken) && o.no == messageNo){
				return o;
			}
		}
		throw CustomException.INVALID_NUMBER;
	}
}
