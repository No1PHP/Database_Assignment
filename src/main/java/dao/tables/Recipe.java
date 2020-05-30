package dao.tables;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Recipe")
public class Recipe {
    @Id
    @Column(name = "recipeName", length = 30)
    private String recipeName;

    //recipe price
    @Column(name = "price", nullable = false)
    private Float price;
    /********************************************************/
    //transaction record foreign key
    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Set<TransactionRecord> transactionRecords = new HashSet<>();

    @ManyToMany(mappedBy = "recipes", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Stall> stalls = new HashSet<>();

    @ManyToMany(targetEntity = Material.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Recipe_Material_Association",
            joinColumns = {@JoinColumn(name = "recipe_name", referencedColumnName = "recipeName")},
            inverseJoinColumns = {@JoinColumn(name = "material_name", referencedColumnName = "name")}
    )
    private Set<Material> materials = new HashSet<>();
    /********************************************************/
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{recipeName='");
        stringBuilder.append(recipeName);
        stringBuilder.append("', price=");
        stringBuilder.append(price);
        stringBuilder.append(", materials=[");
        int count = 0;
        for (Material e : materials) {
            stringBuilder.append('\'');
            stringBuilder.append(e.getName());
            stringBuilder.append('\'');
            if (++count < materials.size())
                stringBuilder.append(',');
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }
}
