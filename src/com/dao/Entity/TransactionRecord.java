package com.dao.Entity;

import java.util.Date;

/**
 * @author Zhining
 * @description recipe selling, 销售记录
 * @create 2020-04-23-01-03
 **/
public class TransactionRecord {
    //id, auto primary key
    public int transactionID;

    //stall id
    public int stallID;

    //recipe's id that have been ordered, a foreign key of recipe table
    public int recipeID;

    //date being sold
    public Date transactionTime;

    //amount of the dish sold, default to be 1
    public int numbers = 1;

    //price of dish
    public float transactionPrice;

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getStallID() {
        return stallID;
    }

    public void setStallID(int stallID) {
        this.stallID = stallID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public float getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(float transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public TransactionRecord(int transactionID, int stallID, int recipeID, Date transactionTime, int numbers, float transactionPrice) {
        this.transactionID = transactionID;
        this.stallID = stallID;
        this.recipeID = recipeID;
        this.transactionTime = transactionTime;
        this.numbers = numbers;
        this.transactionPrice = transactionPrice;
    }
}
