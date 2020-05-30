package controller.contImp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpServletRequestUtils;
import controller.model.LoginAccount;
import controller.model.PasswordChange;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.*;
import service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import static constants.globalConstants.LOGIN_STATUS;
import static constants.globalConstants.SERVICE;

/**
 * @description Login Operation Management
 * 包括：登录，登出，修改密码
 * @note !!!post 请求直接url访问会405，
 * 要配合表单提交或其他action---如：index.html中<form action ="/loginPage",method="post"></form>
 * @create 2020-04-29-18-17
 **/
@CrossOrigin(allowCredentials = "true")
@Controller
@RequestMapping(value = "/account")
public class AccountManageController {
    /**
    * @author Zhining
    * @description login account
    * @requestJson format {account:'',password;''}
    * @param request
    * @return map
    * @create 2020/5/1 1:17 上午
    **/
    @RequestMapping(value = "/loginPage",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request){
        Map<String, Object> reply = new HashMap<String, Object>();
        try{
            //1 receive and parse the request parameter
            LoginAccount log = HttpServletRequestUtils.getModel(request, "accountString", LoginAccount.class);
            //2 打包给service
            SERVICE = Service.connect(log.getAccountName(),log.getPasswordValue());
            LOGIN_STATUS = (SERVICE != null);

            String statusMessage = (LOGIN_STATUS)?"login success":"login failed";
            reply.put("status",statusMessage);
            //前端检查response.data.statusMessage是否为success
        } catch (Exception e) {
            reply.put("convert succeed", false);
            reply.put("error message: ", e.getMessage());
        }
        return reply;
    }

    /**
     * @author Zhining
     * @description change password
     * @requestJson format {account:'',currentPassword;'',newPassword:''}
     * newPassword输入两次判断是否一致，在前端实现
     * @param request
     * @return map
     * @create 2020/5/1 1:25 上午
     **/
    @RequestMapping(value = "/passwordChanging",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> passwordChanging(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            PasswordChange changeInfo = HttpServletRequestUtils.getModel(request, "passwordChangeString", PasswordChange.class);
            Service service = (Service.connect(changeInfo.getAccountName(),changeInfo.getPasswordValue()));
            if(service != null) {
                service.changePassword(changeInfo.getNewPassword());
                map.put("message","password successfully changed");
            }else{
                map.put("message","password doesn't match username, try again");
            }
        }catch (JsonProcessingException e){
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
        }
        return map;
    }


    /**
    * @author Zhining
    * @description 登出，清空service与登录状态
    * @param logStage
    * @return Map
    * @create 2020/5/13 1:35 上午
    **/
    @RequestMapping(value = "/LoginPage/{logStage}",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> logout(@PathVariable("logStage") String logStage) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(logStage.equals("logout")){
            LOGIN_STATUS = false;
            SERVICE = null;
        }
        map.put("logout:",(!LOGIN_STATUS)?"succeed":"failed, try again");
        return map;
    }



}
