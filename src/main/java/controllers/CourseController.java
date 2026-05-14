package controllers;

import java.util.Scanner;

import academic.Course;
import exceptions.CreditLimitException;
import services.CourseService;
import users.Student;
import users.Teacher;

public class CourseController extends Controller{
    private final CourseService CS = new CourseService();
    public CourseController() {
        
    }

    public void createCourse(Scanner scanner) {
        String code = RS.readLine("Course code: ", scanner);
        String name = RS.readLine("Course name: ", scanner);
        int credits = RS.readInt("Credits: ", scanner);

        Course course = new Course(code, name, credits);
        CS.createCourse(course);

        System.out.println("Course created: " + course);
    }

    public void assignTeacher(Course course, Teacher teacher) {
        CS.assignTeacher(course, teacher);
    }

    public void registerStudent(Student student, Course course) throws CreditLimitException {
        CS.registerStudent(student, course);
    }

    public void printAllCourses() {
        System.out.println("All courses:");
        for (Course course : CS.getCourses()) {
            System.out.println(course);
        }
    }
}
