package system;

public class Active {
    public String username;
    public UserType profile;
    public String user_id;

    public Active (String username, String profile, String user_id){
        this.username = username;
        this.profile = UserType.valueOf(profile);
        this.user_id = user_id;
        
    }

    
}
