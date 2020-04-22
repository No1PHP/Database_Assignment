package com.dao.Entity;

/**
 * @author Zhining
 * @description information of stalls
 * @create 2020-04-23-02-17
 **/
public class stallInfo {
    public int stallID;

    public String stallName;

    public String stallLocation;

    public float stallRent;

    public float costLastMonth;

    public int manageTimeSoFar;

    public float aveMonthlySalesAmount;

    public float aveSalesIncome;

    public int getStallID() {
        return stallID;
    }

    public void setStallID(int stallID) {
        this.stallID = stallID;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public String getStallLocation() {
        return stallLocation;
    }

    public void setStallLocation(String  stallLocation) {
        this.stallLocation = stallLocation;
    }

    public float getStallRent() {
        return stallRent;
    }

    public void setStallRent(float stallRent) {
        this.stallRent = stallRent;
    }

    public float getCostLastMonth() {
        return costLastMonth;
    }

    public void setCostLastMonth(float costLastMonth) {
        this.costLastMonth = costLastMonth;
    }

    public int getManageTimeSoFar() {
        return manageTimeSoFar;
    }

    public void setManageTimeSoFar(int manageTimeSoFar) {
        this.manageTimeSoFar = manageTimeSoFar;
    }

    public float getAveMonthlySalesAmount() {
        return aveMonthlySalesAmount;
    }

    public void setAveMonthlySalesAmount(float aveMonthlySalesAmount) {
        this.aveMonthlySalesAmount = aveMonthlySalesAmount;
    }

    public float getAveSalesIncome() {
        return aveSalesIncome;
    }

    public void setAveSalesIncome(float aveSalesIncome) {
        this.aveSalesIncome = aveSalesIncome;
    }

    public stallInfo(int stallID, String stallName, String stallLocation, float stallRent, float costLastMonth, int manageTimeSoFar, float aveMonthlySalesAmount, float aveSalesIncome) {
        this.stallID = stallID;
        this.stallName = stallName;
        this.stallLocation = stallLocation;
        this.stallRent = stallRent;
        this.costLastMonth = costLastMonth;
        this.manageTimeSoFar = manageTimeSoFar;
        this.aveMonthlySalesAmount = aveMonthlySalesAmount;
        this.aveSalesIncome = aveSalesIncome;
    }
}
