package controllers;

import java.util.Scanner;
import java.util.Vector;

import database.UDBM;
import services.EmployeeService;
import system.Active;
import system.UserType;
import users.Employee;
import users.User;

public class EmployeeController extends Controller{
    private final EmployeeService ES = EmployeeService.getInstance();

    public EmployeeController(UDBM db){
         this.db = db;
    }

    public void makeRequest(Active user, Scanner scanner){
      Vector <Employee> employee = ES.getAllEmployee(db);
        for (User us : employee){
        if (us.getProfile() != UserType.STUDENT){
        System.out.println(us);
        }
      }
      String receiver_id = RS.readLine("Type id of receiver: ", scanner);
      String message = RS.readLine("Write your request: ", scanner);
      ES.makeRequest(user.user_id, receiver_id, message, db);
}
   public void viewOwnRequests(Active user){
        for (String str : ES.getOwnRequests(db, user.user_id)){
            System.out.println(str);
        }
}

   public void viewReceivedRequests(Active user){
    for (String str : ES.getReceivedRequests(db, user.user_id)){
            System.out.println(str);
        }
}

}
