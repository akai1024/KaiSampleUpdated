package kai.sample.controller;

import kai.sample.controller.chat.Message;
import kai.sample.controller.chat.OutputMessage;
import kai.sample.controller.msg.BasicResponse;
import kai.sample.controller.msg.ErrorCode;
import kai.sample.controller.msg.ResponseBuilder;
import kai.sample.websocket.MsgTemplate;
import kai.sample.websocket.WebSocketSessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/system", method = RequestMethod.POST)
public class SystemController {

    @Autowired
    private WebSocketSessions sessions;

    @Autowired
    private MsgTemplate template;

    @RequestMapping("/broadcast")
    public BasicResponse broadcast(@RequestBody Message message) {
        template.broadcast(new OutputMessage(new Date().toString(), message));
        return ResponseBuilder.newResponse(ErrorCode.SUCCESS);
    }

    @RequestMapping("/send/{user}")
    public BasicResponse broadcast(@PathVariable("user") String user, @RequestBody Message message) {
        template.sendMsgToUser(user, new OutputMessage(new Date().toString(), message));
        return ResponseBuilder.newResponse(ErrorCode.SUCCESS);
    }

    @RequestMapping("/disconnect/{sessionId}")
    public BasicResponse disconnect(@PathVariable("sessionId") String sessionId) {
        boolean isSuccess = sessions.forceCloseSession(sessionId);
        return isSuccess ?
                ResponseBuilder.newResponse(ErrorCode.SUCCESS)
                :
                ResponseBuilder.newResponse(ErrorCode.UNKNOWN_ERROR);
    }

}
