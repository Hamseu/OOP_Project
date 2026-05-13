package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import academic.Course;
import database.UDBM;
import exceptions.CreditLimitException;
import users.Student;
import users.Teacher;

public class CourseService {
    private List<Course> courses = new ArrayList<>();
    private static final UDBM db = new UDBM();

    public void createCourse(Course course) {
        courses.add(course);
        db.addCourse(course);
    }

    public void assignTeacher(Course course, Teacher teacher) {
        course.addInstructor(teacher);
        db.assignTeacherToCourse(teacher, course);
    }

    public void registerStudent(Student student, Course course) throws CreditLimitException {
        student.registerCourse(course);
        db.assignStudentToCourse(student, course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Vector<Course> getCourseDB(){
        return db.getAllCourses();
    }
}
