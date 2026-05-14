package controllers;

import java.util.Scanner;
import java.util.Vector;

import academic.Course;
import academic.Lesson;
import database.UDBM;
import enums.LessonType;
import exceptions.UserNotFoundException;
import services.CourseService;
import services.ResearchService;
import services.UserService;
import users.Admin;
import users.FullTimeResearcher;
import users.Manager;
import users.Student;
import users.Teacher;
import users.User;

public class AdminController extends Controller{
    private final UserService US = UserService.getInstance();
    private final UserCreationController ucc = UserCreationController.getInstance(db);
    private final CourseService CS = new CourseService();
    private final ResearchService reS = new ResearchService();

    public AdminController(UDBM db) {
        this.db = db;
    }

    public void addUser(User user, String password) {
        db.addUser(user, password);
    }

    public void removeUser(String id) throws UserNotFoundException {
        if(db.removeUser(id)){
            System.out.println("User removed");
        }
        else {
            System.out.println("Failed to remove");
        }
    }

    public void printAllUsers() {
        System.out.println("All users:");
        for (User user : US.getUsers()) {
            System.out.println(user.getId() + " | " + user);
        }
    }


    public void createManager(Scanner scanner) {
        String id = RS.readLine("User id: ", scanner);
        String login = RS.readLine("Login: ", scanner);
        String email = RS.readLine("Email: ", scanner);
        String password = RS.readLine("Password: ", scanner);
        String name = RS.readLine("Name: ", scanner);
        String surname = RS.readLine("Surname: ", scanner);
        String employeeId = RS.readLine("Employee id: ", scanner);
        double salary = RS.readDouble("Salary: ", scanner);

        String department = RS.readLine("Department", scanner);
        String managerType = RS.readLine("ManagerJob", scanner);

        Manager manager = new Manager(id,email,  login, name, surname,
                employeeId, salary, department, managerType);

        ucc.addManager(manager, password);
        System.out.println("Manager created: " + manager);
    }

    public void createAdmin(Scanner scanner) {
        String id = RS.readLine("User id: ", scanner);
        String login = RS.readLine("Login: ", scanner);
        String email = RS.readLine("Email: ", scanner);
        String password = RS.readLine("Password: ", scanner);
        String name = RS.readLine("Name: ", scanner);
        String surname = RS.readLine("Surname: ", scanner);
        String employeeId = RS.readLine("Employee id: ", scanner);
        double salary = RS.readDouble("Salary: ", scanner);
        String department = RS.readLine("Department", scanner);

        Admin admin = new Admin(id, email, login, name, surname,
                employeeId, salary, department);

        ucc.addUser(admin, password);
        System.out.println("Admin created: " + admin);
    }

    public void createFullTimeResearcher(Scanner scanner) {
        String id = RS.readLine("User id: ", scanner);
        String login = RS.readLine("Login: ", scanner);
        String email = RS.readLine("Email: ", scanner);
        String password = RS.readLine("Password: ", scanner);
        String name = RS.readLine("Name: ", scanner);
        String surname = RS.readLine("Surname: ", scanner);
        String employeeId = RS.readLine("Employee id: ", scanner);
        double salary = RS.readDouble("Salary: ", scanner);
        String department = RS.readLine("Department", scanner);
        int hIndex = RS.readInt("H-index: ", scanner);

        FullTimeResearcher researcher = new FullTimeResearcher(id, email,  login,
                name, surname, employeeId, salary, department, hIndex);

        ucc.addUser(researcher, password);
        ucc.addResearcher(hIndex, id);

        System.out.println("Full-time researcher created: " + researcher);
    }

    public void createStudent(Scanner scanner) {
        String id = RS.readLine("User id: ", scanner);
        String login = RS.readLine("Login: ", scanner);
        String email = RS.readLine("Email: ", scanner);
        String password = RS.readLine("Password: ", scanner);
        String name = RS.readLine("Name: ", scanner);
        String surname = RS.readLine("Surname: ", scanner);
        String studentId = RS.readLine("Student id: ", scanner);
        int year = RS.readInt("Year of study: ", scanner);

        Student student = new Student(id, email, login,  name, surname, studentId, year);
        ucc.addStudent(student, password);

        System.out.println("Student created: " + student);
    }

    public void createTeacher(Scanner scanner) {
        String id = RS.readLine("User id: ", scanner);
        String login = RS.readLine("Login: ", scanner);
        String email = RS.readLine("Email: ", scanner);
        String password = RS.readLine("Password: ", scanner);
        String name = RS.readLine("Name: ", scanner);
        String surname = RS.readLine("Surname: ", scanner);
        String employeeId = RS.readLine("Employee id: ", scanner);
        double salary = RS.readDouble("Salary: ", scanner);

        String department = RS.readLine("Department: ", scanner);
        String rank = RS.readLine("Rank: ", scanner);
        int hIndex = RS.readInt("H-index: ", scanner);

        Teacher teacher = new Teacher(id, email, login, name, surname,
                employeeId, salary, department, rank, hIndex);

        ucc.addTeacher(teacher, password);
        if (teacher.is_researcher){
        ucc.addResearcher(hIndex, id);
        }
        System.out.println("Teacher created: " + teacher);
    }

    public void assignTeacherToCourse(Scanner scanner) throws Exception {
        Course course = FS.findCourseByCode(scanner);
        Teacher teacher = FS.findTeacherById(scanner);

        CS.assignTeacher(course, teacher);
        System.out.println("Teacher assigned to course.");
    }
    public void registerStudentToCourse(Scanner scanner) throws Exception {
        Student student = FS.findStudentById(scanner);
        Course course = FS.findCourseByCode(scanner);

        CS.registerStudent(student, course);
        System.out.println("Student registered to course.");
    }

    public void createLesson(Scanner scanner) throws Exception {
        int lessonId = RS.readInt("Lesson id(int): ", scanner);
        String lesson_type = RS.readLine("PRACTICE or LECTURE: ", scanner);
        Course course = FS.findCourseByCode(scanner);
        Teacher teacher = FS.findTeacherById(scanner);

        Lesson lesson = new Lesson(lessonId, LessonType.valueOf(lesson_type), course, teacher);
        CS.addLesson(lesson);

        System.out.println("Lesson created: " + lesson);
    }

    public void assignHeadLector(Scanner scanner){
    Vector <Teacher> possible = db.getAllTeachers();
    for (Teacher te : possible){
        System.out.println(te);
    }
   
    String id_t = RS.readLine("Choose id of Teacher", scanner);
        
        for (Course c : CS.getCourses()){
        System.out.println(c);
    }
    String id_c = RS.readLine("Choose course", scanner);
    reS.assignHeadLector(id_t, id_c);
}

}
