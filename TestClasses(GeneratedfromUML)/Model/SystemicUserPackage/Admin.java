package SystemicUserPackage;

import ScienceManagers;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Admin extends User implements ScienceManagers {

    /**
     * Default constructor
     */
    public Admin() {
    }

    /**
     * 
     */
    public Vector<String> notifications;

    /**
     * @param User u 
     * @param String new_name 
     * @return
     */
    public void changeUserName(void User u, void String new_name) {
        // TODO implement here
        return null;
    }

    /**
     * @param User u 
     * @param String new_password 
     * @return
     */
    public void changeUserPassword(void User u, void String new_password) {
        // TODO implement here
        return null;
    }

    /**
     * @param User u 
     * @param String new_email 
     * @return
     */
    public void changeUserEmail(void User u, void String new_email) {
        // TODO implement here
        return null;
    }

    /**
     * @param String u 
     * @param String p 
     * @param String e 
     * @param String ID 
     * @param UserType t 
     * @return
     */
    public void AddUserNew(void String u, void String p, void String e, void String ID, void UserType t) {
        // TODO implement here
        return null;
    }

    /**
     * @param User u 
     * @return
     */
    public void DeleteExistedUser(void User u) {
        // TODO implement here
        return null;
    }

    /**
     * @param User u 
     * @return
     */
    public void GetUserLogs(void User u) {
        // TODO implement here
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