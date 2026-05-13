package services;
import java.util.Vector;

import academic.Course;
import database.UDBM;
import users.Student;

public class StudentService {
    public static Vector<Student> students;
    public StudentService(){
        students = new Vector<>();
    }

    public static StudentService getInstance(){
        return new StudentService();
    }

    public Student getStudentById(UDBM db, String id){
        
        Student st = db.getStudentByID(id);
        return st;
    }

    public Vector<Student> getStudentsByCourse(UDBM db, String c_id){
          students = new Vector<>(db.getStudentsByCourse(c_id));
          return students;
    }

    public Vector<Student> getStudentsByLesson(UDBM db, String l_id){
        students = new Vector<>(db.getStudentsByLesson(l_id));
        return students;
    }

    public int countCredits(UDBM db, Student s_id){
        int count = 0;
        for (Course c : db.getStudentsCourses(s_id)){
            count += c.getCredits(); 
        }
        return count;
    }

}
