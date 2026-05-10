package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import system.User;
import system.UserType;

public class UDBM {
    Connection conn;
        public UDBM(){ 
            try {
            this.conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/university",
                "postgres",
                "Horse"
            );
            System.out.println("Connected!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection failed");
        }
    }

    public Vector<User> getUsers(){
         Vector <User> users = new Vector<User>();
         String sql = """
        SELECT * FROM users;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()){
    
            while (rs.next()){
                String username = rs.getString("username");
                String id = rs.getString("user_id");
                String email = rs.getString("email");
                String profile = rs.getString("profile_type");
                users.add(new User(username, id, email, profile));
            }
    }
    catch (SQLException s ){
       System.out.println("Unexpected Error");
    }    
    return users;
} 

   public User getUserByID(String ide){
         String sql = """
        SELECT * FROM users WHERE user_id = ?;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, ide);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String username = rs.getString("username");
                String id = rs.getString("user_id");
                String email = rs.getString("email");
                String type = rs.getString("profile_type");

                return new User(username, id, email, type);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("No such user");
    }
    return null;
}

public User getUserByUsername(String usernamer){
         String sql = """
        SELECT * FROM users WHERE username = ?;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, usernamer);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String username = rs.getString("username");
                String id = rs.getString("user_id");
                String email = rs.getString("email");
                String type = rs.getString("profile_type");

                return new User(username, id, email, type);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("No such user");
    }
    return null;
}

 public String getPasswordByID(String ide){
    String pass;
    String sql = """
        SELECT * FROM passwords WHERE user_id = ?;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, ide);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                pass = rs.getString("pass");
                return pass;    
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("No such password or Connection Loss");
    }
    return null;
 }
 
 public void addActiveSession(User user){
    String sql = """
        INSERT INTO active_sessions (user_id, username, profile_type)
        VALUES (?, ?, ?)
        ON CONFLICT (user_id)
        DO UPDATE SET 
            username = EXCLUDED.username,
            profile_type = EXCLUDED.profile_type
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, user.getId());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getProfile().name());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("User already logged in");
    }
 }

 public void closeCon(){
    try {
      this.conn.close();
    }
    catch (SQLException s){
        System.out.println("No current connection");
    }
 }
}
