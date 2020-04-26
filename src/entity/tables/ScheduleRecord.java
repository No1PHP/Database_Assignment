package entity.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Schedule_record")
public class ScheduleRecord implements Serializable {
    private static final long serialVersionUID = 7L;
    //primary key, auto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ScheduleID;

    //corresponds to operationRecords table
    @Column(name = "op_OrderID", nullable = false)
    private Integer operationID;

    @Column(name = "staff_id", nullable = false)
    private Integer staffID;

    @Column(name = "work_time_start", nullable = false)
    private String timeScheduledToStartWorking;

    @Column(name = "work_time_end", nullable = false)
    private String timeScheduledToEndWorking;
}
