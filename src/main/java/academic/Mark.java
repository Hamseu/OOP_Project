package academic;

import java.io.Serializable;

import users.Student;

public class Mark implements Serializable {
    private Student student;
    private Course course;
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    public double midterm;
    public double endterm;

    public Mark(Student student, Course course, double firstAttestation, double secondAttestation, double finalExam) {
        this.student = student;
        this.course = course;
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
    }
        public Mark(Student student, Course course, double midterm, double endterm, double firstAttestation, double secondAttestation, double finalExam) {
        this.student = student;
        this.course = course;
        this.midterm = midterm;
        this.endterm = endterm;
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
    }

    public double getTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

    public double getMidterm(){
        return this.midterm;
    }
    public double getEndterm(){
        return this.endterm;
    }

    public String getLetterGrade() {
        double total = getTotal();
        if (total >= 95) return "A";
        if (total >= 90) return "A-";
        if (total >= 85) return "B+";
        if (total >= 80) return "B";
        if (total >= 75) return "B-";
        if (total >= 70) return "C+";
        if (total >= 65) return "C";
        if (total >= 60) return "C-";
        if (total >= 55) return "D+";
        if (total >= 50) return "D";
        return "F";
    }
    public Double getGpa() {
    double total = getTotal();

    if (total >= 95) return 4.0;
    else if (total >= 90) return 3.67;
    else if (total >= 85) return 3.33;
    else if (total >= 80) return 3.0;
    else if (total >= 75) return 2.67;
    else if (total >= 70) return 2.33;
    else if (total >= 65) return 2.0;
    else if (total >= 60) return 1.67;
    else if (total >= 55) return 1.33;
    else if (total >= 50) return 1.0;
    else return 0.0;
}
    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getFirstAttestation() {
        return firstAttestation;
    }

    public double getSecondAttestation() {
        return secondAttestation;
    }

    public double getFinalExam() {
        return finalExam;
    }

    @Override
    public String toString() {
        return student.getFullName() + " | " + course.getCourseName() +
                " | total=" + getTotal() + " | grade=" + getLetterGrade();
    }
}
