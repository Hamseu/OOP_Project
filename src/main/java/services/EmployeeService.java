package services;

import java.sql.SQLException;
import java.util.Vector;

import database.UDBM;
import users.Employee;

public class EmployeeService {
    Vector<Employee> employees;
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

    public Vector<Employee> getAllEmployee(UDBM db){
      try {
        this.employees = db.getAllEmployees();
      }
      catch (SQLException sq ){

      }
      return employees;
    }
}
