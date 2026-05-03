package SystemicUserPackage;

import ResearchPaper;
import ResearchProject;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class User {

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * 
     */
    protected String Username;

    /**
     * 
     */
    protected String Password;

    /**
     * 
     */
    protected String Email;

    /**
     * 
     */
    protected String user_id;

    /**
     * 
     */
    Vector<String> actionlogs;

    /**
     * 
     */
    static Vector<User> UserRegister;

    /**
     * 
     */
    public Set<ResearchPaper> OwnedPapers;

    /**
     * 
     */
    public Set<ResearchProject> Projects;


    /**
     * @return
     */
    public String GetName() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public String GetEmail() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public String GetID() {
        // TODO implement here
        return "";
    }

    /**
     * @param String new 
     * @return
     */
    public void ChangeEmail(void String new) {
        // TODO implement here
        return null;
    }

    /**
     * @param String new 
     * @return
     */
    public void changePassword(void String new) {
        // TODO implement here
        return null;
    }

}