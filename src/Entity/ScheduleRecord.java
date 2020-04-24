package Entity;

import Utils.UtilsImpl;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zhining
 * @description working time arrangement info table object
 * @create 2020-04-23-02-53
 **/
public class ScheduleRecord implements Serializable {
    private static final long serialVersionUID = 7L;
    //primary key, auto
    private int ScheduleID;

    //corresponds to operationRecords table
    private int operationID;

    private int staffID;

    private String timeScheduledToStartWorking;

    private String timeScheduledToEndWorking;

    public int getScheduleID() {
        return ScheduleID;
    }

    public void setScheduleID(int scheduleID) {
        ScheduleID = scheduleID;
    }

    public int getOperationID() {
        return operationID;
    }

    public void setOperationID(int operationID) {
        this.operationID = operationID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getTimeScheduledToStartWorking() {
        return timeScheduledToStartWorking;
    }

    public void setTimeScheduledToStartWorking(String timeScheduledToStartWorking) {
        this.timeScheduledToStartWorking = timeScheduledToStartWorking;
    }

    public String getTimeScheduledToEndWorking() {
        return timeScheduledToEndWorking;
    }

    public void setTimeScheduledToEndWorking(String timeScheduledToEndWorking) {
        this.timeScheduledToEndWorking = timeScheduledToEndWorking;
    }

    public ScheduleRecord(int scheduleID, int operationID, int staffID, Date timeScheduledToStartWorking, Date timeScheduledToEndWorking) {
        ScheduleID = scheduleID;
        this.operationID = operationID;
        this.staffID = staffID;
        this.timeScheduledToStartWorking = UtilsImpl.dateParsingToString(timeScheduledToStartWorking);
        this.timeScheduledToEndWorking = UtilsImpl.dateParsingToString(timeScheduledToEndWorking);
    }
}
