package com.dao.Entity;

import Utils.UtilsImpl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zhining
 * @description staff information table object
 * @create 2020-04-23-02-25
 **/
public class staff {
    //primary key, auto_incr
    public int staffID;

    public String staffName;

    public String staffCategory;

    //different from mysql datetime type, need considering the parsing!!!
    public String timeStartWorking;

    //different from mysql datetime!!!
    public String timeEndWorking;

    //date parsing method
    private String dateParsingToString(Date date){
        String str = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }

        return str;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffCategory() {
        return staffCategory;
    }

    public void setStaffCategory(String staffCategory) {
        this.staffCategory = staffCategory;
    }

    public String getTimeStartWorking() {
        return timeStartWorking;
    }

    public void setTimeStartWorking(String timeStartWorking) {
        this.timeStartWorking = timeStartWorking;
    }

    public String getTimeEndWorking() {
        return timeEndWorking;
    }

    public void setTimeEndWorking(String timeEndWorking) {
        this.timeEndWorking = timeEndWorking;
    }

    public staff(int staffID, String staffName, String staffCategory, Date timeStartWorking, Date timeEndWorking) {

        this.staffID = staffID;
        this.staffName = staffName;
        this.staffCategory = staffCategory;
        this.timeStartWorking = UtilsImpl.dateParsingToString(timeStartWorking);
        this.timeEndWorking = UtilsImpl.dateParsingToString(timeEndWorking);
    }
}
