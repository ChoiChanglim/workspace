package callback;
public class EventApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CallbackEvent callbackEvent = new CallbackEvent(){

			@Override
			public void callbackMethod() {
				// TODO Auto-generated method stub
				System.out.println("call callback method from callee");
			}

		};

		EventRegistration eventRegistration = new EventRegistration(callbackEvent);
		eventRegistration.doWork();
	}

}