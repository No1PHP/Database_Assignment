package controller.contImp;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpServletRequestUtils;
import controller.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static constants.globalConstants.LOGIN_STATUS;
import static constants.globalConstants.SERVICE;

/**
 * @description Set of controller of recipe info handling related request
 * @operation
 * 包括：增删改菜谱；得到菜谱（分页）；saveStallWithRecipes？？？迷惑；
 * 还有几个关于recipe的service，照猫画虎就行了
 * 注意@RequestMapping不同，要根据需要决定是post还是get，域名自定，例：@RequestMapping(value = "/{currentPage}&{size}",method = RequestMethod.GET)代表GET请求
 * get的方法中参数： 例  @RequestParam int currentPage, @RequestParam  int size，即：直接调用get的路径中的参数就行
 * post的方法中参数： 要先提取request中的数据，然后json转对象（通过ObjectMapper，写法和在下面，网上有文档可以参考），例：HttpServletRequest request
 * @create 2020-05-01-01-12
 **/
@CrossOrigin(allowCredentials = "true")
@Controller
@RequestMapping(value = "/Recipe")
public class RecipeInfoController {
    /**
     * @author Zhining
     * @description 处理增删改菜谱信息的请求
     * @param request
     * @RequestJson
     * {recipeName:'',
     * relevantIngredient:['material1', 'material2',...],price:'Float',
     * operationName:''}
     *
     * @return Map
     * @create 2020/5/2 11:25 下午
     **/
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> RecipeInfoOperation(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            Recipe recipeReq = HttpServletRequestUtils.getModel(request, "recipeRequestString", Recipe.class);
            //增删改服务
            if(LOGIN_STATUS) {
                if (recipeReq.getOperationName().equals("AddRecipe")){
                    SERVICE.saveRecipe(recipeReq.getRecipeName(),recipeReq.getPrice(),recipeReq.getRelevantIngredient());
                } else if (recipeReq.getOperationName().equals("ModifyRecipe")){
                    SERVICE.modifyRecipe(recipeReq.getRecipeName(),recipeReq.getPrice(),recipeReq.getRelevantIngredient());
                } else {
                    throw new Exception("wrong operation type code!");
                }
                map.put("succeed", true);
            }else{
                map.put("succeed", false);
                map.put("message","Please login first");
            }
        }catch (Exception e){
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
        }
        return map;
    }
}
