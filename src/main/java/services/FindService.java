package services;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

import academic.Course;
import database.UDBM;
import exceptions.ContentNotFoundException;
import exceptions.UserNotFoundException;
import research.ResearchProject;
import research.Researcher;
import users.Student;
import users.Teacher;
import users.User;
public class FindService {
    private final ReadService rs = ReadService.getInstance();
    UDBM db;
    public FindService(UDBM db){
        this.db = db;
    }

    public static FindService getInstance(UDBM db){
        return new FindService(db);
    }

    public Course findCourseByCode(Scanner scanner) throws ContentNotFoundException {
        String code = rs.readLine("Course code: ", scanner);

        try {for (Course course : db.getAllCourses()) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
    }
    catch (SQLException sq){
            System.out.println("Course Not Found or connection failure");
    }

        throw new ContentNotFoundException("Course not found.");
    }

    public User findUserById(Scanner scanner) throws UserNotFoundException {
        String id = rs.readLine("User id: ", scanner);
        if (db.getUserByID(id) == null){
            throw new UserNotFoundException("No such User");
        }
        return db.getUserByID(id);
    }

    public Student findStudentById(Scanner scanner) throws Exception, UserNotFoundException {
        String id = rs.readLine("Student id: ", scanner);
        if (db.getStudentByID(id) == null){
            throw new UserNotFoundException("No such Student");
        }
        return db.getStudentByID(id);
    }

    public Teacher findTeacherById(Scanner scanner) throws Exception {
        String id = rs.readLine("Enter teacher ID: ", scanner);


        if (db.getTeacherById(id) == null){
            throw new UserNotFoundException("No such Teacher");
        }
        return db.getTeacherById(id);
    }

    public Researcher findResearcherByUserId(Scanner scanner) throws UserNotFoundException, Exception {
        String id = rs.readLine("Enter Researcher ID: ", scanner);


        Vector<User> res =  db.getAllResearchers();
        for (User us : res){
            if (us.getId().equals(id)){
                return (Researcher) us;
            }
        }
        throw new UserNotFoundException("No such researcher with this id");
    }

    public ResearchProject findProjectByTopic(Scanner scanner) throws ContentNotFoundException {
         Vector <ResearchProject> projects = db.getAllResearchProjects();
         for (ResearchProject pr : projects){
            System.out.println(pr);
         }
        String topic = rs.readLine("Project topic: ", scanner);
        for (ResearchProject project : projects) {
            if (project.getTopic().equalsIgnoreCase(topic)) {
                return project;
            }
        }

        throw new ContentNotFoundException("Project not found.");
    }

    public User findUserById(String id) throws UserNotFoundException {
        return db.getUserByID(id);
    }

}
