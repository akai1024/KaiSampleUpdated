package kai.sample.websocket;

import kai.sample.datasource.service.UserService;
import kai.sample.util.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebSocketSessions sessions;

    @Autowired
    private UserService userService;

    private void printSessions(String msg) {
        LogHelper.logInfoParam(logger, msg + ", {}", sessions);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/user", "/subscribe");
        config.setUserDestinationPrefix("/user");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chatroom");
        registry.addEndpoint("/chatroom").withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        sessions.addWebSocketSession(session);
                        printSessions("afterConnectionEstablished");
                        super.afterConnectionEstablished(session);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                        sessions.removeSessionId(session.getId());
                        sessions.removeWebSocketSession(session);
                        printSessions("afterConnectionClosed");
                        super.afterConnectionClosed(session, closeStatus);
                    }
                };
            }
        });

    }

    /**
     * 頻道內的攔截訊息
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                String sessionId = accessor.getSessionId();
                switch (accessor.getCommand()) {
                    case CONNECT: {
                        String user = accessor.getFirstNativeHeader("user");
                        String pwd = accessor.getFirstNativeHeader("pwd");

                        // 密碼檢查
                        if (userService.isPasswordCorrect(user, pwd)) {
                            // session內提供UserPrinciple資訊
                            accessor.setUser(new SessionUser(sessionId, user, pwd));
                            // 註冊連線
                            sessions.registerSessionId(user, sessionId);
                        } else {
                            sessions.forceCloseSession(sessionId);
                        }

                        printSessions("StompCommand.CONNECT");
                        break;
                    }
                    case DISCONNECT: {
                        // 攔截到斷線訊息時不用處理移除連線
                        // 真正斷線處理流程在Handler
                        printSessions("StompCommand.DISCONNECT");
                        break;
                    }
                    default: {

                    }
                }

                return message;
            }
        });

    }

}