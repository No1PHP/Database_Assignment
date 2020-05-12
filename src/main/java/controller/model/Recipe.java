package controller.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @description
 * @create 2020-05-03-02-04
 **/
@Getter
@Setter
public class Recipe {

     public Integer recipeID;
     public String recipeName;
     public String relevantIngredient;
     public float price;
     public String operationName;
     public String stallName;
}
