package kai.sample.datasource;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = ChatLog.TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class ChatLog {
    public static final String TABLE_NAME = "chat_log";

    @Id
    @Column(columnDefinition = "bigint not null auto_increment")
    private long id;

    @Column
    private String user;

    @Column
    private String text;

    @CreatedDate
    @Column
    private Date event_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }

}
