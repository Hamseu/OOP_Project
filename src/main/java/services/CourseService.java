package services;

import java.sql.SQLException;
import java.util.Vector;

import academic.Course;
import academic.Lesson;
import database.UDBM;
import exceptions.ContentNotFoundException;
import exceptions.CreditLimitException;
import users.Student;
import users.Teacher;

public class CourseService {
    private Vector<Course> courses = new Vector<>();
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

    public Vector<Course> getCourses() {
        try {
        this.courses = db.getAllCourses();
        }
        catch (SQLException sq){
        System.out.println("Failed to load data");
        }
        return this.courses;
    }

    public void addLesson(Lesson l)
    {
        db.addLesson(l);
    }

    public Course getCourseById(String id_c) throws ContentNotFoundException{
            
        this.courses = getCourses();
        for (Course c : this.courses){
            if (c.getCourseCode().equals(id_c)){
                return c;
            }
        }
        throw new ContentNotFoundException("No such course");        
    }
    public Vector<Course> getCoursesByStudent(Student s){
        return db.getStudentsCourses(s);
    }

    public Vector<Course> getCoursesByTeacher(String id){
       return db.getCoursesByTeacher(id);
    }
}
