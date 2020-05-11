package dao.tables;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "MaterialUsage")
public class MaterialUsage implements Serializable {
    private static final long serialVersionUID = 11L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Integer usageId;

    @Column(name = "stallID", nullable = false, insertable = false, updatable = false)
    private Integer stallID;

    @Column(name = "materialID", nullable = false, insertable = false, updatable = false)
    private Integer materialID;

    @Column(name = "storageID", nullable = false, insertable = false, updatable = false)
    private Integer storageID;

    @Column(name = "time", nullable = false, updatable = false)
    private Timestamp time = new Timestamp(System.currentTimeMillis());

    @Column(name = "amount", nullable = false)
    private Float amount;
    /********************************************************/
    //recipeUsage foreign key
    @ManyToOne(targetEntity = Stall.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "stallID", referencedColumnName = "stall_id")
    private Stall stall;

    //recipeUsage foreign key
    @ManyToOne(targetEntity = Material.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "materialID", referencedColumnName = "id")
    private Material material;

    @ManyToOne(targetEntity = MaterialOrder.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "storageID", referencedColumnName = "op_storageID")
    private MaterialOrder materialOrder;
}
