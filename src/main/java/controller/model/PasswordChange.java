package controller.model;

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


}
