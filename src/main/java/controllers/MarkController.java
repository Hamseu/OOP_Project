package controllers;

import java.util.Scanner;
import java.util.Vector;

import academic.Course;
import academic.Mark;
import database.UDBM;
import reports.Report;
import services.MarkService;
import services.StudentService;
import system.Active;
import users.Student;

public class MarkController extends Controller{
    private final MarkService MS = new MarkService();
    private final StudentService SSE = StudentService.getInstance();
    public MarkController(UDBM db) {
        this.db = db;
    }

    public void putMark(Scanner scanner) throws Exception {
        Student student = FS.findStudentById(scanner);
        Course course = FS.findCourseByCode(scanner);

        double midterm = RS.readDouble("Midterm", scanner);
        double endterm = RS.readDouble("Endterm", scanner);
        double att1 = RS.readDouble("First attestation: ", scanner);
        double att2 = RS.readDouble("Second attestation: ", scanner);
        double finalExam = RS.readDouble("Final exam: ", scanner);

        MS.putMark(student, course, midterm, endterm, att1, att2, finalExam);
        System.out.println("Mark added.");
    }

    public Report generateReport(Course course) {
        return MS.generateMarksReport(course);
    }

    public void printMarksReport(Scanner scanner) throws Exception {
        Course course = FS.findCourseByCode(scanner);
        Report report = generateReport(course);
        report.print();
    }

    public void printMarks(Active user, Scanner scanner){
    
    Student s = SSE.getStudentById(db,user.user_id);
    Vector <Course> cs = db.getStudentsCourses(s);
    for (Course c : cs){
    Vector <Mark> marks = db.getMarksByCourse(c);
    for (Mark m : marks){
        if (m.getStudent().getId().equals(user.user_id)){
           System.out.println(m);
        }
    }
    }
}

}
