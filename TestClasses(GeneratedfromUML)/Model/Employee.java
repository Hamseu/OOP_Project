
import SystemicUserPackage.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class Employee extends User {

    /**
     * Default constructor
     */
    public Employee() {
    }

    /**
     * 
     */
    public Date HireDate;

    /**
     * 
     */
    public Double Salary;

    /**
     * @param String req 
     * @param Employer e 
     * @return
     */
    public abstract void makeRequest(void String req, void Employer e);

    /**
     * @return
     */
    public String draftReport() {
        // TODO implement here
        return "";
    }

}