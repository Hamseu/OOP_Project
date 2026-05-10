package system;
import database.UDBM;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class Authenticate {
    public static boolean checkPasswords(UDBM db, String ide, String password){
        String pass = db.getPasswordByID(ide);
        return pass.equals(password);
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
        System.out.println("local storage isn't available");
    }
}

   public static boolean consolewin(UDBM db) {
    
    Scanner sc = new Scanner(System.in);

    System.out.println("Welcome! Press x to stop");
    System.out.print("Login: ");
    String login = sc.nextLine();

    User user = db.getUserByUsername(login);
    while (true){
      System.out.print("Password: ");
      String password = sc.nextLine();
      if (password.charAt(0) == 'x')
      {
        sc.close();
        return false;
      }
      if (checkPasswords(db, user.user_id, password)) {
        System.out.println("Login successful!");
        sc.close();
        db.addActiveSession(user);
        saveSession(user);
        return true;
      } else {
        System.out.println("Invalid credentials");
      }
}
}
}
