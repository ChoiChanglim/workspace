package my.random.api.constant;

import java.util.ArrayList;
import java.util.List;

public class QuizAndChoice {


	public enum QuizEnum{
		Q1("1번 퀴즈", 1),
		Q2("2번 퀴즈", 2),
		Q3("3번 퀴즈", 3),
		;
	
		private String quiz;
		private int no;
		
		private QuizEnum(String quiz, int no) {
			this.quiz = quiz;
			this.no = no;
		}
		
		public String getQuiz() {
			return quiz;
		}

		public int getNo() {
			return no;
		}
		
		/**
		 * 퀴즈 순번으로 
		 */
		public static QuizEnum GetQuestionEnum(int no) {
			for(int i=0;i<QuizEnum.values().length;i++){
				QuizEnum o = QuizEnum.values()[i];
                if(o.getNo() == no){
                    return o;
                }
            }
			return QuizEnum.Q1;
		}
		
		
	}
	
	
	public enum ChoiceEnum{
		C1("1번 퀴즈 보기1", QuizEnum.Q1, 0),
		C2("1번 퀴즈 보기2", QuizEnum.Q1, 1),
		
		C3("2번 퀴즈 보기1", QuizEnum.Q2, 0),
		C4("2번 퀴즈 보기2", QuizEnum.Q2, 1),
		C5("2번 퀴즈 보기3", QuizEnum.Q2, 2),
		
		C6("3번 퀴즈 보기1", QuizEnum.Q3, 0),
		C7("3번 퀴즈 보기2", QuizEnum.Q3, 1),
		;
		
		private String choice;
		private QuizEnum quizEnum;
		private int index;
		private ChoiceEnum(String choice, QuizEnum quizEnum, int index){
			this.choice = choice;
			this.quizEnum = quizEnum;
			this.index = index;
		}
		public String getChoice(){
			return choice;
		}
		
		public QuizEnum getQuizEnum() {
			return quizEnum;
		}
		public int getIndex() {
			return index;
		}
		public static List<ChoiceEnum> GetChoiceEnumList(QuizEnum quizEnum) {
			List<ChoiceEnum> choiceEnumList = new ArrayList<QuizAndChoice.ChoiceEnum>();
			for(int i=0;i<ChoiceEnum.values().length;i++){
				ChoiceEnum o = ChoiceEnum.values()[i];
                if(o.getQuizEnum() == quizEnum){
                	choiceEnumList.add(o);
                }
            }
			return choiceEnumList;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Bean
	*/
	
	private QuizEnum quizEnum;
	private List<ChoiceEnum> choiceList;

	public QuizEnum getQuizEnum() {
		return quizEnum;
	}



	public void setQuizEnum(QuizEnum quizEnum) {
		this.quizEnum = quizEnum;
	}



	public List<ChoiceEnum> getChoiceList() {
		return choiceList;
	}



	public void setChoiceList(List<ChoiceEnum> choiceList) {
		this.choiceList = choiceList;
	}

	public static QuizAndChoice GetQuestionAndChoice(int no){
		QuizEnum quizEnum = QuizEnum.GetQuestionEnum(no);
		List<ChoiceEnum> choiceList = ChoiceEnum.GetChoiceEnumList(quizEnum);
		
		QuizAndChoice questionAndChoice = new QuizAndChoice();
		questionAndChoice.setQuizEnum(quizEnum);
		questionAndChoice.setChoiceList(choiceList);
		return questionAndChoice;
	}
	
	public static List<QuizAndChoice> GetQuestionAndChoiceList(){
		List<QuizAndChoice> list = new ArrayList<QuizAndChoice>();
		for(int i=0;i<QuizEnum.values().length;i++){
			list.add(GetQuestionAndChoice(i+1));
		}
		
		return list;
	}
}