package tableview;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserTableView {
    private IntegerProperty id;
    private StringProperty username;
    private StringProperty password;
    private IntegerProperty admin;

    public UserTableView(int id, String username, String password, int admin) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.admin = new SimpleIntegerProperty(admin);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getAdmin() {
        return admin.get();
    }

    public IntegerProperty adminProperty() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin.set(admin);
    }

}


