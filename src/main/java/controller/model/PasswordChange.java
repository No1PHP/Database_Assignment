package controller.model;

/**
 * @author Zhining
 * @description password refactor
 * @create 2020-05-01-01-41
 **/
public class PasswordChange {

    private String accountName;
    private String passwordValue;
    private String newPassword;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPasswordValue() {
        return passwordValue;
    }

    public void setPasswordValue(String passwordValue) {
        this.passwordValue = passwordValue;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
