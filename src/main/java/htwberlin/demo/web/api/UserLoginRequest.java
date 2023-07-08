package htwberlin.demo.web.api;

public class UserLoginRequest {
    private String username;
    private String password;

    public UserLoginRequest() {
        // Standardkonstruktor
    }

    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter und Setter

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
}
