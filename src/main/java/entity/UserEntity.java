package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "appdb")
public class UserEntity {
    private int userId;
    private String login;
    private String password;
    private int admin;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "admin")
    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId &&
                admin == that.admin &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, login, password, admin);
    }
}
