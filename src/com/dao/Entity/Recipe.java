package com.dao.Entity;

/*
* @author zhining
* @create 2020-4-22-11:12
* @description recipe object
 */
public class Recipe {

    //recipe index, primary key
    public int recipeID;

    // @description recipe name, length<30
    public String  recipeName;

    //set type in mysql
    public String releventIngredient;

    //recipe price
    public float price;

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
