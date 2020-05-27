package controller.model;

import com.alibaba.fastjson.JSONObject;
import dao.enums.OperationType;
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
    private OperationType operationType;
    private String note;
    private JSONObject body;

    public Operation (String para) {
        JSONObject json = JSONObject.parseObject(para);
        operationType = OperationType.getByIndex(json.getInteger("operationType"));
        note = json.getString("note");
        body = json.getJSONObject("body");
    }
}
