package kai.sample.datasource.service;

import kai.sample.controller.chat.Message;
import kai.sample.datasource.ChatLog;
import kai.sample.datasource.ChatLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ChatLogService {

    private ThreadPoolTaskExecutor executor;

    @Autowired
    private ChatLogRepository chatLogRepo;

    @PostConstruct
    public void init() {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.initialize();
    }

    /**
     * 在異步寫聊天紀錄
     */
    public void saveChatLog(Message message) {
        executor.execute(() -> {
            ChatLog log = new ChatLog();
            log.setUser(message.getFrom());
            log.setText(message.getText());
            chatLogRepo.save(log);
        });
    }

}
