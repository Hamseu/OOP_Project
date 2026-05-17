package demo;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;

import javax.security.auth.login.CredentialException;

import academic.Course;
import academic.Lesson;
import academic.Mark;
import comparators.PaperByCitationsComparator;
import comparators.PaperByDateComparator;
import comparators.PaperByPagesComparator;
import controllers.AdminController;
import controllers.CourseController;
import controllers.MarkController;
import controllers.NewsController;
import controllers.ResearchController;
import controllers.UserCreationController;
import database.UDBM;
import enums.LessonType;
import exceptions.ContentNotFoundException;
import exceptions.LowGPAException;
import exceptions.UserNotFoundException;
import reports.Report;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;
import services.CourseService;
import services.EmployeeService;
import services.MarkService;
import services.NewsService;
import services.ResearchService;
import services.StudentService;
import services.UserService;
import storage.ManualDataStorage;
import system.Active;
import system.Authenticate;
import system.OperationalManager;
import system.UserType;
import users.Admin;
import users.FullTimeResearcher;
import users.Manager;
import users.Student;
import users.Teacher;
import users.User;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final UDBM db = new UDBM();
    private static final UserService userService = new UserService();
   
    private static final CourseService courseService = new CourseService();
    private static final MarkService markService = new MarkService();
    private static final ResearchService researchService = new ResearchService();
    private static final UserCreationController createService = new UserCreationController(db);
    private static final EmployeeService employService = EmployeeService.getInstance();
    private static final StudentService  studentService = StudentService.getInstance();
    private static final AdminController adminController = new AdminController(db);
    private static final CourseController courseController = new CourseController(courseService);
    private static final MarkController markController = new MarkController(markService);
    private static final ResearchController researchController = new ResearchController(researchService);
    private static final NewsService newsService = new NewsService(db);
    private static final NewsController newsController = new NewsController(newsService);
    static boolean running = true;
    private static Vector<ResearchProject> projects = researchService.getProjects();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" Research-Oriented University System");
        System.out.println(" Console Demo");
        System.out.println("========================================");

         
        boolean loginer = Authenticate.consolewin(db, scanner);
        while (running) {
            
            if (loginer){
                Active actuser = ManualDataStorage.loadSession();
                OperationalManager op = OperationalManager.getInstance(actuser.profile);
                System.out.println("Hello, " + actuser.username + " Welcome to University system, please, choose an action available for you: ");
                op.operationFrame(); 
                int choice = readInt("Enter your command: ");  
                if (choice != 1){
                try {
                    executeOperation(choice, actuser.profile, actuser);
                }
                catch (Exception e) {
                     System.out.println(e.getMessage()); //TODO, Make More exceptions
                }
            }
            else{
                loginer = false;
            }
        }
                
             else{
                loginer = Authenticate.consolewin(db, scanner);
             }

            System.out.println();
        }
    }


    private static void printMenu() {
        System.out.println("--------------- MENU ---------------");
        System.out.println("1.  Create Student");
        System.out.println("2.  Create Teacher");
        System.out.println("3.  Create Manager");
        System.out.println("4.  Create Admin");
        System.out.println("5.  Create Full-Time Researcher");
        System.out.println("6.  Create Course");
        System.out.println("7.  Assign Teacher to Course");
        System.out.println("8.  Register Student to Course");
        System.out.println("9.  Create Lesson");
        System.out.println("10. Put Mark");
        System.out.println("11. Create Research Paper");
        System.out.println("12. Create Research Project");
        System.out.println("13. Join Research Project");
        System.out.println("14. Assign Research Supervisor");
        System.out.println("15. View All Users");
        System.out.println("16. View All Courses");
        System.out.println("17. Generate Marks Report");
        System.out.println("18. Print All Research Papers");
        System.out.println("19. Print Top Cited Researcher");
        System.out.println("20. Login");
        System.out.println("0.  Exit");
        System.out.println("------------------------------------");
    }

    private static void createStudent() {
        String id = readLine("User id: ");
        String login = readLine("Login: ");
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        String name = readLine("Name: ");
        String surname = readLine("Surname: ");
        String studentId = readLine("Student id: ");
        int year = readInt("Year of study: ");

        Student student = new Student(id, email, login,  name, surname, studentId, year);
        createService.addStudent(student, password);

        System.out.println("Student created: " + student);
    }

    private static void createTeacher() {
        String id = readLine("User id: ");
        String login = readLine("Login: ");
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        String name = readLine("Name: ");
        String surname = readLine("Surname: ");
        String employeeId = readLine("Employee id: ");
        double salary = readDouble("Salary: ");

        String department = readLine("Department: ");
        String rank = readLine("Rank: ");
        int hIndex = readInt("H-index: ");

        Teacher teacher = new Teacher(id, email, login, name, surname,
                employeeId, salary, department, rank, hIndex);

        createService.addTeacher(teacher, password);
        if (teacher.is_researcher){
        createService.addResearcher(hIndex, id);
        }
        System.out.println("Teacher created: " + teacher);
    }

    private static void createManager() {
        String id = readLine("User id: ");
        String login = readLine("Login: ");
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        String name = readLine("Name: ");
        String surname = readLine("Surname: ");
        String employeeId = readLine("Employee id: ");
        double salary = readDouble("Salary: ");

        String department = readLine("Department");
        String managerType = readLine("ManagerJob");

        Manager manager = new Manager(id,email,  login, name, surname,
                employeeId, salary, department, managerType);

        createService.addManager(manager, password);
        System.out.println("Manager created: " + manager);
    }

    private static void createAdmin() {
        String id = readLine("User id: ");
        String login = readLine("Login: ");
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        String name = readLine("Name: ");
        String surname = readLine("Surname: ");
        String employeeId = readLine("Employee id: ");
        double salary = readDouble("Salary: ");
        String department = readLine("Department");

        Admin admin = new Admin(id, email, login, name, surname,
                employeeId, salary, department);

        adminController.addUser(admin, password);
        System.out.println("Admin created: " + admin);
    }

    private static void createFullTimeResearcher() {
        String id = readLine("User id: ");
        String login = readLine("Login: ");
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        String name = readLine("Name: ");
        String surname = readLine("Surname: ");
        String employeeId = readLine("Employee id: ");
        double salary = readDouble("Salary: ");
        String department = readLine("Department");
        int hIndex = readInt("H-index: ");

        FullTimeResearcher researcher = new FullTimeResearcher(id, email,  login,
                name, surname, employeeId, salary, department, hIndex);

        createService.addUser(researcher, password);
        createService.addResearcher(hIndex, id);

        System.out.println("Full-time researcher created: " + researcher);
    }

    private static void createCourse() {
        String code = readLine("Course code: ");
        String name = readLine("Course name: ");
        int credits = readInt("Credits: ");

        Course course = new Course(code, name, credits);
        courseController.createCourse(course);

        System.out.println("Course created: " + course);
    }

    private static void assignTeacherToCourse() throws Exception {
        Course course = findCourseByCode();
        Teacher teacher = findTeacherById();

        courseController.assignTeacher(course, teacher);
        System.out.println("Teacher assigned to course.");
    }

    private static void registerStudentToCourse() throws Exception {
        Student student = findStudentById();
        Course course = findCourseByCode();

        courseController.registerStudent(student, course);
        System.out.println("Student registered to course.");
    }

    private static void createLesson() throws Exception {
        int lessonId = readInt("Lesson id(int): ");
        LessonType lessonType = chooseLessonType();
        Course course = findCourseByCode();
        Teacher teacher = findTeacherById();

        Lesson lesson = new Lesson(lessonId, lessonType, course, teacher);
        course.addLesson(lesson);

        System.out.println("Lesson created: " + lesson);
    }

    private static void putMark() throws Exception {
        Student student = findStudentById();
        Course course = findCourseByCode();

        double midterm = readDouble("Midterm");
        double endterm = readDouble("Endterm");
        double att1 = readDouble("First attestation: ");
        double att2 = readDouble("Second attestation: ");
        double finalExam = readDouble("Final exam: ");

        markController.putMark(student, course, midterm, endterm, att1, att2, finalExam);
        System.out.println("Mark added.");
    }

    private static void createResearchPaper() throws Exception {
        String paperId = readLine("Id: ");
        String authorId = readLine("AuthorId: ");
        String title = readLine("Paper title: ");
        String journal = readLine("Journal: ");
        int pages = readInt("Pages: ");
        int citations = readInt("Citations: ");
        String theme = readLine("Theme of work");
        String abstractions = readLine("Short summary: ");
        int year = readInt("Published year: ");
        int month = readInt("Published month: ");
        int day = readInt("Published day: ");

        String doi = readLine("DOI: ");

        ResearchPaper paper = new  ResearchPaper(
         paperId,
         authorId,
         title,
         theme,
         abstractions,
         citations,
         doi,
         pages,
         journal,
         LocalDate.of(year, month, month)
);

        db.addResearchPaper(paper);
        System.out.println("Research paper added to researcher.");
    }

    private static void createResearchProject(Active user) {
        String id = readLine("Project ID:");
        String title = readLine("Project title: ");
        Double budget = readDouble("Budget: ");
        ResearchProject project = new ResearchProject(id, title, budget);

        
        projects.add(project);
        researchController.createProject(project, db.getUserByID(user.user_id));

        System.out.println("Research project created.");
    }

    private static void joinResearchProject() throws Exception {
        ResearchProject project = findProjectByTopic();
        User user = findUserById();

        researchController.joinProject(project, user);
        System.out.println("User joined research project.");
    }

    private static void assignSupervisor() throws Exception {
        Student student = findStudentById();
        Researcher supervisor = findResearcherByUserId();

        researchController.assignSupervisor(student, supervisor);
        System.out.println("Supervisor assigned.");
    }

    private static void printAllUsers() {
        System.out.println("All users:");
        for (User user : adminController.viewUsers()) {
            System.out.println(user.getId() + " | " + user);
        }
    }

    private static void printAllCourses() {
        System.out.println("All courses:");
        for (Course course : courseController.viewCourses()) {
            System.out.println(course);
        }
    }

    private static void viewStudents() {
        System.out.println("Students:");
        for (Student student : db.getAllStudents()) {
            System.out.println(student.getFullName()
                    + " | login: " + student.getUsername()
                    + " | year: " + student.getYearOfStudy()
                    + " | GPA: " + student.getGpa());
        }
    }

    private static void viewTeachers() {
        System.out.println("Teachers:");
        for (Teacher teacher : db.getAllTeachers()) {
            System.out.println(teacher.getFullName()
                    + " | email: " + teacher.getEmail()
                    + " | rank: " + teacher.getRank());
        }
    }

    private static void printMarksReport() throws Exception {
        Course course = findCourseByCode();
        Report report = markController.generateReport(course);
        report.print();
    }

    private static void printAllPapers() {
        System.out.println("Sort by:");
        System.out.println("1. Date");
        System.out.println("2. Citations");
        System.out.println("3. Pages");

        int choice = readInt("Choose: ");

        if (choice == 1) {
            researchController.printAllPapers(new PaperByDateComparator());
        } else if (choice == 2) {
            researchController.printAllPapers(new PaperByCitationsComparator());
        } else if (choice == 3) {
            researchController.printAllPapers(new PaperByPagesComparator());
        } else {
            System.out.println("Wrong option.");
        }
    }

    private static void printTopCitedResearcher() {
        User top = researchController.getTopCitedResearcher();

        if (top == null) {
            System.out.println("No researchers.");
        } else {
            System.out.println("Top cited researcher: " + top.getFullName());
        }
    }

    private static User findUserById() throws Exception {
        String id = readLine("User id: ");
        if (db.getUserByID(id) == null){
            throw new UserNotFoundException("No such User");
        }
        return db.getUserByID(id);
    }

    private static Student findStudentById() throws Exception, UserNotFoundException {
        User user = findUserById();
        if (studentService.getStudentById(db, user.getId()) == null){
            throw new UserNotFoundException("No such Student");
        }
        return studentService.getStudentById(db, user.getId());
    }

    private static Teacher findTeacherById() throws Exception {
        User user = findUserById();

        if (db.getTeacherById(user.getId()) == null){
            throw new UserNotFoundException("No such Teacher");
        }
        return db.getTeacherById(user.getId());
    }

    private static Researcher findResearcherByUserId() throws UserNotFoundException, Exception {
        User user = findUserById();

        Vector<User> res =  db.getAllResearchers();
        for (User us : res){
            if (us.getId().equals(user.getId())){
                return (Researcher) us;
            }
        }
        throw new UserNotFoundException("No such researcher with this id");
    }

    private static Course findCourseByCode() throws Exception {
        String code = readLine("Course code: ");

        for (Course course : courseController.viewCourses()) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return course;
            }
        }

        throw new Exception("Course not found.");
    }

    private static ResearchProject findProjectByTopic() throws ContentNotFoundException {
         projects = researchService.getProjects();
         for (ResearchProject pr : projects){
            System.out.println(pr);
         }
        String topic = readLine("Project topic: ");
        for (ResearchProject project : projects) {
            if (project.getTopic().equalsIgnoreCase(topic)) {
                return project;
            }
        }

        throw new ContentNotFoundException("Project not found.");
    }


    private static LessonType chooseLessonType() {
        System.out.println("Choose lesson type:");
        LessonType[] values = LessonType.values();

        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }

        int choice = readInt("Lesson type: ");
        return values[choice - 1];
    }

    private static String readLine(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter integer number.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = Double.parseDouble(scanner.nextLine());
                System.out.println();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter number.");
            }
        }
    }


    public static boolean executeOperation(int choice, UserType switcher, Active user) throws Exception {
    if (choice == 1) {
        return false;
    }
    if (choice == 0)
    {
        running = false;
    }

    if (switcher == UserType.ADMIN || switcher == UserType.MANAGER) {
        return executeAdminManagerOperation(choice, user);
    }

    if (switcher == UserType.TEACHER) {
        return executeTeacherOperation(choice, user);
    }

    if (switcher == UserType.STUDENT) {
        return executeStudentOperation(choice, user);
    }

    if (switcher == UserType.RESEARCHER) {
        return executeResearcherOperation(choice, user);
    }

    System.out.println("Unknown user type.");
    return true;
}

