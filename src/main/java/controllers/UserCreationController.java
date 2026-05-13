package controllers;


import database.UDBM;
import users.Manager;
import users.Student;
import users.Teacher;
import users.User;

public class UserCreationController {
     private UDBM db;

    public UserCreationController(UDBM userService) {
        this.db = userService;
    }

    public void addUser(User user, String password) {
        db.addUser(user, password);
    }

    public void addTeacher(Teacher t, String password){
        db.addTeacher(t, password);
    }
        public void addResearcher(int hIndex, String id){
        db.addResearcher(id, hIndex);
    }
        public void addManager(Manager m, String password){
        db.addManager(m, password);
    }
    public void addStudent(Student a, String password){
        if (a.getYearOfStudy() == 4){
            db.assignSupervisor(a.getStudentId(), db.getAllResearchers2().elementAt(0).getId());
        }
        db.addStudent(a, password);
    }
}
