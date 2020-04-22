package com.dao.Entity;

/**
 * @author Zhining
 * @description Table access query object
 * @create 2020-04-23-03-20
 **/
public class AccessInfo {
    //primary key
    public String position;

    //access authority to the order related tables
    public boolean AccessToOrder;

    //access authority to the Staff related tables
    public boolean AccessToStaff;

    //access authority to the stock(materials?) related tables
    public boolean AccessToStock;

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
