package com.dao.Entity;

/**
 * @author Zhining
 * @description Ordering of the materials table object, requires trigger pertinence
 * @create 2020-04-23-01-52
 **/
public class materialOrder {
     //primary key
     public int orderID;

     //corresponds to the operationRecord table, foreign key
     public int operationID;

     //??
     public int operationStorageID;

     //dictation index
     public int materialID;

     public float materialAmount;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOperationID() {
        return operationID;
    }

    public void setOperationID(int operationID) {
        this.operationID = operationID;
    }

    public int getOperationStorageID() {
        return operationStorageID;
    }

    public void setOperationStorageID(int operationStorageID) {
        this.operationStorageID = operationStorageID;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public float getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(float materialAmount) {
        this.materialAmount = materialAmount;
    }

    public materialOrder(int orderID, int operationID, int operationStorageID, int materialID, float materialAmount) {
        this.orderID = orderID;
        this.operationID = operationID;
        this.operationStorageID = operationStorageID;
        this.materialID = materialID;
        this.materialAmount = materialAmount;
    }
}