public static void makeRequest(Active user){
      Vector<User> employee = employService.getAllEmployee(db);
      for (User us : employee){
        if (us.getProfile() != UserType.STUDENT){
        System.out.println(us);
        }
      }
      String receiver_id = readLine("Type id of receiver: ");
      String message = readLine("Write your request: ");
      employService.makeRequest(user.user_id, receiver_id, message, db);
}

public static void becomeResearcher(Active user) throws LowGPAException{
       User us = db.getUserByID(user.user_id);
       if (user.profile == UserType.STUDENT){
       if (db.getStudentByID(user.user_id).getGpa() < 3)
       {
          throw new LowGPAException("TOO LOW GPA");
       }
       Student st = studentService.getStudentById(db, user.user_id);
        researchController.addResearcher(us, 3, st);
        String sup_id = readLine("Supervisor ID");
        db.updateStudent(st.getId(), st.getYearOfStudy(), st.getGpa(), 3, true, sup_id );
       }
       else if (user.profile == UserType.TEACHER)
       {
        Teacher ts = db.getTeacherById(user.user_id);
        researchController.addResearcher(us, 3, ts);
        db.updateTeacher(ts.getId(), ts.getRank().name(), true, 3);
       }
}

private static void viewOwnRequests(Active user){
        for (String str : employService.getOwnRequests(db, user.user_id)){
            System.out.println(str);
        }
}

