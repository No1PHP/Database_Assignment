package controller.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhining
 * @description password refactor
 * @create 2020-05-01-01-41
 **/
@Getter
@Setter
public class PasswordChange {

    private String accountName;
    private String passwordValue;
    private String newPassword;

    //{account:'',currentPassword;'',newPassword:''}
    public PasswordChange(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        accountName = jsonObject.getString("account");
        passwordValue = jsonObject.getString("currentPassword");
        newPassword = jsonObject.getString("newPassword");
    }
}
