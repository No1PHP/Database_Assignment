package dao.tables;

import com.alibaba.fastjson.JSONObject;
import dao.JSONAble;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "MaterialUsage")
public class MaterialUsage implements JSONAble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Integer usageId;

    @Column(name = "stall_name", nullable = false, insertable = false, updatable = false)
    private String stallName;

    @Column(name = "material_name", nullable = false, insertable = false, updatable = false)
    private String materialName;

    @Column(name = "storageID", nullable = false, insertable = false, updatable = false)
    private Integer storageID;

    @Column(name = "time", nullable = false, updatable = false)
    private Timestamp time = new Timestamp(System.currentTimeMillis());

    @Column(name = "amount", nullable = false)
    private Float amount;
    /********************************************************/
    //recipeUsage foreign key
    @ManyToOne(targetEntity = Stall.class, optional = false, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "stall_name", referencedColumnName = "stall_name")
    private Stall stall;

    //recipeUsage foreign key
    @ManyToOne(targetEntity = Material.class, optional = false, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "material_name", referencedColumnName = "name")
    private Material material;

    @ManyToOne(targetEntity = MaterialOrder.class, optional = false, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "storageID", referencedColumnName = "op_storageID")
    private MaterialOrder materialOrder;

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("usageId", usageId);
        json.put("stallName", stallName);
        json.put("materialName", materialName);
        json.put("storageID", storageID);
        json.put("time", time);
        json.put("amount", amount);
        return json;
    }
}
