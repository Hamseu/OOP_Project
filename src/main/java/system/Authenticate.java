package system;
import java.util.Scanner;

import database.UDBM;
import storage.ManualDataStorage;
import users.User;

public class Authenticate {
    public static boolean checkPasswords(UDBM db, String ide, String password){
        String pass = db.getPasswordByID(ide);
        return pass.equals(password);
    }


   public static boolean consolewin(UDBM db, Scanner sc) {
    
    System.out.println("Welcome! Press x to stop");
   
    User user;
    while (true){ 
    System.out.print("Login: ");
    String login = sc.nextLine();

    user = db.getUserByUsername(login);
    if (user != null){
        break;
    }
    else{
      System.out.println("No such user");
    }
  }
    while (true){
      System.out.print("Password: ");
      String password = sc.nextLine();
      if (password.charAt(0) == 'x')
      {
        sc.close();
        return false;
      }
      if (checkPasswords(db, user.getId(), password)) {
        System.out.println("Login successful!");
        db.addActiveSession(user);
        ManualDataStorage.saveSession(user);
        return true;
      } else {
        System.out.println("Invalid credentials");
      }
}
}
}
