package entity.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MaterialOrder")
public class MaterialOrder implements Serializable {
    private static final long serialVersionUID = 4L;
     //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_ID", updatable = false)
     private Integer orderID;

     //corresponds to the operationRecord table, foreign key
    @Column(name = "op_OrderID", nullable = false)
     private Integer operationOrderID;

     //??
     @Column(name = "op_storageID", nullable = false)
     private Integer operationStorageID;

     //dictation index
     @Column(name = "materialID", nullable = false)
     private Integer materialID;

    @Column(name = "amount", nullable = false)
    private Float materialAmount;

    /********************************************************/
    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getOperationOrderID() {
        return operationOrderID;
    }

    public void setOperationOrderID(Integer operationOrderID) {
        this.operationOrderID = operationOrderID;
    }

    public Integer getOperationStorageID() {
        return operationStorageID;
    }

    public void setOperationStorageID(Integer operationStorageID) {
        this.operationStorageID = operationStorageID;
    }

    public Integer getMaterialID() {
        return materialID;
    }

    public void setMaterialID(Integer materialID) {
        this.materialID = materialID;
    }

    public Float getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(Float materialAmount) {
        this.materialAmount = materialAmount;
    }
}