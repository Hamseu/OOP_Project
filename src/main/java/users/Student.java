package users;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import academic.Course;
import academic.Mark;
import database.UDBM;
import exceptions.CreditLimitException;
import exceptions.LowHIndexException;
import research.ResearchPaper;
import research.Researcher;

public class Student extends User implements Researcher{
    private String studentId;
    private int yearOfStudy;
    private double gpa;
    public boolean is_researcher;
    private int totalCredits;
    private double hindex;
    private List<Course> courses;
    private Map<Course, Mark> marks;
    private Researcher supervisor;
    private List<ResearchPaper> researchPapers;

    public Student(String id, String email, String login, String name, String surname,
                   String studentId, int yearOfStudy) {
        super(id, email, login, name, surname, "STUDENT");
        this.studentId = studentId;
        this.yearOfStudy = yearOfStudy;
        this.gpa = 0.0;
        this.totalCredits = 0;
        this.hindex = 0.0;
        this.is_researcher = false;
        this.courses = new ArrayList<>();
        this.marks = new HashMap<>();
    }

    public void registerCourse(Course course) throws CreditLimitException {
        if (totalCredits + course.getCredits() > 21) {
            throw new CreditLimitException("Student cannot take more than 21 credits.");
        }
        courses.add(course);
        totalCredits += course.getCredits();
        course.addStudent(this);
    }

    public void assignSupervisor(Researcher supervisor) throws LowHIndexException {
        if (yearOfStudy < 4) {
            throw new LowHIndexException("Only 4th year bachelor students can have research supervisor.");
        }
        if (supervisor.getHIndex() < 3) {
            throw new LowHIndexException("Supervisor h-index must be at least 3.");
        }
        this.supervisor = supervisor;
    }

    public void addMark(Course course, Mark mark) {
        marks.put(course, mark);
    }

    public String getStudentId() {
        return studentId;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public double getGpa() {
        return gpa;
    }

    @Override
    public double getHIndex(){
        return this.hindex;
    }

    @Override
    public List<ResearchPaper> getResearchPapers() {
        if (!this.is_researcher){
            System.out.println("Not Allowed");
            return null;
        }
    if (researchPapers == null) {
        researchPapers = new ArrayList<>();
    }

    return researchPapers;
}

    @Override
public void addResearchPaper(ResearchPaper paper) {

    if (!this.is_researcher){
            System.out.println("Not Allowed");
        }
    if (researchPapers == null) {
        researchPapers = new ArrayList<>();
        researchPapers.add(paper);
    }

    
}
    @Override
public void printPapers(Comparator<ResearchPaper> comparator) {
    if (researchPapers == null || researchPapers.isEmpty()) {
        System.out.println("No research papers.");
        return;
    }

    List<ResearchPaper> sortedPapers = new ArrayList<>(researchPapers);
    sortedPapers.sort(comparator);

    for (ResearchPaper paper : sortedPapers) {
        System.out.println(paper);
    }
}

public void becomeResearcher() throws Exception {
    if (this.gpa <= 3.5) {
        throw new Exception("Only students with GPA higher than 3.5 can become researchers.");
    }

    this.is_researcher = true;

    if (researchPapers == null) {
        researchPapers = new ArrayList<>();
    }
    new UDBM().addResearcher(studentId, hindex);
    if (this.supervisor instanceof User su){
        new UDBM().updateStudent(studentId, yearOfStudy, gpa, hindex, is_researcher, su.getId());
    }
}
@Override
public String getResearcherFullName() {
    return getName() + " " + getSurname();
}

    public int getTotalCredits() {
        return totalCredits;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Map<Course, Mark> getMarks() {
        return marks;
    }

    public Researcher getSupervisor() {
        return supervisor;
    }
}
