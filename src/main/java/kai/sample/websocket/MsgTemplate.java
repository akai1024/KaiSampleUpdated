package kai.sample.websocket;

import kai.sample.controller.chat.Message;
import kai.sample.controller.chat.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MsgTemplate {

    private static final String SYSTEM_NAME = "<System>";

    public static final String BROADCAST_DESTINATION = "/topic/messages";
    private static final String USER_TOPIC = "/subscribe";

    @Autowired
    private WebSocketSessions sessions;

    @Autowired
    private SimpMessagingTemplate template;

    public void sendMsgTo(String destination, Object msg) {
        template.convertAndSend(destination, msg);
    }

    public void sendMsgToSession(String sessionId, Object msg) {
        template.convertAndSendToUser(sessionId, USER_TOPIC, msg);
    }

    public void sendMsgToUser(String user, Object msg) {
        sessions.getSessionIds(user).forEach(sessionId -> {
            template.convertAndSendToUser(sessionId, USER_TOPIC, msg);
        });
    }

    public void broadcast(Object msg) {
        sendMsgTo(BROADCAST_DESTINATION, msg);
    }

    public void broadcastSystemMsg(String msg) {
        Message message = new Message();
        message.setFrom(SYSTEM_NAME);
        message.setText(msg);
        broadcast(new OutputMessage(new Date().toString(), message));
    }

}
