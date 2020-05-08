package dao.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Account")
public class Account implements Serializable {//这个类是否实现接口还需商议
    private static final long serialVersionUID = 2L;
    //primary key
    @Id
    @Column(name = "staffId")
    private Integer staffID;

    //foreign key
    @Column(name = "position", length = 20)
    private String position;

    @Column(name = "accountName", length = 20, nullable = false, unique = true)
    private String accountName;

    @Column(name = "password", length = 20, nullable = false)
    private String passwordHashValue;
    /********************************************************/
    //operation record foreign key
    @OneToMany(targetEntity = OperationRecord.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "staffId", referencedColumnName = "staffId")
    private Set<OperationRecord> operationRecords = new HashSet<>();

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

    public Set<OperationRecord> getOperationRecords() {
        return operationRecords;
    }

    public void setOperationRecords(Set<OperationRecord> operationRecords) {
        this.operationRecords = operationRecords;
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
