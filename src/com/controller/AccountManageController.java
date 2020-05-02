import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpServletRequestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description Login Operation Management
 * @note !!!post 请求直接url访问会405，
 * 要配合表单提交或其他action---如：index.html中<form action ="/loginPage",method="post"></form>
 * @create 2020-04-29-18-17
 **/
@Controller
@RequestMapping(value = "/")//访问路径 未定
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
    private Map<String,Object> login(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        //1 receive and parse the request parameter
        String accountInfoString = HttpServletRequestUtils.getString(request, "accountString");
        ObjectMapper mapper = new ObjectMapper();
        LoginAccount log = null;
        try{
            log = mapper.readValue(accountInfoString, LoginAccount.class);
        }catch (Exception e){
            map.put("convert succeed", false);
            map.put("error message: ", e.getMessage());
            return map;
        }

        //2 打包给service


        //3 接受service返回的对象，return
        return map;
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
    @RequestMapping(value = "/loginPage",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> passwordChanging(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String accountInfoString = HttpServletRequestUtils.getString(request, "passwordChangeString");
        ObjectMapper mapper = new ObjectMapper();
        LoginAccount log = null;
        try{
            log = mapper.readValue(accountInfoString, LoginAccount.class);
        }catch (Exception e){
            map.put("succeed", false);
            map.put("message: ", e.getMessage());
            return map;
        }

        // 打包


        //return
        return map;
    }





}
