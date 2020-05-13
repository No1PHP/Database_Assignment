package controller.contImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpServletRequestUtils;
import controller.contImp.controllerProperties.PageTurningFunction;
import controller.model.PageInfo;
import controller.model.Recipe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static constants.globalConstants.LOGIN_STATUS;
import static constants.globalConstants.SERVICE;

/**
 * @description Set of controller of recipe info handling related request
 * @operation
 * @create 2020-05-01-01-12
 **/
@RequestMapping(value = "/Recipe")
@Controller
public class RecipeInfoController implements PageTurningFunction {


    /**
     * @author Zhining
     * @description 处理增删改菜谱信息的请求
     * @param request
     * @RequestJson
     * {recipeID:'',recipeName:'',
     * relevantIngredient:'',price:'[Float]',
     * operationName:''}
     *
     * @return Map
     * @create 2020/5/2 11:25 下午
     **/
    @RequestMapping(value = "/",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> RecipeInfoOperation(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String recipeRequestString = HttpServletRequestUtils.getString(request, "recipeRequestString");
        ObjectMapper mapper = new ObjectMapper();
        Recipe recipeReq;
        try{
            recipeReq = mapper.readValue(recipeRequestString, Recipe.class);
        }catch (Exception e){
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
            return map;
        }

        // 请求含条件，打包给不同方法
        //TO-DO: 最好写个外部接口 空值判断
        if(LOGIN_STATUS) {
            if (recipeReq.getOperationName().equals("addRecipe")){
                SERVICE.saveRecipe(recipeReq.getRecipeID(),recipeReq.getRecipeName(),recipeReq.getRelevantIngredient(),recipeReq.getPrice());
            }
            if (recipeReq.getOperationName().equals("ModifyRecipe")){
                SERVICE.saveRecipe(recipeReq.getRecipeID(),recipeReq.getRecipeName(),recipeReq.getRelevantIngredient(),recipeReq.getPrice());
            }
            if (recipeReq.operationName.equals("DeleteRecipe")){
                SERVICE.removeRecipe(recipeReq.getRecipeID(),recipeReq.getRecipeName(),recipeReq.getRelevantIngredient(),recipeReq.getPrice());
            }
        }else{
            map.put("message","Please login first");
        }
        //return
        return map;

    }



    /**
     * @author Zhining
     * @description Get Method  /staff?cur=1&size=10
     * @param currentPage,size,
     * @RequestJson  {currentPageNo:'',size:''} 页数；每页显示数量
     * @return String
     * @create 2020/5/2 9:44 下午
     **/
    @RequestMapping(value = "/{currentPage}&{size}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pageTurning(@RequestParam int currentPage, @RequestParam  int size){
        Map<String, Object> map = new HashMap<String, Object>();

        try{
            PageInfo pageInfo = new PageInfo(currentPage,size);
        }catch (Exception e){
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
            return map;
        }

        //打包


        return map;
    }

}
