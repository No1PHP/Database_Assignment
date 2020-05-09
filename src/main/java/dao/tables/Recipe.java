package dao.tables;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "Recipe")
public class Recipe implements Serializable {
    private static final long serialVersionUID = 6L;
    //recipe index, primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipeID")
    private Integer recipeID;

    // @description recipe name, length<30
    @Column(name = "recipeName", length = 30, nullable = false, unique = true)
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

    @ManyToMany(targetEntity = Material.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "Recipe_Material_Association",
            joinColumns = {@JoinColumn(name = "recipe_id", referencedColumnName = "recipeID")},
            inverseJoinColumns = {@JoinColumn(name = "material_id", referencedColumnName = "id")}
    )
    private Set<Material> materials = new HashSet<>();

    /********************************************************/

    public Integer getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(Integer recipeID) {
        this.recipeID = recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Set<TransactionRecord> getTransactionRecords() {
        return transactionRecords;
    }

    public void setTransactionRecords(Set<TransactionRecord> transactionRecords) {
        this.transactionRecords = transactionRecords;
    }

    public Set<Stall> getStalls() {
        return stalls;
    }

    public void setStalls(Set<Stall> stalls) {
        this.stalls = stalls;
    }

    public Set<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<Material> materials) {
        this.materials = materials;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeID=" + recipeID +
                ", recipeName='" + recipeName + '\'' +
                ", price=" + price +
                '}';
    }
}
