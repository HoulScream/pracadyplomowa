package tableview;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientTableView {
    private IntegerProperty client_id;
    private StringProperty name;
    private StringProperty address;
    private StringProperty postalcode;
    private StringProperty telephonenumber;
    private StringProperty nip;

    public ClientTableView(int client_id, String name, String address, String postalcode, String phonenumber, String nip) {
        this.client_id = new SimpleIntegerProperty(client_id);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.postalcode = new SimpleStringProperty(postalcode);
        this.telephonenumber = new SimpleStringProperty(phonenumber);
        this.nip = new SimpleStringProperty(nip);
    }

    public int getClient_id() {
        return client_id.get();
    }

    public IntegerProperty client_idProperty() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id.set(client_id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }


    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPostalcode() {
        return postalcode.get();
    }

    public StringProperty postalcodeProperty() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode.set(postalcode);
    }

    public String getTelephonenumber() {
        return telephonenumber.get();
    }

    public StringProperty telephonenumberProperty() {
        return telephonenumber;
    }

    public void setTelephonenumber(String telephonenumber) {
        this.telephonenumber.set(telephonenumber);
    }

    public String getNip() {
        return nip.get();
    }

    public StringProperty nipProperty() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip.set(nip);
    }

    @Override
    public String toString() {
        return getName();
    }
}
