package com.dao.Entity;

/**
 * @author Zhining
 * @description raw material of recipes  object
 * @create 2020-04-23-01-13
 **/
public class material {
    //material id
    public int id;

    public int name;

    public String type;

    //单价
    public float unitPrice;

    //fresh period
    public int availablePeriod;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAvailablePeriod() {
        return availablePeriod;
    }

    public void setAvailablePeriod(int availablePeriod) {
        this.availablePeriod = availablePeriod;
    }

    public material(int id, int name, String type, float unitPrice, int availablePeriod) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.unitPrice = unitPrice;
        this.availablePeriod = availablePeriod;
    }
}
