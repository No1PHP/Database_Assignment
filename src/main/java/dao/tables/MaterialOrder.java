package dao.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "MaterialOrder")
public class MaterialOrder implements Serializable {
    @Id
    @Column(name = "op_OrderID", updatable = false)
     private Integer operationOrderID;

     //??
     @Column(name = "op_storageID", insertable = false, updatable = false)
     private Integer operationStorageID;

     //dictation index
     @Column(name = "material_name", nullable = false, insertable = false, updatable = false)
     private String materialName;

    @Column(name = "amount", nullable = false)
    private Float materialAmount;
    /********************************************************/
    //material order foreign key
    @ManyToOne(targetEntity = Material.class, optional = false, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "material_name", referencedColumnName = "name")
    private Material material;

    //material order foreign key
    @OneToOne(targetEntity = OperationRecord.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "op_OrderID", referencedColumnName = "operationId")
    private OperationRecord orderRecord;

    //material order foreign key
    @OneToOne(targetEntity = OperationRecord.class, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "op_storageID", referencedColumnName = "operationId")
    private OperationRecord storageRecord;

    @OneToMany(mappedBy = "materialOrder", cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<MaterialUsage> materialUsages = new HashSet<>();

    @Override
    public String toString() {
        return "{" +
                "operationOrderID=" + operationOrderID +
                ", operationStorageID=" + operationStorageID +
                ", materialName='" + materialName + '\'' +
                ", materialAmount=" + materialAmount +
                '}';
    }
}
