package dao.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MaterialOrder")
public class MaterialOrder implements Serializable {
    private static final long serialVersionUID = 4L;
     //primary key
     //corresponds to the operationRecord table, foreign key
    @Id
    @Column(name = "op_OrderID")
     private Integer operationOrderID;

     //??
     @Column(name = "op_storageID")
     private Integer operationStorageID;

     //dictation index
     @Column(name = "materialID", nullable = false)
     private Integer materialID;

    @Column(name = "amount", nullable = false)
    private Float materialAmount;
    /********************************************************/
    //material order foreign key
    @ManyToOne(targetEntity = Material.class, optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "materialID", referencedColumnName = "id")
    private Material material;


    //material order foreign key
    @OneToOne(targetEntity = OperationRecord.class, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "op_OrderID", referencedColumnName = "operationId")
    private OperationRecord orderRecord;

    //material order foreign key
    @OneToOne(targetEntity = OperationRecord.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "op_StorageID", referencedColumnName = "operationId")
    private OperationRecord storageRecord;
    /********************************************************/
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public OperationRecord getOrderRecord() {
        return orderRecord;
    }

    public void setOrderRecord(OperationRecord orderRecord) {
        this.orderRecord = orderRecord;
    }

    public OperationRecord getStorageRecord() {
        return storageRecord;
    }

    public void setStorageRecord(OperationRecord storageRecord) {
        this.storageRecord = storageRecord;
    }

    @Override
    public String toString() {
        return "MaterialOrder{" +
                "operationOrderID=" + operationOrderID +
                ", operationStorageID=" + operationStorageID +
                ", materialID=" + materialID +
                ", materialAmount=" + materialAmount +
                '}';
    }
}
