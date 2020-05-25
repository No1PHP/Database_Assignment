package controller.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @description account&password for logging
 * @create 2020-05-01-00-55
 **/
@Getter
@Setter
public class LoginAccount {
    private String accountName;
    private String passwordValue;

    public LoginAccount(String json) {
        JSONObject param = JSONObject.parseObject(json);
        this.accountName = param.getString("account");
        this.passwordValue = param.getString("password");
    }
}
