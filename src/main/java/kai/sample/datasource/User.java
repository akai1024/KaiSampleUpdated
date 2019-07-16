package kai.sample.datasource;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = User.TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class User {

    public static final String TABLE_NAME = "users";

    @Id
    @Column
    private String account;

    @Column
    private String password;

    @CreatedDate
    @Column
    private Date create_time;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

}
