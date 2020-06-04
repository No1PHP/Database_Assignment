package controller.model;

import com.alibaba.fastjson.JSONObject;
import dao.enums.StaffCategoryTypes;
import dao.tables.Account;
import dao.tables.OperationRecord;
import dao.tables.ScheduleRecord;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @create 2020-05-13-03-07
 **/
@Getter
@Setter
public class Staff {
    private Integer staffID;
    private String staffName;
    private StaffCategoryTypes staffCategory;
    private Time timeStartWorking;
    private Time timeEndWorking;
    private String operationName;
    //{staffID:'',staffName:'',
    //    * staffCategoryTypes:'',timeStartWorking:'[DATETIME]',
    //    * timeEndWorking:'[DATETIME]',
    //    * operationName:''}
    public Staff(String para) {
        JSONObject json = JSONObject.parseObject(para);
        this.staffID = json.getInteger("staffID");
        this.staffName = json.getString("staffName");
        this.staffCategory = StaffCategoryTypes.valueOf(json.getString("staffCategoryTypes"));
        this.timeStartWorking = Time.valueOf(json.getString("timeStartWorking"));
        this.timeEndWorking = Time.valueOf(json.getString("timeEndWorking"));
        this.operationName = json.getString("operationName");
    }
}
