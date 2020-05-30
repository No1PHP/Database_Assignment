package controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @description staff information class for controller
 * @create 2020-05-02-23-27
 **/
@Getter
@Setter
public class Stall {
    private String stallName;

    private Integer stallLocation;

    private Float stallRent;

    private Float costLastMonth;

    private String operationName;

    private String[] recipes;
//{stallName:'',
//     *     * stallLocation:'',stallRent:'Float',
//     *     * costLastMonth:'Float',
//     *     * operationName:''}
    public Stall(String para) {
        JSONObject json = JSONObject.parseObject(para);
        stallName = json.getString("stallName");
        stallLocation = json.getInteger("stallLocation");
        stallRent = json.getFloat("stallRent");
        costLastMonth = json.getFloat("costLastMonth");
        operationName = json.getString("operationName");
        try {
            JSONArray array = json.getJSONArray("recipes");
            recipes = new String[array.size()];
            recipes = array.toArray(recipes);
        } catch (Exception e) {
            recipes = new String[0];
        }
    }
}
