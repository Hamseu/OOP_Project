package services;

import java.util.Comparator;
import java.util.Vector;

import database.UDBM;
import exceptions.LowHIndexException;
import exceptions.NotResearcherException;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;
import users.Student;
import users.User;

public class ResearchService {
    UDBM db = new UDBM();
    private Vector<Researcher> researchers = new Vector<>();
    private Vector<ResearchProject> projects = new Vector<>();

    public void addResearcher(User u, double h, Researcher r) {
        db.addResearcher(u.getId(), h);
        researchers.add(r);
    }

    public Vector<User> getDBResearchers(){
        return db.getAllResearchers();
    }

    public void createProject(ResearchProject project, User u) {
        projects.add(project);
        db.addResearchProjectToDB(project);
        db.addProjectMemberToDB(u.getId(), project.project_id);
    }

    public void assignSupervisor(Student student, Researcher supervisor) throws LowHIndexException {
        student.assignSupervisor(supervisor);
        String ide = "";
        if (student.getSupervisor() instanceof User supervisore) {
    ide = supervisore.getId();
}
        db.updateStudent(student.getId(), student.getYearOfStudy(), student.getGpa(), student.getHIndex(), student.is_researcher, ide);
    }

    public void joinProject(ResearchProject project, User user) throws NotResearcherException {
        project.joinProject(user);
        db.addProjectMemberToDB(user.getId(), project.project_id);
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        Vector<ResearchPaper> allPapers = new Vector<>();
        allPapers = db.getAllResearchPaper();
        
        allPapers.stream().sorted(comparator).forEach(System.out::println);
    }

    public User getTopCitedResearcher() {
        User top = null;
        int maxCitations = -1;

        for (User usaro : db.getAllResearchers()) {
            int total = 0;
            for (ResearchPaper paper : db.getPapersByUserId(usaro.getId())) {
                total += paper.getCitations();
            }

            if (total > maxCitations) {
                maxCitations = total;
                top = usaro;
            }
        }

        return top;
    }

    public Vector<User> getResearchers() {
        return db.getAllResearchers2();
    }

    public Vector<ResearchProject> getProjects() {
        return db.getAllResearchProjects();
    }
}
