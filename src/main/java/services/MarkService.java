package services;

import academic.Course;
import academic.Mark;
import database.UDBM;
import reports.Report;
import users.Student;

public class MarkService {
    public static final UDBM db = new UDBM();
    public void putMark(Student student, Course course, double midterm, double endterm, double att1, double att2, double finalExam) {
        Mark mark = new Mark(student, course, midterm, endterm, att1, att2, finalExam);
        student.addMark(course, mark);
        db.addMark(mark, student, course);
    }

    public Report generateMarksReport(Course course) {
        double sum = 0;
        int count = 0;

        for (Mark marke : db.getMarksByCourse(course)) {
            if (marke != null) {
                sum += marke.getTotal();
                count++;
            }
        }

        double average = count == 0 ? 0 : sum / count;
        return new Report("Marks report for " + course.getCourseName(),
                "Students with marks: " + count + "\nAverage total mark: " + average);
    }
}
