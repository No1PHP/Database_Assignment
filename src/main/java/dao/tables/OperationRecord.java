package dao.tables;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OperationRecord")
public class OperationRecord implements Serializable {
    private static final long serialVersionUID = 5L;
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operationId")
    private Integer operationID;

    @Column(name = "staffId", nullable = false)
    private Integer staffID;

    //pull, order, day shift(schedule), stallChange
    @Column(name = "operationType", nullable = false, updatable = false)
    private Byte operationType;

    //operation details
    @Column(name = "note")
    private String note;

    //need parsing from sql datetime type
    @CreatedDate
    @Column(name = "operationTime", nullable = false, updatable = false)
    private Date operationTime;

    @Column(name = "willSendUpdateMessage", nullable = false)
    private Boolean willSendUpdateMessage;
    /********************************************************/
    //operation record foreign key
    @ManyToOne(targetEntity = Staff.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "staffId", referencedColumnName = "staff_Id")
    private Staff staff;

    //material order foreign key
    @OneToMany(targetEntity = MaterialOrder.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "op_OrderID", referencedColumnName = "operationId")
    private Set<MaterialOrder> materialOrders = new HashSet<>();

    //material order foreign key
    @OneToMany(targetEntity = MaterialOrder.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "op_StorageID", referencedColumnName = "operationId")
    private Set<MaterialOrder> materialStorage = new HashSet<>();

    //schedule record foreign key
    @OneToMany(targetEntity = ScheduleRecord.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "op_ID", referencedColumnName = "operationId")
    private Set<ScheduleRecord> scheduleRecords = new HashSet<>();

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

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
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

    public Set<MaterialOrder> getMaterialOrders() {
        return materialOrders;
    }

    public void setMaterialOrders(Set<MaterialOrder> materialOrders) {
        this.materialOrders = materialOrders;
    }

    public Set<MaterialOrder> getMaterialStorage() {
        return materialStorage;
    }

    public void setMaterialStorage(Set<MaterialOrder> materialStorage) {
        this.materialStorage = materialStorage;
    }

    public Set<ScheduleRecord> getScheduleRecords() {
        return scheduleRecords;
    }

    public void setScheduleRecords(Set<ScheduleRecord> scheduleRecords) {
        this.scheduleRecords = scheduleRecords;
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
