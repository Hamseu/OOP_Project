package controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Scanner;

import comparators.PaperByCitationsComparator;
import comparators.PaperByDateComparator;
import comparators.PaperByPagesComparator;
import exceptions.ContentNotFoundException;
import exceptions.LowGPAException;
import exceptions.NotResearcherException;
import exceptions.UserNotFoundException;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;
import services.ResearchService;
import services.StudentService;
import services.UserService;
import system.Active;
import system.UserType;
import users.Student;
import users.Teacher;
import users.User;

public class ResearchController extends Controller{
    private final StudentService SSE = StudentService.getInstance();
    private final ResearchService reS = new ResearchService();
    private final UserService US = UserService.getInstance();
    public ResearchController() {
        
    }

    public void addResearcher(User user, double hindex,  Researcher researcher) {
        reS.addResearcher(user, hindex, researcher);
    }

    public void createResearchProject(Active user, Scanner scanner) {
        String id = RS.readLine("Project ID:", scanner);
        String title = RS.readLine("Project title: ", scanner);
        Double budget = RS.readDouble("Budget: ", scanner);
        ResearchProject project = new ResearchProject(id, title, budget);
        
        reS.createProject(project, db.getUserByID(user.user_id));

        System.out.println("Research project created.");
    }

    public void assignSupervisor(Scanner scanner) throws Exception {
        Student student = FS.findStudentById(scanner);
        Researcher supervisor = FS.findResearcherByUserId(scanner);

        reS.assignSupervisor(student, supervisor);
        System.out.println("Supervisor assigned.");
    }

    public  void joinResearchProject(Active user, Scanner scanner) throws ContentNotFoundException, UserNotFoundException, SQLException, NotResearcherException {
        ResearchProject project = FS.findProjectByTopic(scanner);
        User usere = FS.findUserById(user.user_id);

        reS.joinProject(project, usere);
        System.out.println("User joined research project.");
    }
     public  void joinResearchProject(Scanner scanner) throws ContentNotFoundException, UserNotFoundException, SQLException, NotResearcherException {
        ResearchProject project = FS.findProjectByTopic(scanner);
        String id = RS.readLine("UserId: ", scanner);
        User usere = FS.findUserById(id);

        reS.joinProject(project, usere);
        System.out.println("User joined research project.");
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        reS.printAllPapers(comparator);
    }

    public void printTopCitedResearcher() {
        User top = reS.getTopCitedResearcher();

        if (top == null) {
            System.out.println("No researchers.");
        } else {
            System.out.println("Top cited researcher: " + top.getFullName());
        }
    }

    public void createResearchPaper(Active user, Scanner scanner) throws Exception {
        String paperId = RS.readLine("Id: ", scanner);
        String authorId = user.user_id;
        String title = RS.readLine("Paper title: ", scanner);
        String journal = RS.readLine("Journal: ", scanner);
        int pages = RS.readInt("Pages: ", scanner);
        int citations = RS.readInt("Citations: ", scanner);
        String theme = RS.readLine("Theme of work", scanner);
        String abstractions = RS.readLine("Short summary: ", scanner);
        int year = RS.readInt("Published year: ", scanner);
        int month = RS.readInt("Published month: ", scanner);
        int day = RS.readInt("Published day: ", scanner);

        String doi = RS.readLine("DOI: ", scanner);

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
         LocalDate.of(year, month, day)
);

        reS.addResearchPaper(paper);
        System.out.println("Research paper added to researcher.");
    }

    public void printAllPapers(Scanner scanner) {
        System.out.println("Sort by:");
        System.out.println("1. Date");
        System.out.println("2. Citations");
        System.out.println("3. Pages");

        int choice = RS.readInt("Choose: ", scanner);

        switch (choice) {
         case 1:
            printAllPapers(new PaperByDateComparator());
            break;         
         case 2: 
            printAllPapers(new PaperByCitationsComparator());
            break;
         case 3: 
            printAllPapers(new PaperByPagesComparator());
            break;
         default: 
            System.out.println("Wrong option.");
        
    }
    }

    public void becomeResearcher(Active user, Scanner scanner) throws LowGPAException, UserNotFoundException{
       User us = US.findById(user.user_id);
       if (user.profile == UserType.STUDENT){
       if (db.getStudentByID(user.user_id).getGpa() < 3)
       {
          throw new LowGPAException("TOO LOW GPA");
       }
       Student st = SSE.getStudentById(db, user.user_id);
        reS.addResearcher(us, 3, st);
        String sup_id = RS.readLine("Supervisor ID", scanner);
        db.updateStudent(st.getId(), st.getYearOfStudy(), st.getGpa(), 3, true, sup_id );
       }
       else if (user.profile == UserType.TEACHER)
       {
        Teacher ts = db.getTeacherById(user.user_id);
        reS.addResearcher(us, 3, ts);
        db.updateTeacher(ts.getId(), ts.getRank().name(), true, 3);
       }
}

public void createResearchPaper(Scanner scanner) throws Exception {
        String paperId = RS.readLine("Id: ", scanner);
        String authorId = RS.readLine("AuthorId: ", scanner);
        String title = RS.readLine("Paper title: ", scanner);
        String journal = RS.readLine("Journal: ", scanner);
        int pages = RS.readInt("Pages: ", scanner);
        int citations = RS.readInt("Citations: ", scanner);
        String theme = RS.readLine("Theme of work", scanner);
        String abstractions = RS.readLine("Short summary: ", scanner);
        int year = RS.readInt("Published year: ", scanner);
        int month = RS.readInt("Published month: ", scanner);
        int day = RS.readInt("Published day: ", scanner);

        String doi = RS.readLine("DOI: ", scanner);

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
         LocalDate.of(year, month, day)
);

        reS.addResearchPaper(paper);
        System.out.println("Research paper added to researcher.");
    }

}
