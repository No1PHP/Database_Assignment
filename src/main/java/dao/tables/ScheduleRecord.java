package dao.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "Schedule_record")
public class ScheduleRecord implements Serializable {
    private static final long serialVersionUID = 7L;
    //primary key
    //corresponds to operationRecords table
    @Id
    @Column(name = "op_ID", nullable = false)
    private Integer operationID;

    @Column(name = "staff_id", nullable = false)
    private Integer staffID;

    @Column(name = "work_time_start", nullable = false)
    private Date timeScheduledToStartWorking;

    @Column(name = "work_time_end", nullable = false)
    private Date timeScheduledToEndWorking;

    /********************************************************/
    public Integer getOperationID() {
        return operationID;
    }

    public void setOperationID(Integer operationID) {
        this.operationID = operationID;
    }

    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public Date getTimeScheduledToStartWorking() {
        return timeScheduledToStartWorking;
    }

    public void setTimeScheduledToStartWorking(Date timeScheduledToStartWorking) {
        this.timeScheduledToStartWorking = timeScheduledToStartWorking;
    }

    public Date getTimeScheduledToEndWorking() {
        return timeScheduledToEndWorking;
    }

    public void setTimeScheduledToEndWorking(Date timeScheduledToEndWorking) {
        this.timeScheduledToEndWorking = timeScheduledToEndWorking;
    }

    @Override
    public String toString() {
        return "ScheduleRecord{" +
                "operationID=" + operationID +
                ", staffID=" + staffID +
                ", timeScheduledToStartWorking=" + timeScheduledToStartWorking +
                ", timeScheduledToEndWorking=" + timeScheduledToEndWorking +
                '}';
    }
}
