package controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Material {
    private String name;
    private String type;
    private Float unitPrice;
    private Float availablePeriod;
    private String operation;
    public Material(String para) {
        JSONObject json = JSONObject.parseObject(para);
        name = json.getString("materialName");
        type = json.getString("materialType");
        unitPrice = json.getFloat("unitPrice");
        availablePeriod = json.getFloat("availablePeriod");
        operation = json.getString("operationName");
    }
}
