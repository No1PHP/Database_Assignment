package controller.contImp;

import com.alibaba.fastjson.JSONObject;
import common.HttpServletRequestUtils;
import controller.model.Operation;
import dao.tables.OperationRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static constants.globalConstants.LOGIN_STATUS;
import static constants.globalConstants.SERVICE;

/**
 * @author Zhining
 * @description
 * @create 2020-05-12-15-01
 **/
@CrossOrigin(allowCredentials = "true")
@RequestMapping(value = "/operate")
@Controller
public class OperationController{
    /**
     * @param request
     * @return Map
     * @author Zhining
     * @description 处理增删改职工排期信息的请求
     * {operationType:'',note:'',
     * body:''
     * }
     * @create 2020/5/2 11:25 下午
     **/
    @RequestMapping(value = "/do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doOperation(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (LOGIN_STATUS) {
            try {
                Operation oper = HttpServletRequestUtils.getModel(request, "operationRequestString", Operation.class);
                JSONObject body = oper.getBody();
                switch (oper.getOperationType()) {
                    //body: '{operationID:''}'
                    case PULL: {
                        SERVICE.ensureMaterialOrder(body.getInteger("operationID"), oper.getNote());
                        break;
                    }
                    //body:'{materialName:'', amount:''}'
                    case ORDER: {
                        SERVICE.orderMaterial(oper.getNote(), body.getString("materialName"), body.getFloat("amount"));
                        break;
                    }
                    //body:'{staffID:'', startTime:'', endTime:''}'
                    case DAY_SHIFT: {
                        SERVICE.scheduleStaff(Timestamp.valueOf(body.getString("startTime")), Timestamp.valueOf(body.getString("endTime")), body.getInteger("staffID"), oper.getNote());
                        break;
                    }
                    //body:'{stallName:'', newLocation:''}'
                    case STALL_CHANGE: {
                        SERVICE.saveStallLocation(body.getString("stallName"), body.getInteger("newLocation"));
                        break;
                    }
                    default: throw new Exception("wrong operation type code!");
                }
                map.put("succeed", true);
            } catch (Exception e) {
                map.put("succeed", false);
                map.put("message: ", e.getMessage());
            }
        } else {
            map.put("succeed", false);
            map.put("message", "please login first!");
        }
        return map;
    }

    /**
     * @param staffId
     * @return  Map
     * @author Zhining
     * @description
     * @create 2020/5/10 9:59 下午
     **/
    @RequestMapping(value = "/OperationList/{staffId}",method = RequestMethod.GET)
    @ResponseBody
    public List<String> getOperationList(@PathVariable(value = "staffId") String staffId) {
        List<String> list = new LinkedList<>();
        if (LOGIN_STATUS) {
            List<OperationRecord> result = SERVICE.getOperationRecord(Integer.parseInt(staffId));
            for (OperationRecord e : result) {
                list.add(e.toString());
            }
        } else {
            list.add(null);
        }
        return list;
    }
}
