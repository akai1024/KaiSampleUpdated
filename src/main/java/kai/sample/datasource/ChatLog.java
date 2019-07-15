package kai.sample.datasource;

import javax.persistence.*;

@Entity
@Table(name = ChatLog.TABLE_NAME)
public class ChatLog {
    public static final String TABLE_NAME = "chat_log";

    @Id
    @Column(columnDefinition = "bigint not null auto_increment")
    private long id;

    @Column
    private String user;

    @Column
    private String text;

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
}
