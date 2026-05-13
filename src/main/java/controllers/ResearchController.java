package controllers;

import java.util.Comparator;

import exceptions.LowHIndexException;
import exceptions.NotResearcherException;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;
import services.ResearchService;
import users.Student;
import users.User;

public class ResearchController {
    private ResearchService researchService;

    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
    }

    public void addResearcher(User user, double hindex,  Researcher researcher) {
        researchService.addResearcher(user, hindex, researcher);
    }

    public void createProject(ResearchProject project, User u) {
        researchService.createProject(project, u);
    }

    public void assignSupervisor(Student student, Researcher supervisor) throws LowHIndexException {
        researchService.assignSupervisor(student, supervisor);
    }

    public void joinProject(ResearchProject project, User user) throws NotResearcherException {
        researchService.joinProject(project, user);
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        researchService.printAllPapers(comparator);
    }

    public User getTopCitedResearcher() {
        return researchService.getTopCitedResearcher();
    }
}
