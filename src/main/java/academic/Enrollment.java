package academic;

import java.io.Serializable;
import java.time.LocalDate;

import users.Student;

public class Enrollment implements Serializable {
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private boolean approved;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.approved = false;
    }

    public Student getStudent() {
        return this.student;
    }

    public Course getCourse() {
        return this.course;
    }

    public LocalDate getEnrollmentDate() {
        return this.enrollmentDate;
    }

    public boolean isApproved() {
        return this.approved;
    }
}
