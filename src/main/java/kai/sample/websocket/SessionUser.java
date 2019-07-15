package kai.sample.websocket;

import java.security.Principal;
import java.util.Objects;

/**
 * WebSocket連線資訊
 */
public class SessionUser implements Principal {

    private static final long serialVersionUID = 824805003230599889L;

    private String sessionId;
    private String user;
    private String password;

    public SessionUser(String sessionId, String user, String password) {
        this.sessionId = sessionId;
        this.user = user;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionUser that = (SessionUser) o;
        return Objects.equals(sessionId, that.sessionId) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, user);
    }

    @Override
    public String getName() {
        return "[" + user + "@" + sessionId + "]";
    }

    @Override
    public String toString() {
        return getName();
    }

}
