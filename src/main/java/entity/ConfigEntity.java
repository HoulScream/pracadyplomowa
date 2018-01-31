package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "config", schema = "appdb")
public class ConfigEntity {
    private int configId;
    private String hirename;
    private String hireadress;
    private String hirepostalcode;
    private String hirephonenumber;
    private String hirenip;
    private int maxrentaltime;
    private int dailypenality;
    private String orderconfirmationfolder;
    private String returnconfirmationfolder;

    @Id
    @Column(name = "config_id")
    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    @Basic
    @Column(name = "hirename")
    public String getHirename() {
        return hirename;
    }

    public void setHirename(String hirename) {
        this.hirename = hirename;
    }

    @Basic
    @Column(name = "hireadress")
    public String getHireadress() {
        return hireadress;
    }

    public void setHireadress(String hireadress) {
        this.hireadress = hireadress;
    }

    @Basic
    @Column(name = "hirepostalcode")
    public String getHirepostalcode() {
        return hirepostalcode;
    }

    public void setHirepostalcode(String hirepostalcode) {
        this.hirepostalcode = hirepostalcode;
    }

    @Basic
    @Column(name = "hirephonenumber")
    public String getHirephonenumber() {
        return hirephonenumber;
    }

    public void setHirephonenumber(String hirephonenumber) {
        this.hirephonenumber = hirephonenumber;
    }

    @Basic
    @Column(name = "hirenip")
    public String getHirenip() {
        return hirenip;
    }

    public void setHirenip(String hirenip) {
        this.hirenip = hirenip;
    }

    @Basic
    @Column(name = "maxrentaltime")
    public int getMaxrentaltime() {
        return maxrentaltime;
    }

    public void setMaxrentaltime(int maxrentaltime) {
        this.maxrentaltime = maxrentaltime;
    }

    @Basic
    @Column(name = "dailypenality")
    public int getDailypenality() {
        return dailypenality;
    }

    public void setDailypenality(int dailypenality) {
        this.dailypenality = dailypenality;
    }

    @Basic
    @Column(name = "orderconfirmationfolder")
    public String getOrderconfirmationfolder() {
        return orderconfirmationfolder;
    }

    public void setOrderconfirmationfolder(String orderconfirmationfolder) {
        this.orderconfirmationfolder = orderconfirmationfolder;
    }

    @Basic
    @Column(name = "returnconfirmationfolder")
    public String getReturnconfirmationfolder() {
        return returnconfirmationfolder;
    }

    public void setReturnconfirmationfolder(String returnconfirmationfolder) {
        this.returnconfirmationfolder = returnconfirmationfolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigEntity that = (ConfigEntity) o;
        return configId == that.configId &&
                maxrentaltime == that.maxrentaltime &&
                dailypenality == that.dailypenality &&
                Objects.equals(hirename, that.hirename) &&
                Objects.equals(hireadress, that.hireadress) &&
                Objects.equals(hirepostalcode, that.hirepostalcode) &&
                Objects.equals(hirephonenumber, that.hirephonenumber) &&
                Objects.equals(hirenip, that.hirenip) &&
                Objects.equals(orderconfirmationfolder, that.orderconfirmationfolder) &&
                Objects.equals(returnconfirmationfolder, that.returnconfirmationfolder);
    }

    @Override
    public int hashCode() {

        return Objects.hash(configId, hirename, hireadress, hirepostalcode, hirephonenumber, hirenip, maxrentaltime, dailypenality, orderconfirmationfolder, returnconfirmationfolder);
    }
}
