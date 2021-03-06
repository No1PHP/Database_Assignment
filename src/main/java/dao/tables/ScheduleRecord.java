package dao.tables;

import com.alibaba.fastjson.JSONObject;
import dao.JSONAble;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Schedule_record")
public class ScheduleRecord implements JSONAble {
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
    @ManyToOne(targetEntity = Staff.class, optional = false, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
    private Staff staff;

    //schedule record foreign key
    @OneToOne(targetEntity = OperationRecord.class, optional = false, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "op_ID", referencedColumnName = "operationId")
    private OperationRecord operationRecord;

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("operationID", operationID);
        json.put("staffID", staffID);
        json.put("timeScheduledToStartWorking", timeScheduledToStartWorking);
        json.put("timeScheduledToEndWorking", timeScheduledToEndWorking);
        return json;
    }
}
