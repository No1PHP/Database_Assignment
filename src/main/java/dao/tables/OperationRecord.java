package dao.tables;

import com.alibaba.fastjson.JSONObject;
import dao.JSONAble;
import dao.enums.OperationType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "OperationRecord")
public class OperationRecord implements JSONAble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operationId", insertable = false)
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
    @ManyToOne(targetEntity = Staff.class, optional = false, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "staffId", referencedColumnName = "staff_Id")
    private Staff staff;

    //material order foreign key
    @OneToOne(mappedBy = "orderRecord", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private MaterialOrder orderedMaterialRecord;

    //material order foreign key
    @OneToOne(mappedBy = "storageRecord", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private MaterialOrder storageMaterialRecord;

    //schedule record foreign key
    @OneToOne(mappedBy = "operationRecord", cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private ScheduleRecord scheduleRecord;
    /********************************************************/

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("operationID", operationID);
        json.put("staffID", staffID);
        json.put("operationType", OperationType.getByIndex(operationType));
        json.put("note", note);
        json.put("operationTime", operationTime.toString());
        json.put("willSendUpdateMessage", willSendUpdateMessage);
        return json;
    }
}
