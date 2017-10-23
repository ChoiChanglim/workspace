package my.random.api.constant;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;


/**
 * @author NHNEnt
 *
 */
@Component("sessionScopebean")
public class SessionScopeBean implements Serializable{
    private static final long serialVersionUID = 3080964111413624631L;

    long usn = 0;
    String nick="";
    private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();

    public long getUsn() {
        return usn;
    }
    public void setUsn(long usn) {
        this.usn = usn;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public Set<WebSocketSession> getSessionSet() {
        return sessionSet;
    }
    public void setSessionSet(Set<WebSocketSession> sessionSet) {
        this.sessionSet = sessionSet;
    }


}
