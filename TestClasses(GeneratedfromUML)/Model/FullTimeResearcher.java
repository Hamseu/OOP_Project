
import java.io.*;
import java.util.*;

/**
 * 
 */
public class FullTimeResearcher extends Employee implements ReseachActive {

    /**
     * Default constructor
     */
    public FullTimeResearcher() {
    }

    /**
     * 
     */
    public boolean Reseacher = True;

    /**
     * 
     */
    public double hindex;

    /**
     * 
     */
    public Student fd;

    /**
     * 
     */
    public Student Current Supervisor;

    /**
     * @param String req 
     * @param Employer e 
     * @return
     */
    public abstract void makeRequest(void String req, void Employer e);

    /**
     * @return
     */
    public ResearchPaper getReserchPaper() {
        // TODO implement ReseachActive.getReserchPaper() here
        return null;
    }

    /**
     * @return
     */
    public ResearchProject getResearchProject() {
        // TODO implement ReseachActive.getResearchProject() here
        return null;
    }

    /**
     * @return
     */
    public void DraftResearchPaper() {
        // TODO implement ReseachActive.DraftResearchPaper() here
        return null;
    }

    /**
     * @param ResearchProject p 
     * @return
     */
    public void JoinProject(void ResearchProject p) {
        // TODO implement ReseachActive.JoinProject() here
        return null;
    }

    /**
     * @return
     */
    public void BecomeResearchActive() {
        // TODO implement ReseachActive.BecomeResearchActive() here
        return null;
    }

    /**
     * @param Comparator c 
     * @return
     */
    public void PrintPapers(void Comparator c) {
        // TODO implement ReseachActive.PrintPapers() here
        return null;
    }

    /**
     * @return
     */
    public void StartResearchProject() {
        // TODO implement ReseachActive.StartResearchProject() here
        return null;
    }

    /**
     * @param ResearchProject 
     * @return
     */
    public void LeaveResearchProject(void ResearchProject) {
        // TODO implement ReseachActive.LeaveResearchProject() here
        return null;
    }

}