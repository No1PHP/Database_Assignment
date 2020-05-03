package dao.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "Staff")
public class Staff implements Serializable {
    private static final long serialVersionUID = 8L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", updatable = false)
    private Integer staffID;

    @Column(name = "staff_name", length = 30, nullable = false)
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
    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Byte getStaffCategory() {
        return staffCategory;
    }

    public void setStaffCategory(Byte staffCategory) {
        this.staffCategory = staffCategory;
    }

    public Time getTimeStartWorking() {
        return timeStartWorking;
    }

    public void setTimeStartWorking(Time timeStartWorking) {
        this.timeStartWorking = timeStartWorking;
    }

    public Time getTimeEndWorking() {
        return timeEndWorking;
    }

    public void setTimeEndWorking(Time timeEndWorking) {
        this.timeEndWorking = timeEndWorking;
    }
}
