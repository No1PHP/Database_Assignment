package controller.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author Zhining
 * @description Request body json object form of operationRecord
 * @create 2020-05-12-15-09
 **/
@Getter
@Setter
public class Operation {
    private Integer operationID;
    private Integer staffID;
    private Byte operationType;
    private String note;
    private Timestamp operationTime = new Timestamp(System.currentTimeMillis());
    private Boolean willSendUpdateMessage;

}
