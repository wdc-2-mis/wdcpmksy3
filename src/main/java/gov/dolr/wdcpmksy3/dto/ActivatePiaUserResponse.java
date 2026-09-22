package gov.dolr.wdcpmksy3.dto;

public class ActivatePiaUserResponse {

    private String status;
    private String message;
    private String userName;
    private String userId;
    private String password;

    private String emailStatus;
    private String emailMessage;

    public ActivatePiaUserResponse() {
    }

    public ActivatePiaUserResponse(
            String status,
            String message,
            String userName,
            String userId,
            String password) {

        this.status = status;
        this.message = message;
        this.userName = userName;
        this.userId = userId;
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }
}