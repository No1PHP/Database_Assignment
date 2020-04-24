package Entity;

import java.io.Serializable;

/**
 * @author Zhining
 * @description Account details
 * @create 2020-04-23-03-36
 **/
public class Account implements Serializable {//这个类是否实现接口还需商议
    private static final long serialVersionUID = 2L;
    //primary key
    private int staffID;

    //foreign key
    private String position;

    private String accountName;

    private String passwordHashValue;

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPasswordHashValue(String passwordHashValue){
        this.passwordHashValue = passwordHashValue;
    }

    public String getPasswordHashValue() {
        return passwordHashValue;
    }

    public Account(int staffID, String position, String accountName) {
        this.staffID = staffID;
        this.position = position;
        this.accountName = accountName;
    }

    public Account(int staffID, String position, String accountName, String passwordHashValue) {
        this.staffID = staffID;
        this.position = position;
        this.accountName = accountName;
        this.passwordHashValue = passwordHashValue;
    }
}
