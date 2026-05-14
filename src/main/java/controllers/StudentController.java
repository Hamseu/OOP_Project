package controllers;

import java.util.Scanner;
import java.util.Vector;

import javax.security.auth.login.CredentialException;

import academic.Course;
import academic.Lesson;
import exceptions.ContentNotFoundException;
import services.CourseService;
import services.StudentService;
import system.Active;
import users.Student;

public class StudentController extends Controller{
    private final CourseService CS = new CourseService();
    private final StudentService SSE = StudentService.getInstance();
    public void signUpForCourse(Active user, Scanner scanner) throws CredentialException
{
    Vector <Course> courses = CS.getCourses();
    for (Course c : courses){
        System.out.println(c);
    }
    String id_c = RS.readLine("Choose course", scanner);
    Course c = new Course();
    try {c = CS.getCourseById(id_c);
    }
    catch(ContentNotFoundException ce) {

    }
    Student s = SSE.getStudentById(db, user.user_id); 
    if (SSE.countCredits(db,s) > 30){
     throw new CredentialException("Credit Limit excession");
    }
    else {
    db.assignStudentToCourse(s, c);
    }
}
public  void printYourClasses(Active user, Scanner scanner){
    Student s = SSE.getStudentById(this.db, user.user_id);
    Vector <Course> cs = CS.getCoursesByStudent(s);
    for (Course c : cs)
    {
        System.out.println(c);
    }
}

public  void printLessons(Active user, Scanner scanner){
    Student s = db.getStudentByID(user.user_id);
    Vector <Course> cs = CS.getCoursesByStudent(s);
    for (Course c : cs)
    {
        System.out.println(c);
    }
    String c_id = RS.readLine("Select Course", scanner);
    Course cou = new Course();
    try  {
    cou = CS.getCourseById(c_id);
    Vector <Lesson> ls = db.getStudentLessonsByCourse(s, cou);
    for (Lesson lse : ls){
        System.out.println(lse);
    }
}
catch (ContentNotFoundException ce){
   
}
}

}
