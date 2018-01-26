package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rentaldetails", schema = "appdb")
public class RentalDetailsEntity {
    private int rentalId;
    private int itemId;
    private Timestamp rentalDate;
    private int rentalCount;

    private List<ClientEntity> clientList;

    @Id
    @Column(name = "rental_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    @Basic
    @Column(name = "item_id")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "rental_date")
    public Timestamp getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
    }

    @Basic
    @Column(name = "rental_count")
    public int getRentalCount() {
        return rentalCount;
    }

    public void setRentalCount(int rentalCount) {
        this.rentalCount = rentalCount;
    }

    @ManyToMany(mappedBy = "rentalDetailsList")
    public List<ClientEntity> getClientList() {
        return clientList;
    }

    public void setClientList(List<ClientEntity> clientList) {
        this.clientList = clientList;
    }
}
