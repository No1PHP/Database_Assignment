package Entity;

import java.io.Serializable;

/*
* @author zhining
* @create 2020-4-22-11:12
* @description recipe object
 */
public class Recipe implements Serializable {
    private static final long serialVersionUID = 6L;

    //recipe index, primary key
    private int recipeID;

    // @description recipe name, length<30
    private String  recipeName;

    //set type in mysql
    private String releventIngredient;

    //recipe price
    private float price;

    public int getRecipeID() {
        return recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getReleventIngredient() {
        return releventIngredient;
    }

    public float getPrice() {
        return price;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setReleventIngredient(String releventIngredient) {
        this.releventIngredient = releventIngredient;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Recipe(int recipeID, String recipeName, String releventIngredient, float price) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.releventIngredient = releventIngredient;
        this.price = price;
    }
}
