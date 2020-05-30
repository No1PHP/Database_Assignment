package controller.contImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpServletRequestUtils;
import controller.model.Stall;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static constants.globalConstants.LOGIN_STATUS;
import static constants.globalConstants.SERVICE;

/**
 * @description
 * @create 2020-05-12-21-34
 **/
@CrossOrigin(allowCredentials = "true")
@RequestMapping(value = "/Stall") //此类url前缀
@Controller
public class StallInfoController {
    /**
    * @author Zhining
    * @description
    * @param
    * @return
     * @RequestJson {stallID:'',stallName:'',
     *     * stallLocation:'',stallRent:'[Float]',
     *     * costLastMonth:'[Float]',manageTimeSoFar:'[Float]',
     *     * aveMonthlySalesAmount:'[Float]',OperationName:'',
     *     * operationName:''}
     *
     * @create 2020/5/13 3:19
    **/
    @RequestMapping(value = "/saveStallWithRecipes",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> StallInfoOperation(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String staffRequestString = HttpServletRequestUtils.getString(request, "staffRequestString");
        ObjectMapper mapper = new ObjectMapper();
        Stall stallReq;
        try{
            stallReq = mapper.readValue(staffRequestString, Stall.class);
        }catch (Exception e){
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
            return map;
        }

        // 请求含条件，打包给不同方法
        //TO-DO: 外部接口 空值判断
        if(LOGIN_STATUS) {
            if (stallReq.getOperationName().equals("AddStall")) {
            }
            if (stallReq.getOperationName().equals("ModifyStall")){
            }
            if (stallReq.getOperationName().equals("DeleteStall")){
            }
        }else {
            map.put("message","Please login first");
        }
        return map;
    }

    /**
     * @author Zhining
     * @description
     * @param
     * @return
     * 问题：这个service是个啥意思？stallLocation还是int？
     * 暂时把请求数据格式定为下面这样了
     * {stallName:'',stallLocation:'',stallRent:'',recipes:['','',......]}
     * @create 2020/5/24 11:14 下午
     **/
    @RequestMapping(value = "/saveStallWithRecipes",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveStallWithRecipes(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();

        String saveStallWithRecipesString = HttpServletRequestUtils.getString(request, "saveStallWithRecipesString");
        ObjectMapper mapper = new ObjectMapper();
        //建一个新model，model里有什么数参考上面注释里写的数据格式
        try{
            //用objectmapper，json转model对象
        }catch (Exception e){
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
            return map;
        }
        if(LOGIN_STATUS) {
//            SERVICE;
        }else{
            map.put("message","Please login first");
        }
        //return
        //上面的@response注释说明默认http响应就是这里return的东西，即：response的数据由service的返回值决定
        return map;
    }
}
