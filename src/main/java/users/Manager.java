package users;

import enums.ManagerType;

public class Manager extends Employee {
    private ManagerType managerType;

    public Manager(String id, String email, String login, String name, String surname,
                   String employeeId, double salary, String department, String managerType) {
        super(id, login, email,  name, surname, "MANAGER", employeeId, salary, department);
        this.managerType = ManagerType.valueOf(managerType);
    }

    public ManagerType getManagerType() {
        return managerType;
    }
}
