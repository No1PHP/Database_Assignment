package controller.model;

import dao.tables.OperationRecord;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 *
 * @description request corresponding to ScheduleRecord
 * @create 2020-05-13-01-39
 **/
@Getter
@Setter
public class Schedule {

    private Integer operationID;

    private Integer staffID;

    private Timestamp timeScheduledToStartWorking;

    private Timestamp timeScheduledToEndWorking;

    private Stall staff;

    private OperationRecord operationRecord;
}
