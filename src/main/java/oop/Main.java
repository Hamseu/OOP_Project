package oop;
import database.UDBM;
import java.io.FileInputStream;
import java.util.Properties;
import system.Active;
import system.Authenticate;

public class Main {

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
        System.out.println("Something is wrong");
        return null;
    }
    }
    public static void main(String[] args) {
        UDBM db = new UDBM();
        boolean loginer = Authenticate.consolewin(db);
        while (true) {
            
             if (loginer){
                Active actuser = loadSession();
                System.out.println("Hello, " + actuser.username);
                break; /* TODO, make Operational Manager */
             }
             else{
                loginer = Authenticate.consolewin(db);
             }
        } 
    }
}