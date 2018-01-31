package tableview;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RentalTableView {
    private IntegerProperty item_id;
    private StringProperty name;
    private IntegerProperty rental_count;


    public RentalTableView(int item_id, String name, int rental_count) {
        this.item_id = new SimpleIntegerProperty(item_id);
        this.name = new SimpleStringProperty(name);
        this.rental_count = new SimpleIntegerProperty(rental_count);
    }

    public int getItem_id() {
        return item_id.get();
    }

    public IntegerProperty item_idProperty() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id.set(item_id);
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

    public int getRental_count() {
        return rental_count.get();
    }

    public IntegerProperty rental_countProperty() {
        return rental_count;
    }

    public void setRental_count(int rental_count) {
        this.rental_count.set(rental_count);
    }
}
