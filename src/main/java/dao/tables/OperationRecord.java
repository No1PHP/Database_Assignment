package dao.tables;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "OperationRecord")
public class OperationRecord implements Serializable {
    private static final long serialVersionUID = 5L;
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operationId")
    private Integer operationID;

    @Column(name = "staffId", nullable = false, insertable = false, updatable = false)
    private Integer staffID;

    //pull, order, day shift(schedule), stallChange
    @Column(name = "operationType", nullable = false, updatable = false)
    private Byte operationType;

    //operation details
    @Column(name = "note")
    private String note;

    //need parsing from sql datetime type
    @Column(name = "operationTime", nullable = false, updatable = false)
    private Timestamp operationTime = new Timestamp(System.currentTimeMillis());

    @Column(name = "willSendUpdateMessage", nullable = false)
    private Boolean willSendUpdateMessage;
    /********************************************************/
    //operation record foreign key
    @ManyToOne(targetEntity = Staff.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "staffId", referencedColumnName = "staff_Id")
    private Staff staff;

    //material order foreign key
    @OneToOne(mappedBy = "orderRecord", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    private MaterialOrder orderedMaterialRecord;

    //material order foreign key
    @OneToOne(mappedBy = "storageRecord", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private MaterialOrder storageMaterialRecord;

    //schedule record foreign key
    @OneToOne(mappedBy = "operationRecord", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    private ScheduleRecord scheduleRecord;

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

    public Byte getOperationType() {
        return operationType;
    }

    public void setOperationType(Byte operationType) {
        this.operationType = operationType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Timestamp operationTime) {
        this.operationTime = operationTime;
    }

    public Boolean getWillSendUpdateMessage() {
        return willSendUpdateMessage;
    }

    public void setWillSendUpdateMessage(Boolean willSendUpdateMessage) {
        this.willSendUpdateMessage = willSendUpdateMessage;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public MaterialOrder getOrderedMaterialRecord() {
        return orderedMaterialRecord;
    }

    public void setOrderedMaterialRecord(MaterialOrder orderedMaterialRecord) {
        this.orderedMaterialRecord = orderedMaterialRecord;
    }

    public MaterialOrder getStorageMaterialRecord() {
        return storageMaterialRecord;
    }

    public void setStorageMaterialRecord(MaterialOrder storageMaterialRecord) {
        this.storageMaterialRecord = storageMaterialRecord;
    }

    public ScheduleRecord getScheduleRecord() {
        return scheduleRecord;
    }

    public void setScheduleRecord(ScheduleRecord scheduleRecord) {
        this.scheduleRecord = scheduleRecord;
    }

    @Override
    public String toString() {
        return "OperationRecord{" +
                "operationID=" + operationID +
                ", staffID=" + staffID +
                ", operationType=" + operationType +
                ", note='" + note + '\'' +
                ", operationTime=" + operationTime +
                ", willSendUpdateMessage=" + willSendUpdateMessage +
                '}';
    }
}
