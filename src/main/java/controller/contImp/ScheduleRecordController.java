package controller.contImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpServletRequestUtils;
import controller.contImp.controllerProperties.PageTurningFunction;
import controller.model.Operation;
import controller.model.PageInfo;
import dao.tables.ScheduleRecord;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
public class ScheduleRecordController implements PageTurningFunction {

    /**
     * @param request
     * @return Map
     * @author Zhining
     * @description 处理增删改菜谱信息的请求
     * @RequestJson {operationID:'',staffID:'',
     * operationType:'',note:'',
     * operationTime:'',willSendUpdateMessage:''}
     * @create 2020/5/2 11:25 下午
     **/
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> scheduleInfoOperation(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String operationRequestString = HttpServletRequestUtils.getString(request, "operationRequestString");
        ObjectMapper mapper = new ObjectMapper();
        Operation operationReq;
        try {
            operationReq = mapper.readValue(operationRequestString, Operation.class);
        } catch (Exception e) {
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
            return map;
        }

        if (LOGIN_STATUS) {
            // 请求含条件，打包给不同service
            //TO-DO: 最好写个外部接口 空值判断
            if (operationReq.getOperationType()==0) {
                //pull
            }
            if (operationReq.getOperationType()==1) {
                //order
            }
            if (operationReq.getOperationType()==2) {
                //day shift
            }
            if (operationReq.getOperationType()==3) {
                //stall change
            }
            if(operationReq.getOperationType()==10){

            }
            if(operationReq.getOperationType()==20){

            }
            if(operationReq.getOperationType()==30){

            }
        } else {
            map.put("message", "please login first!");
        }
        //return
        return map;

    }


    /**
     * @param start
     * @return  Map
     * @author Zhining
     * @description
     * @create 2020/5/10 9:59 下午
     **/
    @RequestMapping(value = "/OperationList/{start}",method = RequestMethod.GET)
    @ResponseBody
    public List<ScheduleRecord> getOperationList(@PathVariable(value = "start") int start) {
        List<ScheduleRecord> list = new LinkedList<ScheduleRecord>();
        if (LOGIN_STATUS) {
           SERVICE.getOperationRecord(start);
        } else {
            list.add(null);
        }

        return list;


    }


    /**
     * @param currentPage,size,
     * @return String
     * @author Zhining
     * @description Get Method  /staff?cur=1&size=10
     * @RequestJson {currentPageNo:'',size:''} 页数；每页显示数量
     * @create 2020/5/2 9:44 下午
     **/
    @RequestMapping(value = "/{currentPage}&{size}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pageTurning(@RequestParam(value = "pageNo", defaultValue = "1") int currentPage, @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            PageInfo pageInfo = new PageInfo(currentPage, size);
        } catch (Exception e) {
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
            return map;
        }

        //打包


        return map;
    }
}
