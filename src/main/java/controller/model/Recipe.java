package controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * @description
 * @create 2020-05-03-02-04
 **/
@Getter
@Setter
public class Recipe {

     public String recipeName;
     public String[] relevantIngredient;
     public float price;
     public String operationName;

     //{recipeName:'', relevantIngredient:['material1', 'material2',...],price:'Float', operationName:''}
     public Recipe(String json) {
          JSONObject jsonObject = JSONObject.parseObject(json);
          recipeName = jsonObject.getString("recipeName");
          price = jsonObject.getFloat("price");
          operationName = jsonObject.getString("operationName");
          relevantIngredient = jsonObject.getString("relevantIngredient").split(",");
     }
}
