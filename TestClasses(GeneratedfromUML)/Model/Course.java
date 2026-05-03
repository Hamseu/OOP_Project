
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Course {

    /**
     * Default constructor
     */
    public Course() {
    }

    /**
     * 
     */
    public String CourseName;

    /**
     * 
     */
    public Vector<Student> Students;

    /**
     * 
     */
    public int Credits;

    /**
     * 
     */
    public Teacher SeniourLecturer;

    /**
     * 
     */
    public static Vector<Course> Courses;

    /**
     * 
     */
    public Vector<Lesson> Lessons;

    /**
     * 
     */
    public Vector<Mark> Marks;

    /**
     * 
     */
    public Vector<Teacher> Teachers;




    /**
     * @return
     */
    public String getCourseName() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public Vector<Student> getStudents() {
        // TODO implement here
        return null;
    }

    /**
     * @param String user_id 
     * @return
     */
    public Student getStudent(void String user_id) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Teacher> getLecturers() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Teacher getSeniourLecturer() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Lesson> getLessons() {
        // TODO implement here
        return null;
    }

    /**
     * @param String lesson_id 
     * @return
     */
    public void updateLesson(void String lesson_id) {
        // TODO implement here
        return null;
    }

    /**
     * @param String nname 
     * @return
     */
    public void setCourseName(void String nname) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void resetStudents() {
        // TODO implement here
        return null;
    }

    /**
     * @param Student 
     * @return
     */
    public void addStudent(void Student) {
        // TODO implement here
        return null;
    }

    /**
     * @param int cre 
     * @return
     */
    public void changeCredits(void int cre) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public int getCredits() {
        // TODO implement here
        return 0;
    }

    /**
     * @param Teacher 
     * @return
     */
    public void changeSeniour(void Teacher) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void resetMarks() {
        // TODO implement here
        return null;
    }

    /**
     * @param Teacher 
     * @return
     */
    public void addTeacher(void Teacher) {
        // TODO implement here
        return null;
    }

    /**
     * @param Teacher 
     * @return
     */
    public void removeTeacher(void Teacher) {
        // TODO implement here
        return null;
    }

    /**
     * @param String user_id 
     * @return
     */
    public Mark getMarkById(void String user_id) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void showLessons() {
        // TODO implement here
        return null;
    }

    /**
     * @param String id 
     * @return
     */
    public Lesson getLessonById(void String id) {
        // TODO implement here
        return null;
    }

    /**
     * @param Teacher 
     * @return
     */
    public Vector<Lesson> getLessonsByTeacher(void Teacher) {
        // TODO implement here
        return null;
    }

}