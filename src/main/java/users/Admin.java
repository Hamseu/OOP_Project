package users;

public class Admin extends Employee {
    public Admin(String id, String email, String login, String name, String surname,
                 String employeeId, double salary, String department) {
        super(id, email, login, name, surname, "ADMIN", employeeId, salary, department);
    }
}
