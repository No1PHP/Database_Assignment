package entity.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "Schedule_record")
public class ScheduleRecord implements Serializable {
    private static final long serialVersionUID = 7L;
    //primary key, auto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Schedule_id", updatable = false)
    private Integer ScheduleID;

    //corresponds to operationRecords table
    @Column(name = "op_OrderID", nullable = false)
    private Integer operationID;

    @Column(name = "staff_id", nullable = false)
    private Integer staffID;

    @Column(name = "work_time_start", nullable = false)
    private Date timeScheduledToStartWorking;

    @Column(name = "work_time_end", nullable = false)
    private Date timeScheduledToEndWorking;

    /********************************************************/
    public Integer getScheduleID() {
        return ScheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        ScheduleID = scheduleID;
    }

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
}
