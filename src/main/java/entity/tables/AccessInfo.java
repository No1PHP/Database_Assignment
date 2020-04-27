package entity.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "AccessInfo")
public class AccessInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position", updatable = false)
    private String position;

    //access authority to the order related tables
    @Column(name = "AccessToOrder", nullable = false)
    private Byte AccessToOrder;

    //access authority to the Staff related tables
    @Column(name = "AccessToStaff", nullable = false)
    private Byte AccessToStaff;

    //access authority to the stock(materials?) related tables
    @Column(name = "AccessToStock", nullable = false)
    private Byte AccessToStock;

    /********************************************************/
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Byte getAccessToOrder() {
        return AccessToOrder;
    }

    public void setAccessToOrder(Byte accessToOrder) {
        AccessToOrder = accessToOrder;
    }

    public Byte getAccessToStaff() {
        return AccessToStaff;
    }

    public void setAccessToStaff(Byte accessToStaff) {
        AccessToStaff = accessToStaff;
    }

    public Byte getAccessToStock() {
        return AccessToStock;
    }

    public void setAccessToStock(Byte accessToStock) {
        AccessToStock = accessToStock;
    }
}
