package controller.model;

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

    private Account account;

    private Set<ScheduleRecord> scheduleRecords = new HashSet<>();

    private Set<OperationRecord> operationRecords = new HashSet<>();

    private String operationName;
}
