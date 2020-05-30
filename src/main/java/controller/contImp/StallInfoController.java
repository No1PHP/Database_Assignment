package controller.contImp;

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
     * @RequestJson {stallName:'',
     *     * stallLocation:'',stallRent:'Float',
     *     * costLastMonth:'Float',
     *     * operationName:'',recipes:['','',......]}
     *
     * @create 2020/5/13 3:19
    **/
    @RequestMapping(value = "/oper",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> StallInfoOperation(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        if(LOGIN_STATUS) {
            try{
                Stall stallReq = HttpServletRequestUtils.getModel(request, "staffRequestString", Stall.class);
                switch (stallReq.getOperationName()) {
                    case "AddStall":
                        SERVICE.saveStall(stallReq.getStallName(), stallReq.getStallLocation(), stallReq.getStallRent());
                        break;
                    case "ModifyStall":
                        SERVICE.saveStall(stallReq.getStallName(), stallReq.getStallLocation(), stallReq.getStallRent(), stallReq.getCostLastMonth());
                        break;
                    case "DeleteStall":
                        SERVICE.removeStall(stallReq.getStallName());
                        break;
                    case "AddRecipeForStall":
                        SERVICE.saveStallWithRecipes(stallReq.getStallName(), stallReq.getStallLocation(), stallReq.getStallRent(), stallReq.getRecipes());
                        break;
                }
            }catch (Exception e){
                map.put("succeed", false);
                map.put("message: ", e.getMessage());
                return map;
            }
        }else {
            map.put("message","Please login first");
        }
        return map;
    }
}
