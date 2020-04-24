package Entity;

import java.io.Serializable;

/**
 * @author Zhining
 * @description raw material of recipes  object
 * @create 2020-04-23-01-13
 **/
public class Material implements Serializable {
    private static final long serialVersionUID = 3L;
    //material id
    private int id;

    private int name;

    private String type;

    //单价
    private float unitPrice;

    //fresh period
    private int availablePeriod;

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

    public Material(int id, int name, String type, float unitPrice, int availablePeriod) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.unitPrice = unitPrice;
        this.availablePeriod = availablePeriod;
    }
}
