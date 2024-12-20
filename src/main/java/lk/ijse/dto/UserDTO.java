package lk.ijse.dto;


public class UserDTO {

    private String username;
    private String password;
    private String email;
    private String JobRole;

    public UserDTO() {}

    public UserDTO(String username, String password, String email, String jobRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        JobRole = jobRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobRole() {
        return JobRole;
    }

    public void setJobRole(String jobRole) {
        JobRole = jobRole;
    }
}
