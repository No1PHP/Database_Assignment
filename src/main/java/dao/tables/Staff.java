package dao.tables;

import com.alibaba.fastjson.JSONObject;
import dao.JSONAble;
import dao.enums.StaffCategoryTypes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Staff")
public class Staff implements JSONAble {
    private static final long serialVersionUID = 8L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", updatable = false)
    private Integer staffID;

    @Column(name = "staff_name", length = 30, nullable = false, updatable = false)
    private String staffName;

    @Column(name = "staff_category")
    private Byte staffCategory;

    //different from mysql datetime type, need considering the parsing!!!
    @Column(name = "Effe_work_time_starts")
    private Time timeStartWorking;

    //different from mysql datetime!!!
    @Column(name = "Effe_work_time_end")
    private Time timeEndWorking;
    /********************************************************/
    //account foreign key
    @OneToOne(mappedBy = "staff", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private Account account;

    //schedule record foreign key
    @OneToMany(mappedBy = "staff", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private Set<ScheduleRecord> scheduleRecords = new HashSet<>();

    //operation record foreign key
    @OneToMany(mappedBy = "staff", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private Set<OperationRecord> operationRecords = new HashSet<>();

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("staffID", staffID);
        json.put("staffName", staffName);
        json.put("staffCategoryTypes", StaffCategoryTypes.getByIndex(staffCategory));
        json.put("timeStartWorking", timeStartWorking);
        json.put("timeEndWorking", timeEndWorking);
        return json;
    }
}
