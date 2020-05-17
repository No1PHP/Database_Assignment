package controller.contImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpServletRequestUtils;
import controller.model.Stall;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static constants.globalConstants.LOGIN_STATUS;

/**
 * @description
 * @create 2020-05-12-21-34
 **/
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
        //return
        return map;

    }
}
