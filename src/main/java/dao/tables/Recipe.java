package dao.tables;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Recipe")
@TypeDef(name = "json", typeClass = JsonStringType.class)
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

    //set type in mysql
    @Type(type = "json")
    @Column(name = "relevantIngredient", nullable = false, columnDefinition = "json")
    private List<String> relevantIngredient;

    //recipe price
    @Column(name = "price", nullable = false)
    private Float price;

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

    public List<String> getRelevantIngredient() {
        return relevantIngredient;
    }

    public void setRelevantIngredient(List<String> relevantIngredient) {
        this.relevantIngredient = relevantIngredient;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeID=" + recipeID +
                ", recipeName='" + recipeName + '\'' +
                ", relevantIngredient='" + relevantIngredient + '\'' +
                ", price=" + price +
                '}';
    }
}
