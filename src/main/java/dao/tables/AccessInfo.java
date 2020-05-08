package dao.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AccessInfo")
public class AccessInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position")
    private String position;

    //access authority to the order related tables
    @Column(name = "AccessToOrder", nullable = false)
    private Boolean accessToOrder;

    //access authority to the Staff related tables
    @Column(name = "AccessToStaff", nullable = false)
    private Boolean accessToStaff;

    //access authority to the stock(materials?) related tables
    @Column(name = "AccessToStock", nullable = false)
    private Boolean accessToStock;
    /********************************************************/
    //account foreign key
    @OneToMany(mappedBy = "accessInfo", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Account> accounts = new HashSet<>();

    /********************************************************/
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getAccessToOrder() {
        return accessToOrder;
    }

    public void setAccessToOrder(Boolean accessToOrder) {
        this.accessToOrder = accessToOrder;
    }

    public Boolean getAccessToStaff() {
        return accessToStaff;
    }

    public void setAccessToStaff(Boolean accessToStaff) {
        this.accessToStaff = accessToStaff;
    }

    public Boolean getAccessToStock() {
        return accessToStock;
    }

    public void setAccessToStock(Boolean accessToStock) {
        this.accessToStock = accessToStock;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "AccessInfo{" +
                "position='" + position + '\'' +
                ", AccessToOrder=" + accessToOrder +
                ", AccessToStaff=" + accessToStaff +
                ", AccessToStock=" + accessToStock +
                '}';
    }
}
