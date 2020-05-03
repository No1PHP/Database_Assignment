package dao.tables;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Material")
public class Material implements Serializable {
    private static final long serialVersionUID = 3L;
    //material id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private Integer name;

    @Column(name = "type", nullable = false)
    private Byte type;

    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "availableAmount")
    private Float availableAmount = 0.0F;

    //fresh period
    @Column(name = "availableTime", nullable = false)
    private Integer availablePeriod;

    /********************************************************/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Float availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Integer getAvailablePeriod() {
        return availablePeriod;
    }

    public void setAvailablePeriod(Integer availablePeriod) {
        this.availablePeriod = availablePeriod;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name=" + name +
                ", type=" + type +
                ", unitPrice=" + unitPrice +
                ", availableAmount=" + availableAmount +
                ", availablePeriod=" + availablePeriod +
                '}';
    }
}
