package Entity;

import Utils.UtilsImpl;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zhining
 * @description Operation including pull, push, update of table contents object
 * @create 2020-04-23-03-24
 **/
public class OperationRecord implements Serializable {
    private static final long serialVersionUID = 5L;
    //primary key
    private int operationID;

    private int staffID;

    //pull, order, dayshift(schedule), stallChange
    private String oprationType;

    //operation details
    private String note;

    //need parsing from sql datetime type
    private String operationTime;

    private boolean willSendUpdateMessage;

    public void setOperationID(int operationID) {
        this.operationID = operationID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public void setOprationType(String oprationType) {
        this.oprationType = oprationType;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public void setWillSendUpdateMessage(boolean willSendUpdateMessage) {
        this.willSendUpdateMessage = willSendUpdateMessage;
    }

    public OperationRecord(int operationID, int staffID, String oprationType, String note, Date operationTime, boolean willSendUpdateMessage) {
        this.operationID = operationID;
        this.staffID = staffID;
        this.oprationType = oprationType;
        this.note = note;
        this.operationTime = UtilsImpl.dateParsingToString(operationTime);
        this.willSendUpdateMessage = willSendUpdateMessage;
    }
}
