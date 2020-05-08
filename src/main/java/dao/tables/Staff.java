package dao.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Staff")
public class Staff implements Serializable {
    private static final long serialVersionUID = 8L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
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
    //account foreign key
    @OneToMany(targetEntity = Account.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "staffId", referencedColumnName = "staff_id")
    private Set<Account> accounts = new HashSet<>();

    //schedule record foreign key
    @OneToMany(targetEntity = ScheduleRecord.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
    private Set<ScheduleRecord> scheduleRecords = new HashSet<>();

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

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<ScheduleRecord> getScheduleRecords() {
        return scheduleRecords;
    }

    public void setScheduleRecords(Set<ScheduleRecord> scheduleRecords) {
        this.scheduleRecords = scheduleRecords;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffID=" + staffID +
                ", staffName='" + staffName + '\'' +
                ", staffCategory=" + staffCategory +
                ", timeStartWorking=" + timeStartWorking +
                ", timeEndWorking=" + timeEndWorking +
                '}';
    }
}
