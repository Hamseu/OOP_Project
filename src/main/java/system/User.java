package system;

public class User {

    protected String username;
    protected String password;
    protected String email;
    protected String user_id;
    protected UserType profile;

    public User(String name, String id, String email, String profile){
        this.username = name;
        this.email = email;
        this.user_id = id;
        this.profile = UserType.valueOf(profile);
    }

    public String getUsername(){
        return this.username;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public String getId(){
        return this.user_id;
    }
    
    public UserType getProfile(){
        return this.profile;
    }
    
}
