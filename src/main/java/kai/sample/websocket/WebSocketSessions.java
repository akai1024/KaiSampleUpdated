package kai.sample.websocket;

import kai.sample.util.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket連線管理服務
 */
@Service
public class WebSocketSessions {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ConcurrentHashMap<String, String> sessionUsers = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, WebSocketSession> webSocketSessions = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        return "[WebSocketSessions] sessionUsers: " + sessionUsers.size() + ", WebSockets: " + webSocketSessions.size();
    }

    public synchronized void registerSessionId(String user, String sessionId) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(sessionId, "sessionId must not be null");

        sessionUsers.put(sessionId, user);
        LogHelper.logInfoParam(logger, "registerSessionId, user:{}, sessionId:{}", user, sessionId);
    }

    public synchronized void removeSessionId(String sessionId) {
        Assert.notNull(sessionId, "sessionId must not be null");

        if (sessionUsers.containsKey(sessionId)) {
            sessionUsers.remove(sessionId);
            LogHelper.logInfoParam(logger, "removeSessionId, sessionId:{}", sessionId);
        }
    }

    public ArrayList<String> getAllUsers() {
        return new ArrayList<>(sessionUsers.values());
    }

    public ArrayList<String> getAllSessionIds() {
        return new ArrayList<>(sessionUsers.keySet());
    }

    /**
     * 取得相同使用者的所有sessionIds
     */
    public ArrayList<String> getSessionIds(String user) {
        ArrayList<String> sessionIds = new ArrayList<>();
        for (Map.Entry<String, String> entry : sessionUsers.entrySet()) {
            String userInMap = entry.getValue();
            if (userInMap.equals(user)) {
                sessionIds.add(entry.getKey());
            }
        }
        return sessionIds;
    }

    /**
     * 添加WebSocket連線
     */
    public synchronized void addWebSocketSession(WebSocketSession session) {
        if (session == null) {
            return;
        }
        String sessionId = session.getId();
        webSocketSessions.put(sessionId, session);
        LogHelper.logInfoParam(logger, "addWebSocketSession, sessionId:{}", sessionId);
    }

    /**
     * 移除WebSocket連線
     */
    public synchronized void removeWebSocketSession(WebSocketSession session) {
        if (session == null) {
            return;
        }

        String sessionId = session.getId();
        if (webSocketSessions.containsKey(sessionId)) {
            webSocketSessions.remove(sessionId);
        }
        LogHelper.logInfoParam(logger, "removeWebSocketSession, sessionId:{}", sessionId);
    }

    /**
     * 強制中斷WebSocket連線
     */
    public synchronized boolean forceCloseSession(String sessionId) {
        if (sessionId == null) {
            return false;
        }

        LogHelper.logInfoParam(logger, "forceCloseSession, sessionId:{}", sessionId);
        boolean isDisconnect = false;
        if (webSocketSessions.containsKey(sessionId)) {
            try {
                WebSocketSession session = webSocketSessions.get(sessionId);
                session.close(CloseStatus.POLICY_VIOLATION);
                isDisconnect = true;
            } catch (IOException e) {
                LogHelper.logError(logger, e.getMessage(), e);
            }
        }
        return isDisconnect;
    }

}
