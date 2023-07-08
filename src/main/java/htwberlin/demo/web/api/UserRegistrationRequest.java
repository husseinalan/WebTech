package htwberlin.demo.web.api;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserRegistrationRequest {
    private String username;
    private String password;

    public UserRegistrationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @JsonCreator
    public UserRegistrationRequest() {
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

}


