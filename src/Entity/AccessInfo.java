package Entity;

import java.io.Serializable;

/**
 * @author Zhining
 * @description Table access query object
 * @create 2020-04-23-03-20
 **/
public class AccessInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    //primary key
    private String position;

    //access authority to the order related tables
    private boolean AccessToOrder;

    //access authority to the Staff related tables
    private boolean AccessToStaff;

    //access authority to the stock(materials?) related tables
    private boolean AccessToStock;

    public String getPosition() {
        return position;
    }

    public boolean hasAccessToOrder() {
        return AccessToOrder;
    }

    public boolean hasAccessToStaff() {
        return AccessToStaff;
    }

    public boolean hasAccessToStock() {
        return AccessToStock;
    }

    public AccessInfo(String position, boolean accessToOrder, boolean accessToStaff, boolean accessToStock) {
        this.position = position;
        AccessToOrder = accessToOrder;
        AccessToStaff = accessToStaff;
        AccessToStock = accessToStock;
    }
}
