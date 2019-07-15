package kai.sample.controller.chat;

import kai.sample.datasource.service.ChatLogService;
import kai.sample.websocket.MsgTemplate;
import kai.sample.websocket.WebSocketSessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private WebSocketSessions sessions;

    @Autowired
    private ChatLogService chatLogService;

    @MessageMapping("/chat")
    @SendTo(MsgTemplate.BROADCAST_DESTINATION)
    public OutputMessage send(final SimpMessageHeaderAccessor accessor, final Message message) throws Exception {
        final String time = new Date().toString();
        // 寫入chat_log
        chatLogService.saveChatLog(message);
//        // 印出預設的sessionId
//        System.out.println("session:" + accessor.getSessionId() + ", " + message.toString());
//        // 查看header
//        System.out.println(accessor.getFirstNativeHeader("user"));
//        System.out.println(accessor.getFirstNativeHeader("testParam"));
//        System.out.println(accessor.getFirstNativeHeader("other"));

        return new OutputMessage(time, message);
    }

}
