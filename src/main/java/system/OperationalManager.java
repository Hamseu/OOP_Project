package system;
import java.util.Scanner;

import controllers.AdminController;
import controllers.CourseController;
import controllers.EmployeeController;
import controllers.MarkController;
import controllers.ResearchController;
import controllers.StudentController;
import controllers.TeacherController;
import database.UDBM;

public class OperationalManager{
       public UserType switcher;
       public boolean running = true;
       private final UDBM db;
       private final MarkController MK;
       private final StudentController SC;
       private final AdminController AC;
       private final CourseController CC;
       private final EmployeeController EC;
       private final ResearchController RC;
       private final TeacherController TC;

       public OperationalManager(UDBM db){
          this.db = db;
          this.MK = new MarkController(db);
          this.SC = new StudentController();
          this.AC = new AdminController(db);
          this.CC = new CourseController();
          this.EC = new EmployeeController(db);
          this.RC = new ResearchController();
          this.TC = new TeacherController();
       }



       public void setType(UserType a){
          this.switcher = a;
       }

       public UserType getSwitch(){
        return this.switcher;
       }

       public static OperationalManager getInstance(UDBM db){
            OperationalManager inst = new OperationalManager(db);
            return inst;
       }

       public void operationFrame() {
    int counter = 0;

    System.out.println("Please, select the command");

    counter++;
    System.out.println(counter + ". Exit");

    if (switcher == UserType.ADMIN || switcher == UserType.MANAGER) {
        counter++;
        System.out.println(counter + ". Register new manager");

        counter++;
        System.out.println(counter + ". Register new admin");

        counter++;
        System.out.println(counter + ". Register new teacher");

        counter++;
        System.out.println(counter + ". Register new student");

        counter++;
        System.out.println(counter + ". Register new researcher");

        counter++;
        System.out.println(counter + ". Register new course");

        counter++;
        System.out.println(counter + ". Assign head lector");

        counter++;
        System.out.println(counter + ". Assign teacher to course");

        counter++;
        System.out.println(counter + ". Register student to course");

        counter++;
        System.out.println(counter + ". Create lesson");

        counter++;
        System.out.println(counter + ". Assign supervisor");

        counter++;
        System.out.println(counter + ". Print all users");

        counter++;
        System.out.println(counter + ". Print all courses");

        counter++;
        System.out.println(counter + ". Print all papers");

        counter++;
        System.out.println(counter + ". Print top cited researcher");
        counter++;
        System.out.println(counter + ". Create research project");
        counter++;
        System.out.println(counter + ". Make request");
        counter++;
        System.out.println(counter + ". Get own requests");
        counter++;
        System.out.println(counter + ". Get received requests");
        System.out.println("0. Close console");
    }

    if (switcher == UserType.TEACHER) {
        counter++;
        System.out.println(counter + ". Put mark");

        counter++;
        System.out.println(counter + ". Print marks report");

        counter++;
        System.out.println(counter + ". Print all courses");
    }

    if (switcher == UserType.STUDENT) {
        counter++;
        System.out.println(counter + ". Print all courses");
        counter++;
        System.out.println(counter + ". Sign up for a course");
        counter++;
        System.out.println(counter + ". Print marks");
        counter++; 
        System.out.println(counter + ". Print all courses");
        counter++;
        System.out.println(counter + ". Print your classes");
        counter++;
        System.out.println(counter + ". Print lessons");
    }

    if (switcher == UserType.RESEARCHER || switcher == UserType.STUDENT || switcher == UserType.TEACHER){
        counter++;
        System.out.println(counter + ". Become Researcher");
        counter++;
        System.out.println(counter + ". Join research project");
        counter++;
        System.out.println(counter + ". Start research project");
        counter++;
        System.out.println(counter + ". Create research paper");
        if (switcher == UserType.RESEARCHER || switcher == UserType.TEACHER){
        counter++;
        System.out.println(counter + ". Make request");
        counter++;
        System.out.println(counter + ". Get own requests");
        counter++;
        System.out.println(counter + ". Get received requests");
    }
        System.out.println("0. Close console");
    }

    
}

