package dao.tables;

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
public class OperationRecord implements Serializable {
    private static final long serialVersionUID = 5L;
    //primary key
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
    @OneToOne(mappedBy = "storageRecord", cascade = {CascadeType.REFRESH})
    private MaterialOrder storageMaterialRecord;

    //schedule record foreign key
    @OneToOne(mappedBy = "operationRecord", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private ScheduleRecord scheduleRecord;
}
