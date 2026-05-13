package users;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import academic.Course;
import academic.Mark;
import enums.TeacherRank;
import research.ResearchPaper;
import research.Researcher;

public class Teacher extends Employee implements Researcher {
    private TeacherRank rank;
    private List<Course> courses;
    private double hIndex;
    public boolean is_researcher = false;
    private List<ResearchPaper> researchPapers;

    public Teacher(String id, String email, String login, String name, String surname,
                   String employeeId, double salary, String department,
                   String rank, int hIndex) {
        super(id, email, login,  name, surname, "TEACHER", employeeId,    salary,  department);
        this.rank = TeacherRank.valueOf(rank);
        this.hIndex = hIndex;
        if (this.isProfessor()){
            this.is_researcher = true;
        }
        this.courses = new ArrayList<>();
        this.researchPapers = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void putMark(Student student, Course course, Mark mark) {
        student.addMark(course, mark);
    }

    public boolean isProfessor() {
        return rank == TeacherRank.PROFESSOR;
    }

    @Override
    public double getHIndex() {
        return hIndex;
    }

    @Override
    public List<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    @Override
    public void addResearchPaper(ResearchPaper paper) {
        researchPapers.add(paper);
        paper.addAuthor(this);
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        researchPapers.stream().sorted(comparator).forEach(System.out::println);
    }

    @Override
    public String getResearcherFullName() {
        return getFullName();
    }

    public TeacherRank getRank() {
        return rank;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
