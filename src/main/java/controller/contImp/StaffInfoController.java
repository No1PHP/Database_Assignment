package controller.contImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpServletRequestUtils;
import controller.contImp.controllerProperties.PageTurningFunction;
import controller.model.PageInfo;
import controller.model.Staff;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description Set of controller of staff information handling request
 * @create 2020-05-01-01-11
 **/
@RequestMapping(value = "/Staff") //此类url前缀
@Controller
public class StaffInfoController implements PageTurningFunction {


    /**
    * @author Zhining
    * @description 处理增删改职工先关信息的请求
    * @param request
    * @RequestJson
    * {stallID:'',stallName:'',
    * stallLocation:'',stallRent:'[Float]',
    * costLastMonth:'[Float]',manageTimeSoFar:'[Float]',
    * aveMonthlySalesAmount:'[Float]',OperationName:'',
    * operationName:''}
     *
    * @return Map
    * @create 2020/5/2 11:25 下午
    **/
    @RequestMapping(value = "/staff",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> StaffInfoOperation(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String staffRequestString = HttpServletRequestUtils.getString(request, "staffRequestString");
        ObjectMapper mapper = new ObjectMapper();
        Staff staffReq;
        try{
            staffReq = mapper.readValue(staffRequestString, Staff.class);
        }catch (Exception e){
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
            return map;
        }

        // 请求含条件，打包给不同方法
        //TO-DO: 外部接口 空值判断
        if(staffReq.operationName.equals("AddStaff"))
            ; //调用daoImpl，map接受返回参数
        if(staffReq.operationName.equals("ModifyStaff"))
            ;
        if(staffReq.operationName.equals("DeleteStaff"))
            ;

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
