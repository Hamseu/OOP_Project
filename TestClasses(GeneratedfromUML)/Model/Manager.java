
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Manager extends Employee implements CourseManagers, ScienceManagers {

    /**
     * Default constructor
     */
    public Manager() {
    }

    /**
     * 
     */
    public String Department;

    /**
     * 
     */
    public Map<String, Vector<String>> Requests;

    /**
     * 
     */
    public Vector<String> RegisterNotification;

    /**
     * @return
     */
    public void showRequests() {
        // TODO implement here
        return null;
    }

    /**
     * @param String id 
     * @return
     */
    public Vector<String> GetRequestsByUserId(void String id) {
        // TODO implement here
        return null;
    }

    /**
     * @param Student s 
     * @param Cource c 
     * @return
     */
    public void assignStudentToCourse(void Student s, void Cource c) {
        // TODO implement here
        return null;
    }

    /**
     * @param Teacher t 
     * @param Course c 
     * @return
     */
    public void assignTeacherToCourse(void Teacher t, void Course c) {
        // TODO implement here
        return null;
    }

    /**
     * @param String course_id 
     * @param String name 
     * @param int credits 
     * @return
     */
    public void registerCourse(void String course_id, void String name, void int credits) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void updateProject() {
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
     * @param Course C 
     * @return
     */
    public void updateCourse(void Course C) {
        // TODO implement CourseManagers.updateCourse() here
        return null;
    }

    /**
     * @return
     */
    public void updateRegister() {
        // TODO implement ScienceManagers.updateRegister() here
        return null;
    }

    /**
     * @return
     */
    public void printPaperStatistics() {
        // TODO implement ScienceManagers.printPaperStatistics() here
        return null;
    }

    /**
     * @return
     */
    public void printProjectsStatistics() {
        // TODO implement ScienceManagers.printProjectsStatistics() here
        return null;
    }

}