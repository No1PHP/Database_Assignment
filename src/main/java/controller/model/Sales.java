package controller.model;

import controller.contImp.controllerProperties.PageTurningFunction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhining
 * @description sales related request controller
 * @create 2020-05-03-02-08
 **/
public class Sales implements PageTurningFunction {




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
