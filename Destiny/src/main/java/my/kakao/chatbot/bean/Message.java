package my.kakao.chatbot.bean;

public class Message {
	String user_key;
	String type;
	String content;
	public String getUser_key() {
		return user_key;
	}
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content.trim();
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
