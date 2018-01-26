package entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client", schema = "appdb")
public class ClientEntity
{
    private int client_id;
    private String name;
    private String address;
    private String postalcode;
    private String phonenumber;
    private String nip;

    private List<ItemEntity> itemList;
    private List<RentalDetailsEntity> rentalDetailsList;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    public int getClient_id()
    {
        return client_id;
    }

    public void setClient_id(int client_id)
    {
        this.client_id = client_id;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Basic
    @Column(name = "address", nullable = false)
    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Basic
    @Column(name = "postalcode", nullable = false)
    public String getPostalcode()
    {
        return postalcode;
    }

    public void setPostalcode(String postalcode)
    {
        this.postalcode = postalcode;
    }


    @Basic
    @Column(name = "phonenumber", nullable = false)
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    @Basic
    @Column(name = "nip", nullable = true)
    public String getNip()
    {
        return nip;
    }

    public void setNip(String nip)
    {
        this.nip = nip;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rental",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    public List<ItemEntity> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemEntity> orderList) {
        this.itemList = orderList;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rentalinfo",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "rental_id")
    )
    public List<RentalDetailsEntity> getRentalDetailsList() {
        return rentalDetailsList;
    }

    public void setRentalDetailsList(List<RentalDetailsEntity> rentalDetailsList) {
        this.rentalDetailsList = rentalDetailsList;
    }
}
