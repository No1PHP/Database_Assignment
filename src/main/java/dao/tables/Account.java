package dao.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Account")
public class Account implements Serializable {
    private static final long serialVersionUID = 2L;
    //primary key
    @Id
    @Column(name = "staffId")
    private Integer staffID;

    //foreign key
    @Column(name = "position", length = 20, insertable = false, updatable = false)
    private String position;

    @Column(name = "accountName", length = 20, nullable = false, unique = true)
    private String accountName;

    @Column(name = "password", length = 20, nullable = false)
    private String passwordHashValue;
    /********************************************************/
    //account foreign key
    @OneToOne(targetEntity = Staff.class, optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "staffId", referencedColumnName = "staff_id")
    private Staff staff;

    //account foreign key
    @ManyToOne(targetEntity = AccessInfo.class, optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "position", referencedColumnName = "position")
    private AccessInfo accessInfo;

    /********************************************************/
    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPasswordHashValue() {
        return passwordHashValue;
    }

    public void setPasswordHashValue(String passwordHashValue) {
        this.passwordHashValue = passwordHashValue;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
        this.staffID = staff.getStaffID();
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "staffID=" + staffID +
                ", position='" + position + '\'' +
                ", accountName='" + accountName + '\'' +
                ", passwordHashValue='" + passwordHashValue + '\'' +
                '}';
    }
}
