package entity;

import javafx.beans.property.*;

public class Item {
    private IntegerProperty item_id;
    private StringProperty name;
    private DoubleProperty bail;
    private DoubleProperty rentalprice;
    private IntegerProperty count;

    public Item(int item_id, String name, double bail, double rentalprice, int count) {
        this.item_id = new SimpleIntegerProperty(item_id);
        this.name = new SimpleStringProperty(name);
        this.bail = new SimpleDoubleProperty(bail);
        this.rentalprice = new SimpleDoubleProperty(rentalprice);
        this.count = new SimpleIntegerProperty(count);
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

    public double getBail() {
        return bail.get();
    }

    public DoubleProperty bailProperty() {
        return bail;
    }

    public void setBail(double bail) {
        this.bail.set(bail);
    }

    public double getRentalprice() {
        return rentalprice.get();
    }

    public DoubleProperty rentalpriceProperty() {
        return rentalprice;
    }

    public void setRentalprice(double rentalprice) {
        this.rentalprice.set(rentalprice);
    }

    public int getCount() {
        return count.get();
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }
}
