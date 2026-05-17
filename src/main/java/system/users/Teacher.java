package system.users;

import system.User;
import system.enums.TeacherRank;
import system.research.ResearchActive;
import system.research.ResearchPaper;
import system.research.ResearchProject;

import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

public class Teacher extends Employee implements ResearchActive {
    public TeacherRank rank;
    public double hindex;
    public boolean isResearcher;

    // студенты под научным руководством
    private Vector<User> supervisedStudents;

    // исследовательские данные
    private Vector<ResearchPaper> papers;
    private Vector<ResearchProject> projects;

    public Teacher(String name, String id, String email, String profile,
                   Date hireDate, double salary, TeacherRank rank) {
        super(name, id, email, profile, hireDate, salary);
        this.rank = rank;
        this.hindex = 0.0;
        this.isResearcher = false;
        this.supervisedStudents = new Vector<>();
        this.papers = new Vector<>();
        this.projects = new Vector<>();
    }

    // --- Академические методы ---

    // public void setLessonMark(Mark mark, int value) {
    //     mark.setLessonMark(value);
    //     System.out.println(username + " set lesson mark: " + value);
    // }

    // public void setAttMark(Mark mark, int value) {
    //     mark.setAttMark(value);
    //     System.out.println(username + " set attendance mark: " + value);
    // }

    // public void setExamMark(Mark mark, int value) {
    //     mark.setExamMark(value);
    //     System.out.println(username + " set exam mark: " + value);
    // }

    // public void setFinalMark(Mark mark) {
    //     mark.calculateFinal();
    //     System.out.println(username + " finalized mark for student.");
    // }

    public void updateLesson(String lessonId, String newInfo) {
        System.out.println(username + " updated lesson [" + lessonId + "]: " + newInfo);
    }

    // --- Supervision ---

    public void addSupervisedStudent(User student) {
        if (!supervisedStudents.contains(student))
            supervisedStudents.add(student);
    }

    public void removeSupervisedStudent(User student) {
        supervisedStudents.remove(student);
    }

    public Vector<User> getSupervisedStudents() { return supervisedStudents; }

    // --- Employee ---

    @Override
    public void makeRequest(String request) {
        System.out.println("[Teacher request] " + username + ": " + request);
    }

    // --- ResearchActive ---

    @Override
    public void becomeResearchActive() {
        this.isResearcher = true;
        System.out.println(username + " is now research active.");
    }

    @Override
    public void draftResearchPaper() {
        if (!isResearcher) {
            System.out.println(username + " must be research active first.");
            return;
        }
        ResearchPaper paper = new ResearchPaper(this, "Draft", "TBD", 0);
        papers.add(paper);
        System.out.println(username + " drafted a new research paper.");
    }

    @Override
    public void joinProject(ResearchProject p) {
        p.addParticipant(this);
        if (!projects.contains(p)) projects.add(p);
        System.out.println(username + " joined project: " + p.getTitle());
    }

    @Override
    public void leaveResearchProject(ResearchProject p) {
        p.removeParticipant(this);
        projects.remove(p);
        System.out.println(username + " left project: " + p.getTitle());
    }

    @Override
    public void startResearchProject() {
        ResearchProject p = new ResearchProject(
            user_id + "_proj_" + projects.size(), "New Project", this
        );
        projects.add(p);
        System.out.println(username + " started project: " + p.getTitle());
    }

    @Override
    public ResearchPaper getResearchPaper() {
        return papers.isEmpty() ? null : papers.lastElement();
    }

    @Override
    public ResearchProject getResearchProject() {
        return projects.isEmpty() ? null : projects.lastElement();
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        papers.stream().sorted(c)
              .forEach(p -> System.out.println(p));
    }
}