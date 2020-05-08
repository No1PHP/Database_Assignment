package dao.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Material")
public class Material implements Serializable {
    private static final long serialVersionUID = 3L;
    //material id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

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
    //material order foreign key
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private Set<MaterialOrder> materialOrders = new HashSet<>();

    /********************************************************/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public Set<MaterialOrder> getMaterialOrders() {
        return materialOrders;
    }

    public void setMaterialOrders(Set<MaterialOrder> materialOrders) {
        this.materialOrders = materialOrders;
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
