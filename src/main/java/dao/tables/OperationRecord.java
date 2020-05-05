package dao.tables;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

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
