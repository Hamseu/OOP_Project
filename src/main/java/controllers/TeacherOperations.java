package users;

import academic.Course;
import academic.Mark;
import database.UDBM;

import java.util.*;

public class TeacherOperations {

    private final Teacher teacher;
    private final Scanner scanner;
    private final UDBM    db;

    public TeacherOperations(Teacher teacher, Scanner scanner, UDBM db) {
        this.teacher = teacher;
        this.scanner = scanner;
        this.db      = db;
    }

    public void manageMarks() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║     MARK MANAGEMENT MENU     ║");
        System.out.println("╚══════════════════════════════╝");

        boolean running = true;
        while (running) {
            System.out.println("""

                1. Put / update a student mark
                2. View all marks for a course
                3. View one student's mark details
                0. Back
                """);
            System.out.print("Choice: ");
            switch (readInt()) {
                case 1  -> putOrUpdateMark();
                case 2  -> viewCourseMarks();
                case 3  -> viewStudentMarkDetails();
                case 0  -> running = false;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private void putOrUpdateMark() {
        Course course = selectTeacherCourse();
        if (course == null) return;

        course.fillStudents();
        Student student = selectStudent(course);
        if (student == null) return;

        Mark existing = student.getMarks().get(course);
        double att1 = existing != null ? existing.getFirstAttestation()  : 0;
        double att2 = existing != null ? existing.getSecondAttestation() : 0;
        double fin  = existing != null ? existing.getFinalExam()         : 0;

        if (existing != null) printMark(student, course, existing);

        System.out.printf("""

            Which component to update?
              1. 1st Attestation  (max 30, current: %.1f)
              2. 2nd Attestation  (max 30, current: %.1f)
              3. Final Exam       (max 40, current: %.1f)
              0. Cancel
            """, att1, att2, fin);
        System.out.print("Choice: ");

        int component = readInt();
        if (component == 0) { System.out.println("  Cancelled."); return; }
        if (component < 1 || component > 3) { System.out.println("  Invalid."); return; }

        double maxScore = component == 3 ? 40.0 : 30.0;
        double score    = readScore("Enter score (0 – " + maxScore + "): ", maxScore);
        if (score < 0) return;

        switch (component) {
            case 1 -> att1 = score;
            case 2 -> att2 = score;
            case 3 -> fin  = score;
        }

        Mark newMark = new Mark(student, course, att1, att2, fin);
        teacher.putMark(student, course, newMark);
        db.addMark(newMark, student, course);

        System.out.println("\n  Mark saved!");
        printMark(student, course, newMark);
    }

    private void viewCourseMarks() {
        Course course = selectTeacherCourse();
        if (course == null) return;

        course.fillStudents();
        List<Student> students = new ArrayList<>(course.getStudents());
        if (students.isEmpty()) { System.out.println("  No students enrolled."); return; }

        System.out.printf("%n  %-5s %-25s %8s %8s %8s %8s %6s%n",
            "#", "Student", "Att1", "Att2", "Final", "Total", "Grade");
        System.out.println("  " + "─".repeat(72));

        int i = 1;
        for (Student s : students) {
            Mark m = s.getMarks().get(course);
            if (m == null) {
                System.out.printf("  %-5d %-25s %8s%n", i++, s.getName() + " " + s.getSurname(), "no mark");
            } else {
                System.out.printf("  %-5d %-25s %8.1f %8.1f %8.1f %8.1f %6s%n",
                    i++, s.getName() + " " + s.getSurname(),
                    m.getFirstAttestation(), m.getSecondAttestation(),
                    m.getFinalExam(), m.getTotal(), m.getLetterGrade());
            }
        }

        OptionalDouble avg = students.stream()
            .filter(s -> s.getMarks().get(course) != null)
            .mapToDouble(s -> s.getMarks().get(course).getTotal())
            .average();
        avg.ifPresent(v -> System.out.printf("%n  Course average: %.2f%n", v));
    }

    private void viewStudentMarkDetails() {
        Course course = selectTeacherCourse();
        if (course == null) return;
        course.fillStudents();
        Student student = selectStudent(course);
        if (student == null) return;
        Mark m = student.getMarks().get(course);
        if (m == null) { System.out.println("  No mark found for this student."); return; }
        printMark(student, course, m);
    }

    private Course selectTeacherCourse() {
        List<Course> courses = teacher.getCourses();
        if (courses.isEmpty()) { System.out.println("  You have no assigned courses."); return null; }

        System.out.println("\n  Your courses:");
        for (int i = 0; i < courses.size(); i++)
            System.out.printf("    %d. %s (%d credits)%n",
                i + 1, courses.get(i).getCourseName(), courses.get(i).getCredits());
        System.out.println("    0. Cancel");
        System.out.print("  Select course: ");

        int idx = readInt();
        if (idx == 0 || idx < 1 || idx > courses.size()) return null;
        return courses.get(idx - 1);
    }

    private Student selectStudent(Course course) {
        List<Student> students = new ArrayList<>(course.getStudents());
        if (students.isEmpty()) { System.out.println("  No students enrolled."); return null; }

        System.out.println("\n  Students in \"" + course.getCourseName() + "\":");
        for (int i = 0; i < students.size(); i++)
            System.out.printf("    %d. %s %s%n", i + 1,
                students.get(i).getName(), students.get(i).getSurname());
        System.out.println("    0. Cancel");
        System.out.print("  Select student: ");

        int idx = readInt();
        if (idx == 0 || idx < 1 || idx > students.size()) return null;
        return students.get(idx - 1);
    }

    private void printMark(Student student, Course course, Mark m) {
        System.out.println("\n  ┌────────────────────────────────────────────┐");
        System.out.printf ("  │  Student : %-33s│%n", student.getName() + " " + student.getSurname());
        System.out.printf ("  │  Course  : %-33s│%n", course.getCourseName());
        System.out.println("  ├──────────────────────┬─────────────────────┤");
        System.out.printf ("  │  1st Attestation     │  %5.1f / 30.0       │%n", m.getFirstAttestation());
        System.out.printf ("  │  2nd Attestation     │  %5.1f / 30.0       │%n", m.getSecondAttestation());
        System.out.printf ("  │  Final Exam          │  %5.1f / 40.0       │%n", m.getFinalExam());
        System.out.println("  ├──────────────────────┼─────────────────────┤");
        System.out.printf ("  │  TOTAL               │  %5.1f / 100        │%n", m.getTotal());
        System.out.printf ("  │  Letter Grade        │  %-20s │%n", m.getLetterGrade());
        System.out.printf ("  │  GPA                 │  %-20.2f │%n", m.getGpa());
        System.out.printf ("  │  Status              │  %-20s │%n", m.getTotal() >= 50 ? "PASSED" : "FAILED");
        System.out.println("  └──────────────────────┴─────────────────────┘");
    }

    private int readInt() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private double readScore(String prompt, double maxScore) {
        System.out.print("  " + prompt);
        try {
            double v = Double.parseDouble(scanner.nextLine().trim());
            if (v < 0 || v > maxScore) { System.out.printf("  Must be 0–%.1f%n", maxScore); return -1; }
            return v;
        } catch (NumberFormatException e) {
            System.out.println("  Invalid number.");
            return -1;
        }
    }
}
