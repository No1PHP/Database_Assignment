package dao.tables;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
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
     @Column(name = "materialID", nullable = false, insertable = false, updatable = false)
     private Integer materialID;

    @Column(name = "amount", nullable = false)
    private Float materialAmount;
    /********************************************************/
    //material order foreign key
    @ManyToOne(targetEntity = Material.class, optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "materialID", referencedColumnName = "id")
    private Material material;

    //material order foreign key
    @OneToOne(targetEntity = OperationRecord.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "op_OrderID", referencedColumnName = "operationId")
    private OperationRecord orderRecord;

    //material order foreign key
    @OneToOne(targetEntity = OperationRecord.class, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "op_StorageID", referencedColumnName = "operationId")
    private OperationRecord storageRecord;

    @OneToMany(mappedBy = "materialOrder", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    private Set<MaterialUsage> materialUsages = new HashSet<>();
}
