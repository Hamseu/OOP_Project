package users;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import research.ResearchPaper;
import research.Researcher;

public class FullTimeResearcher extends Employee implements Researcher {
    private double hIndex;
    private List<ResearchPaper> researchPapers;

    public FullTimeResearcher(String id, String email, String login, String name, String surname,
                              String employeeId, double salary, String department, double hIndex) {
        super(id, email,  login, name, surname, "RESEARCHER", employeeId, salary, department);
        this.hIndex = hIndex;
        this.researchPapers = new ArrayList<>();
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
}
