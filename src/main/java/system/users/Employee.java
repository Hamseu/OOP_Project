package system.users;

import system.User;
import java.util.Date;

public abstract class Employee extends User {
    public Date hireDate;
    public double salary;

    public Employee(String name, String id, String email, String profile,
                    Date hireDate, double salary) {
        super(name, id, email, profile);
        this.hireDate = hireDate;
        this.salary = salary;
    }

    // то есть каждый подкласс реализует посвоему
    public abstract void makeRequest(String request);

    public void draftReport() {
        System.out.println("[Report] " + username + " drafted a report on " + new Date());
    }

    public Date getHireDate(){ return hireDate; }
    public double getSalary(){ return salary; }
    public void setSalary(double s){ this.salary = s; }
}