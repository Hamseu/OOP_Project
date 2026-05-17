package controllers;
import java.util.Scanner;
import java.util.Vector;

import academic.Course;
import academic.Mark;
import database.UDBM;
import exceptions.ContentNotFoundException;
import services.CourseService;
import services.StudentService;
import system.Active;

public class TeacherController extends Controller{
    private final UserCreationController UCC;
    private final CourseService CS = new CourseService();
    private final StudentService SSE = new StudentService();
    public TeacherController(UDBM db){
        this.db = db;
        this.UCC = UserCreationController.getInstance(db);
    }

    public static TeacherController getInstance(UDBM db){
       return new TeacherController(db);
    }

    public void printCourseMarkDetails(Scanner scanner, Active user){
     System.out.println("""
             Your courses: 
             """);
     Vector<Course> courses = CS.getCoursesByTeacher(user.user_id);
     for(Course c : courses){
        System.out.println(c);
     }
     String chooser = RS.readLine("Choose course: ", scanner);
     Course c =new Course();
     try {
         c = CS.getCourseById(chooser);
     } catch (ContentNotFoundException e) {
     }
     Vector<Mark> marks = db.getMarksByCourse(c);
     for (Mark m: marks){
        System.out.printf(
            "%-25s %8.1f %8.1f %8.1f %8.1f %8.1f %6s %6.2f%n",
            m.getStudent().getFullName(),
            m.getFirstAttestation(),
            m.getSecondAttestation(),
            m.getMidterm(),
            m.getEndterm(),
            m.getFinalExam(),
            m.getLetterGrade(),
            m.getGpa()
    );
     }
}
}
