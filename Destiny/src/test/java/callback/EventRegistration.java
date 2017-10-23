package callback;
public class EventRegistration {
	private CallbackEvent callbackEvent;

	public EventRegistration(CallbackEvent event){
		callbackEvent = event;
	}

	public void doWork(){
		callbackEvent.callbackMethod();
	}
}