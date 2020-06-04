package dao.tables;

import com.alibaba.fastjson.JSONObject;
import dao.JSONAble;
import dao.enums.MaterialTypes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Material")
public class Material implements JSONAble {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "type", nullable = false)
    private Byte type;

    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    //fresh period
    @Column(name = "availableTime", nullable = false)
    private Float availablePeriod;
    /********************************************************/
    //material order foreign key
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private Set<MaterialOrder> materialOrders = new HashSet<>();

    @ManyToMany(mappedBy = "materials", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Recipe> recipes = new HashSet<>();

    //recipeUsage foreign key
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private Set<MaterialUsage> materialUsages = new HashSet<>();

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", MaterialTypes.getByIndex(type));
        json.put("unitPrice", unitPrice);
        json.put("availablePeriod", availablePeriod);
        return json;
    }
}