private static void viewReceivedRequests(Active user){
    for (String str : employService.getReceivedRequests(db, user.user_id)){
            System.out.println(str);
        }
}

private static void addNews(Active user) {
    System.out.println("\n========== ADD NEWS ==========");
    String title = readLine("Enter news title: ");
    String content = readLine("Enter news content: ");
    
    newsController.addNews(title, content, user.username);
}

private static void viewNews() {
    newsController.displayNewsList();
    
    int choice = readInt("Enter the news number to read details (0 to skip): ");
    if (choice > 0) {
        newsController.displayNewsDetail(choice);
    }
}

private static boolean executeTeacherOperation(int choice, Active user) throws Exception {
    switch (choice) {
        case 2:
            putMark();
            break;

        case 3:
            printMarksReport();
            break;

        case 4:
            printAllCourses();
            break;

        case 5:
            viewStudents();
            break;

        case 6:
            viewNews();
            break;

        case 7:
            becomeResearcher(user);
            break;

        case 8:
            joinResearchProject();
            break;

        case 9:
            createResearchProject(user);
            break;

        case 10:
            createResearchPaper();
            break;
        case 11:
            makeRequest(user);
            break;
        case 12:
            viewOwnRequests(user);
            break;
        case 13:
            viewReceivedRequests(user);
            break;

        default:
            System.out.println("It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}

public static void assignHeadLector(){
    Vector <Teacher> possible = db.getAllTeachers();
    for (Teacher te : possible){
        System.out.println(te);
    }
    String id_t = readLine("Choose id of Teacher");
    Vector <Course> courses = db.getAllCourses();
    for (Course c : courses){
        System.out.println(c);
    }
    String id_c = readLine("Choose course");
    db.assignHeadLector(id_t, id_c);
}
public static void signUpForCourse(Active user) throws CredentialException
{
    Vector <Course> courses = db.getAllCourses();
    for (Course c : courses){
        System.out.println(c);
    }
    String id_c = readLine("Choose course");
    Course c = db.getCourseById(id_c);
    Student s = db.getStudentByID(user.user_id); 
    if (studentService.countCredits(db,s) > 30){
     throw new CredentialException("Credit Limit excession");
    }
    else {
    db.assignStudentToCourse(s, c);
    }
}

public static void printMarks(Active user){
    
    Student s = db.getStudentByID(user.user_id);
    Vector <Course> cs = db.getStudentsCourses(s);
    for (Course c : cs){
    Vector <Mark> marks = db.getMarksByCourse(c);
    for (Mark m : marks){
        if (m.getStudent().getId().equals(user.user_id)){
           System.out.println(m);
        }
    }
    }
}

public static void printYourClasses(Active user){
    Student s = db.getStudentByID(user.user_id);
    Vector <Course> cs = db.getStudentsCourses(s);
    for (Course c : cs)
    {
        System.out.println(c);
    }
}

public static void printLessons(Active user){
    Student s = db.getStudentByID(user.user_id);
    Vector <Course> cs = db.getStudentsCourses(s);
    for (Course c : cs)
    {
        System.out.println(c);
    }
    String c_id = readLine("Select Course");
    Course cou = db.getCourseById(c_id);
    Vector <Lesson> ls = db.getStudentLessonsByCourse(s, cou);
    for (Lesson lse : ls){
        System.out.println(lse);
    }
}

private static boolean executeAdminManagerOperation(int choice, Active user) throws Exception {
    switch (choice) {
        case 2:
            createManager();
            break;

        case 3:
            createAdmin();
            break;

        case 4:
            createTeacher();
            break;

        case 5:
            createStudent();
            break;

        case 6:
            createFullTimeResearcher();
            break;

        case 7:
            createCourse();
            break;

        case 8:
            assignHeadLector();
            break;

        case 9:
            assignTeacherToCourse();
            break;

        case 10:
            registerStudentToCourse();
            break;

        case 11:
            createLesson();
            break;

        case 12:
            assignSupervisor();
            break;

        case 13:
            printAllUsers();
            break;

        case 14:
            printAllCourses();
            break;

        case 15:
            printAllPapers();
            break;

        case 16:
            printTopCitedResearcher();
            break;

        case 17:
            createResearchProject(user);
            break;
        case 18:
            makeRequest(user);
            break;
        case 19:
            viewOwnRequests(user);
            break;
        case 20:
            viewReceivedRequests(user);
            break;
        case 21:
            addNews(user);
            break;
        case 22:
            viewNews();
            break;
        case 0:
            System.out.println("Goodbye");
        default:
            System.out.println(" It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}

private static boolean executeStudentOperation(int choice, Active user) throws Exception {
    switch (choice) {
        case 2:
            printAllCourses();
            break;

        case 3:
            signUpForCourse(user);
            break;

        case 4:
            printMarks(user);
            break;

        case 5:
            printYourClasses(user);
            break;

        case 6:
            printLessons(user);
            break;

        case 7:
            viewTeachers();
            break;

        case 8:
            viewNews();
            break;

        case 9:
            becomeResearcher(user);
            break;

        case 10:
            joinResearchProject();
            break;

        case 11:
            createResearchProject(user);
            break;

        case 12:
            createResearchPaper();
            break;

        default:
            System.out.println("It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}

private static boolean executeResearcherOperation(int choice, Active user) throws Exception {
    switch (choice) {
        case 2:
            becomeResearcher(user);
            break;
        case 3:
            joinResearchProject();
            break;
        case 4:
            createResearchProject(user);
            break;
        case 5:
            createResearchPaper();
            break;
        case 6:
            makeRequest(user);
            break;
        case 7:
            viewOwnRequests(user);
            break;
        case 8:
            viewReceivedRequests(user);
            break;
        case 9:
            viewNews();
            break;
        default:
            System.out.println("It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}
}
