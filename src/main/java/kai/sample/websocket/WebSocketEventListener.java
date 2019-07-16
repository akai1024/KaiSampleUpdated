package kai.sample.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * WebSocket的事件監聽器
 */
@Component
public class WebSocketEventListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String LOGIN_MSG = " logged in!";
    private static final String LOGOUT_MSG = " logged out!";

    @Autowired
    private MsgTemplate template;

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        SessionUser user = (SessionUser) accessor.getUser();
        template.broadcastSystemMsg(user.toString() + LOGIN_MSG);
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        SessionUser user = (SessionUser) accessor.getUser();
        template.broadcastSystemMsg(user.toString() + LOGOUT_MSG);
    }

}
