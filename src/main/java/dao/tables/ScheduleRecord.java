package dao.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Schedule_record")
public class ScheduleRecord implements Serializable {
    private static final long serialVersionUID = 7L;
    //primary key
    //corresponds to operationRecords table
    @Id
    @Column(name = "op_ID")
    private Integer operationID;

    @Column(name = "staff_id", nullable = false, insertable = false, updatable = false)
    private Integer staffID;

    @Column(name = "work_time_start", nullable = false)
    private Timestamp timeScheduledToStartWorking;

    @Column(name = "work_time_end", nullable = false)
    private Timestamp timeScheduledToEndWorking;
    /********************************************************/
    //schedule record foreign key
    @ManyToOne(targetEntity = Staff.class, optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
    private Staff staff;

    //schedule record foreign key
    @OneToOne(targetEntity = OperationRecord.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "op_ID", referencedColumnName = "operationId")
    private OperationRecord operationRecord;

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

    public Timestamp getTimeScheduledToStartWorking() {
        return timeScheduledToStartWorking;
    }

    public void setTimeScheduledToStartWorking(Timestamp timeScheduledToStartWorking) {
        this.timeScheduledToStartWorking = timeScheduledToStartWorking;
    }

    public Timestamp getTimeScheduledToEndWorking() {
        return timeScheduledToEndWorking;
    }

    public void setTimeScheduledToEndWorking(Timestamp timeScheduledToEndWorking) {
        this.timeScheduledToEndWorking = timeScheduledToEndWorking;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public OperationRecord getOperationRecord() {
        return operationRecord;
    }

    public void setOperationRecord(OperationRecord operationRecord) {
        this.operationRecord = operationRecord;
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
