package controller.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialAllocate {
    private String materialName;
    private String stallName;
    private Float amount;

    public MaterialAllocate(String para) {
        JSONObject json = JSONObject.parseObject(para);
        materialName = json.getString("materialName");
        stallName = json.getString("stallName");
        amount = json.getFloat("amount");
    }
}
