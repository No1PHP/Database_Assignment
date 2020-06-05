package controller.contImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpServletRequestUtils;
import controller.model.Staff;
import dao.enums.StaffCategoryTypes;
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
 * @description Set of controller of staff information handling request
 * @create 2020-05-01-01-11
 **/
@CrossOrigin(allowCredentials = "true")
@RequestMapping(value = "/Staff") //此类url前缀
@Controller
public class StaffInfoController{
    /**
    * @author Zhining
    * @description 处理增删改职工先关信息的请求
    * @param request
    * @RequestJson
    * {staffID:'',staffName:'',
    * staffCategoryTypes:'',timeStartWorking:'DATETIME',
    * timeEndWorking:'DATETIME',
    * operationName:''}
    * @return Map
    * @create 2020/5/2 11:25 下午
    **/
    @RequestMapping(value = "/operate",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> StaffInfoOperation(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        if(LOGIN_STATUS) {
            try{
                Staff staffReq = HttpServletRequestUtils.getModel(request, "staffRequestString", Staff.class);
                switch (staffReq.getOperationName()) {
                    case "AddStaff": {
                        dao.tables.Staff staff = SERVICE.insertStaff(staffReq.getStaffName(),staffReq.getStaffCategory(),staffReq.getTimeStartWorking(),staffReq.getTimeEndWorking());
                        SERVICE.saveAccount(staff.getStaffID(), staffReq.getStaffCategory().getPosition(), staffReq.getStaffName(), staffReq.getStaffName());
                        map.put("succeed", true);
                        break;
                    }
                    case "ModifyStaff": {
                        SERVICE.updateStaff(staffReq.getStaffID(),staffReq.getStaffCategory(),staffReq.getTimeStartWorking(),staffReq.getTimeEndWorking());
                        map.put("succeed", true);
                        break;
                    }
                    default: throw new Exception("wrong operation type code!");
                }
            }catch (Exception e){
                map.put("succeed", false);
                map.put("message: ", e.getMessage());
            }
        }else {
            map.put("message","Please login first");
        }
        return map;
    }
}
