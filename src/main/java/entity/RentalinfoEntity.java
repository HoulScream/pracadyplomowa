package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rentalinfo", schema = "appdb")
public class RentalinfoEntity {
    private int rentalId;
    private Timestamp rentalTime;
    private int rentalCount;

    private List<ItemEntity> rentalItemList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    @Basic
    @Column(name = "rental_time")
    public Timestamp getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(Timestamp rentalTime) {
        this.rentalTime = rentalTime;
    }

    @Basic
    @Column(name = "rental_count")
    public int getRentalCount() {
        return rentalCount;
    }

    public void setRentalCount(int rentalCount) {
        this.rentalCount = rentalCount;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rentaldetail",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "rental_id")
    )
    public List<ItemEntity> getRentalItemList() {
        return rentalItemList;
    }

    public void setRentalItemList(List<ItemEntity> rentalItemList) {
        this.rentalItemList = rentalItemList;
    }
}
