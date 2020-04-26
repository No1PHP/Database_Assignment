package entity.tables;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Recipe")
public class Recipe implements Serializable {
    private static final long serialVersionUID = 6L;
    //recipe index, primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recipeID;

    // @description recipe name, length<30
    @Column(name = "recipeName", length = 30, nullable = false)
    private String  recipeName;

    //set type in mysql
    @Column(name = "relevantIngredient", nullable = false)
    private String relevantIngredient;

    //recipe price
    @Column(name = "price", nullable = false)
    private Float price;
}
