package dao.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RecipeUsage")
public class RecipeUsage implements Serializable {
    private static final long serialVersionUID = 11L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Integer usageId;

    @Column(name = "stallID", nullable = false)
    private Integer stallID;

    @Column(name = "materialID", nullable = false)
    private Integer materialID;

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

    /********************************************************/
    public Integer getUsageId() {
        return usageId;
    }

    public void setUsageId(Integer usageId) {
        this.usageId = usageId;

    }
    public Integer getStallID() {
        return stallID;
    }

    public void setStallID(Integer stallID) {
        this.stallID = stallID;
    }

    public Integer getMaterialID() {
        return materialID;
    }

    public void setMaterialID(Integer materialID) {
        this.materialID = materialID;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Stall getStall() {
        return stall;
    }

    public void setStall(Stall stall) {
        this.stall = stall;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
