package controller.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Transaction {
    private Integer transactionID;
    private String stallName;
    private String recipeName;
    private Timestamp transactionTime;
    private Integer numbers = 1;
    private Float transactionPrice;
    private String operation;

    public Transaction(String para) {
        JSONObject json = JSONObject.parseObject(para);
        transactionID = json.getInteger("transactionID");
        stallName = json.getString("stallName");
        recipeName = json.getString("recipeName");
        transactionTime = json.getTimestamp("transactionTime");
        numbers = json.getInteger("numbers");
        transactionPrice = json.getFloat("transactionPrice");
        operation = json.getString("operation");
    }
}
