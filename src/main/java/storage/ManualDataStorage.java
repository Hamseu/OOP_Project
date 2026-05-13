package storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import system.Active;
import users.User;

public class ManualDataStorage {
    public static Active loadSession(){
        Properties props = new Properties();

    try (FileInputStream in = new FileInputStream("session.properties")) {
        props.load(in);

        
        Active actor = new Active(
                props.getProperty("username"),
                props.getProperty("profile_type"),
                props.getProperty("user_id"));
        in.close();
        return actor;
        

    } catch (Exception e) {
        System.out.println("Local storage is most probably not existing in your reality");
        return null;
    }
    }

    public static void saveSession(User user) {
    Properties props = new Properties();

    props.setProperty("user_id", user.getId());
    props.setProperty("username", user.getUsername());
    props.setProperty("profile_type", user.getProfile().name());

    try (FileOutputStream out = new FileOutputStream("session.properties")) {
        props.store(out, "Active Session");
        out.close();
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("No Admin rights or missing properties, huh?");
    }
}
}
