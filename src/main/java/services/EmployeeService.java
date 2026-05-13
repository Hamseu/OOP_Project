package services;

import java.util.Vector;

import database.UDBM;
import system.UserType;
import users.User;

public class EmployeeService {
    public EmployeeService (){

    }

    public static EmployeeService getInstance(){
        return new EmployeeService();
    }

    public void makeRequest(String employee_id_provider, String employee_id_receiver, String request, UDBM db){
         db.addRequest(employee_id_provider, employee_id_receiver,  request);
    }
    public Vector<String> getOwnRequests(UDBM db, String owner_id){
        return db.getOwnRequests(owner_id);
    }

    public Vector<String> getReceivedRequests(UDBM db, String receiver_id){
       return db.getReceivedRequests(receiver_id);
    }

    public Vector<User> getAllEmployee(UDBM db){
      Vector<User> users = db.getUsers();
      Vector<User> employees = new Vector<>();
      for (User us : users){
        if (us.getProfile() != UserType.STUDENT){
        employees.add(us);
        }
      }
      return employees;
    }
}
