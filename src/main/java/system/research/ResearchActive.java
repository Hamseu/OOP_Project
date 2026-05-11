package system.research;
import java.util.Comparator;

public interface ResearchActive {
    ResearchPaper getResearchPaper();
    ResearchProject getResearchProject();
    void draftResearchPaper();
    void joinProject(ResearchProject p);
    void becomeResearchActive();
    void printPapers(Comparator<ResearchPaper> c);
    void startResearchProject();
    void leaveResearchProject(ResearchProject p);
}