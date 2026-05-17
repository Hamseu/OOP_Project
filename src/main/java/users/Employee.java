package users;

import enums.DepartmentType;

public  class Employee extends User {
    private String employeeId;
    private double salary;
    private DepartmentType department;

    public Employee(String id, String email, String login, String name, String surname,
                    String role, String employeeId, double salary, String department) {
        super(id, email, login, name, surname, role);
        this.employeeId = employeeId;
        this.salary = salary;
        this.department = DepartmentType.valueOf(department);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public DepartmentType getDepartment() {
        return department;
    }
    @Override
    public String toString(){
        return super.toString() + " Salary: " + getSalary() + " Department: " + getDepartment();
    }
}
