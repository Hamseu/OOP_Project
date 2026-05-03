
import SystemicUserPackage.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Student extends User implements ReseachActive {

    /**
     * Default constructor
     */
    public Student() {
    }

    /**
     * 
     */
    public Vector<Course> courses;

    /**
     * 
     */
    public int Year;

    /**
     * 
     */
    public double GPA;

    /**
     * 
     */
    public double hindex;

    /**
     * 
     */
    public ResearchActive current_supervisor;

    /**
     * 
     */
    public Set<Course> finished_courses;

    /**
     * 
     */
    public boolean IsResearchactive;

    /**
     * 
     */
    public Set<Teacher> Supervised Students;


    /**
     * 
     */
    public Set<FullTimeResearcher> Supervised students;


    /**
     * @param Course c 
     * @return
     */
    public void pickUpCourse(void Course c) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void viewMarks() {
        // TODO implement here
        return null;
    }

    /**
     * @param Course c 
     * @return
     */
    public Mark viewMarks(void Course c) {
        // TODO implement here
        return null;
    }

    /**
     * @param Course c 
     * @return
     */
    public void dropCourse(void Course c) {
        // TODO implement here
        return null;
    }

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