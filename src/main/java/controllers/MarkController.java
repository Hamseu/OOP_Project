package controllers;

import academic.Course;
import reports.Report;
import services.MarkService;
import users.Student;

public class MarkController {
    private MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    public void putMark(Student student, Course course, double midterm, double endterm, double att1, double att2, double finalExam) {
        markService.putMark(student, course, midterm, endterm, att1, att2, finalExam);
    }

    public Report generateReport(Course course) {
        return markService.generateMarksReport(course);
    }
}
