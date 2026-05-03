
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Teacher extends Employee implements ReseachActive, CourseManagers {

    /**
     * Default constructor
     */
    public Teacher() {
    }

    /**
     * 
     */
    public TeacherRank rank;

    /**
     * 
     */
    public double hindex;

    /**
     * 
     */
    public boolean IsReseacher;

    /**
     * 
     */
    public Student Current Supervisor;


    /**
     * @return
     */
    public void setLessonMark() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void setAttMark() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void setExamMark() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void setFinalMark() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void updateLesson() {
        // TODO implement here
        return null;
    }

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

    /**
     * @param Course C 
     * @return
     */
    public void updateCourse(void Course C) {
        // TODO implement CourseManagers.updateCourse() here
        return null;
    }

}