    public boolean executeOperation(int choice, UserType switcher, Active user, Scanner scanner) throws Exception {
    if (choice == 1) {
        return false;
    }
    if (choice == 0)
    {
        this.running = false;
    }

    if (switcher == UserType.ADMIN || switcher == UserType.MANAGER) {
        return executeAdminManagerOperation(choice, user, scanner);
    }

    if (switcher == UserType.TEACHER) {
        return executeTeacherOperation(choice, user, scanner);
    }

    if (switcher == UserType.STUDENT) {
        return executeStudentOperation(choice, user, scanner);
    }

    if (switcher == UserType.RESEARCHER) {
        return executeResearcherOperation(choice, user, scanner);
    }

    System.out.println("Unknown user type.");
    return true;
}

private  boolean executeTeacherOperation(int choice, Active user, Scanner scanner) throws Exception {
    switch (choice) {
        case 2:
            MK.putMark(scanner);
            break;

        case 3:
            MK.printMarksReport(scanner);
            break;

        case 4:
            CC.printAllCourses();
            break;

        case 5:
            RC.becomeResearcher(user, scanner);
            break;

        case 6:
            RC.joinResearchProject(scanner);
            break;

        case 7:
            RC.createResearchProject(user, scanner);
            break;

        case 8:
            RC.createResearchPaper(scanner);
            break;
        case 9:
            EC.makeRequest(user, scanner);
        case 10:
            EC.viewOwnRequests(user);
            break;
        case 11:
            EC.viewReceivedRequests(user);
            break;

        default:
            System.out.println("It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}
private boolean executeAdminManagerOperation(int choice, Active user, Scanner scanner) throws Exception {
    switch (choice) {
        case 2:
            AC.createManager(scanner);
            break;

        case 3:
            AC.createAdmin(scanner);
            break;

        case 4:
            AC.createTeacher(scanner);
            break;

        case 5:
            AC.createStudent(scanner);
            break;

        case 6:
            AC.createFullTimeResearcher(scanner);
            break;

        case 7:
            CC.createCourse(scanner);
            break;

        case 8:
            AC.assignHeadLector(scanner);
            break;

        case 9:
            AC.assignTeacherToCourse(scanner);
            break;

        case 10:
            AC.registerStudentToCourse(scanner);
            break;

        case 11:
            AC.createLesson(scanner);
            break;

        case 12:
            RC.assignSupervisor(scanner);
            break;

        case 13:
            AC.printAllUsers();
            break;

        case 14:
            CC.printAllCourses();
            break;

        case 15:
            RC.printAllPapers(scanner);
            break;

        case 16:
            RC.printTopCitedResearcher();
            break;

        case 17:
            RC.createResearchProject(user, scanner);
            break;
        case 18:
            EC.makeRequest(user, scanner);
            break;
        case 19:
            EC.viewOwnRequests(user);
            break;
        case 20:
            EC.viewReceivedRequests(user);
            break;
        case 0:
            System.out.println("Goodbye");
        default:
            System.out.println(" It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}

private  boolean executeStudentOperation(int choice, Active user, Scanner scanner) throws Exception {
    switch (choice) {
        case 2:
            CC.printAllCourses();
            break;

        case 3:
            SC.signUpForCourse(user, scanner);
            break;

        case 4:
            MK.printMarks(user, scanner);
            break;

        case 5:
            SC.printYourClasses(user, scanner);
            break;

        case 6:
            SC.printLessons(user, scanner);
            break;
        case 7:
            RC.becomeResearcher(user, scanner);
            break;

        case 8:
            RC.joinResearchProject(user, scanner);
            break;

        case 9:
            RC.createResearchProject(user, scanner);
            break;

        case 10:
            RC.createResearchPaper(user, scanner);
            break;

        default:
            System.out.println("It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}

private  boolean executeResearcherOperation(int choice, Active user, Scanner scanner) throws Exception {
    switch (choice) {
        case 2:
            RC.becomeResearcher(user, scanner);
            break;
        case 3: 
            RC.joinResearchProject(user, scanner);
            break;
        case 4:
            RC.createResearchProject(user, scanner);
            break;

        case 5:
            RC.createResearchPaper(user, scanner);
            break;
        case 6:
            EC.makeRequest(user, scanner);
        case 7:
            EC.viewOwnRequests(user);
            break;
        case 8:
            EC.viewReceivedRequests(user);
            break;

        default:
            System.out.println("It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}


}