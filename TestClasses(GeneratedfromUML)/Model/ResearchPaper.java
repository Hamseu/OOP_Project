
import SystemicUserPackage.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ResearchPaper {

    /**
     * Default constructor
     */
    public ResearchPaper() {
    }

    /**
     * 
     */
    public User Author;

    /**
     * 
     */
    public String Title;

    /**
     * 
     */
    public String Theme;

    /**
     * 
     */
    public String Abstraction;

    /**
     * 
     */
    public int Citations Number;

    /**
     * 
     */
    public Vector<User> Co-authors;

    /**
     * 
     */
    public Map<String, double> Metrics1;

    /**
     * 
     */
    public Vector<ResearchProject> related_projects;

    /**
     * 
     */
    public int Pages;

    /**
     * 
     */
    public Date Date;

    /**
     * 
     */
    public ResearchProject Published Papers;

    /**
     * 
     */
    public Set<User> Owner;


    /**
     * @param String t 
     * @return
     */
    public void setTitle(void String t) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String getTitle() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public String getThem() {
        // TODO implement here
        return "";
    }

    /**
     * @param String 
     * @return
     */
    public void setTheme(void String) {
        // TODO implement here
        return null;
    }

    /**
     * @param String 
     * @return
     */
    public void setAbstraction(void String) {
        // TODO implement here
        return null;
    }

    /**
     * @param User 
     * @return
     */
    public void addCoAuthor(void User) {
        // TODO implement here
        return null;
    }

    /**
     * @param String m 
     * @param double v 
     * @return
     */
    public void setMetrics(void String m, void double v) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void showMetrics() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String getAbstraction() {
        // TODO implement here
        return "";
    }

}