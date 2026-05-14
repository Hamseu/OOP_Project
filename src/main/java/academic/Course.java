package academic;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import database.UDBM;
import users.Student;
import users.Teacher;

public class Course implements Serializable {
    private static final UDBM db = new UDBM();
    private String courseCode;
    private String courseName;
    private int credits;
    public String head_lector = "";
    public  Vector<Teacher> instructors;
    public  Vector<Student> students;
    public  Vector<Lesson> lessons;

    public Course(String courseCode, String courseName, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.instructors = new Vector<>();
        this.students = new Vector<>();
        this.lessons = new Vector<>();
    }
    public Course(){
        
    }

    public void addInstructor(Teacher teacher) {
        instructors.add(teacher);
        teacher.addCourse(this);
    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
            db.assignStudentToCourse(student, this);
        }
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        db.addLesson(lesson);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public List<Teacher> getInstructors() {
        return instructors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + credits + " credits)";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    public void fillStudents(){
        this.students = db.getStudentsByCourse(courseCode);
    }

    public void fillLessons(){
        this.lessons = db.getLessonByCourseID(this);
    }
    public void fillInstructors(){
        this.instructors = db.getTeachersByCourse(courseCode);
    }
}
