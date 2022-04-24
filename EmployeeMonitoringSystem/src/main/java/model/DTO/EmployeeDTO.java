package model.DTO;

public class EmployeeDTO {
    private int id;
    private String username;
    private String loginTime;

    public EmployeeDTO(int id, String username, String loginTime) {
        this.id = id;
        this.username = username;
        this.loginTime = loginTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
