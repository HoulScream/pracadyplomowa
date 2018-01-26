package entity;

        import javax.persistence.*;
        import java.util.List;

@Entity
@Table(name = "item", schema = "appdb")
public class ItemEntity {
    private int item_id;
    private String name;
    private double bail;
    private double rentalprice;
    private int count;

    private List<RentalinfoEntity rentalInfoList;
    private List<ClientEntity> clientList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "bail", nullable = false)
    public double getBail() {
        return bail;
    }

    public void setBail(double bail) {
        this.bail = bail;
    }

    @Basic
    @Column(name = "rentalprice", nullable = false)
    public double getRentalprice() {
        return rentalprice;
    }

    public void setRentalprice(double rentalprice) {
        this.rentalprice = rentalprice;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @ManyToMany(mappedBy = "itemList")
    public List<ClientEntity> getClientList() {
        return clientList;
    }

    public void setClientList(List<ClientEntity> clientList) {
        this.clientList = clientList;
    }

    @ManyToMany(mappedBy = "rentalItemList")
    public List<RentalinfoEntity> getRentalInfoList() {
        return rentalInfoList;
    }

    public void setRentalInfoList(List<RentalinfoEntity> rentalInfoList) {
        this.rentalInfoList = rentalInfoList;
    }
}
