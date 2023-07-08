package htwberlin.demo.web.api;

public class User {
    private String UserName ;
    private int id ;

    private String password ;

    public User(String userName, int id, String password) {
        UserName = userName;
        this.id = id;
        this.password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
