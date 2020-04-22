package com.dao.Entity;

/*
* @author zhining
* @create 2020-4-22-11:12
* @description recipe object
 */
public class Recipe {

    //recipe index
    public int recipeID;

    // @description recipe name, length<30
    public char recipeName;

    //set type in mysql
    public char releventIngredient;

    //recipe price
    public float price;

    public int getRecipeID() {
        return recipeID;
    }

    public char getRecipeName() {
        return recipeName;
    }

    public char getReleventIngredient() {
        return releventIngredient;
    }

    public float getPrice() {
        return price;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setRecipeName(char recipeName) {
        this.recipeName = recipeName;
    }

    public void setReleventIngredient(char releventIngredient) {
        this.releventIngredient = releventIngredient;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
