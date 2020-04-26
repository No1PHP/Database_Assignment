package entity.tables;

import Utils.UtilsImpl;
import entity.enums.OperationType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "OperationRecord")
public class OperationRecord implements Serializable {
    private static final long serialVersionUID = 5L;
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer operationID;

    @Column(name = "staffId", nullable = false)
    private Integer staffID;

    //pull, order, dayshift(schedule), stallChange
    @Column(name = "operationType", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    //operation details
    @Column(name = "note", nullable = false)
    private String note;

    //need parsing from sql datetime type
    @Column(name = "operationTime", nullable = false)
    private String operationTime;

    @Column(name = "willSendUpdateMessage", nullable = false)
    private Boolean willSendUpdateMessage;
}
