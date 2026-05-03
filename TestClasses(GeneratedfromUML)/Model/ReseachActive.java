
import java.io.*;
import java.util.*;

/**
 * 
 */
public interface ReseachActive {

    /**
     * @return
     */
    public ResearchPaper getReserchPaper();

    /**
     * @return
     */
    public ResearchProject getResearchProject();

    /**
     * @return
     */
    public void DraftResearchPaper();

    /**
     * @param ResearchProject p 
     * @return
     */
    public void JoinProject(void ResearchProject p);

    /**
     * @return
     */
    public void BecomeResearchActive();

    /**
     * @param Comparator c 
     * @return
     */
    public void PrintPapers(void Comparator c);

    /**
     * @return
     */
    public void StartResearchProject();

    /**
     * @param ResearchProject 
     * @return
     */
    public void LeaveResearchProject(void ResearchProject);

}