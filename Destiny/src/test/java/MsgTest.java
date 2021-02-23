import my.random.api.constant.WinnersMessageEnum;


public class MsgTest {

	public static void main(String[] args) {
		WinnersMessageEnum winnersMessageEnum = WinnersMessageEnum.GetWinnersMessage(1);
		
		System.err.println(winnersMessageEnum.getMessage());
	}

}
