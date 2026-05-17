package users;

import academic.Course;
import academic.Mark;
import database.UDBM;

import java.util.*;

public class StudentOperations {

    private final Student student;
    private final Scanner scanner;
    private final UDBM    db;

    public StudentOperations(Student student, Scanner scanner, UDBM db) {
        this.student = student;
        this.scanner = scanner;
        this.db      = db;
    }

    public void viewMarksAndTranscript() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║   GRADES & TRANSCRIPT  MENU      ║");
        System.out.println("╚══════════════════════════════════╝");

        boolean running = true;
        while (running) {
            System.out.println("""

                1. View full transcript
                2. View mark details for a specific course
                3. Print transcript
                0. Back
                """);
            System.out.print("Choice: ");
            switch (readInt()) {
                case 1  -> viewFullTranscript();
                case 2  -> viewCourseMarkDetails();
                case 3  -> printTranscript();
                case 0  -> running = false;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private void viewFullTranscript() {
        Map<Course, Mark> marks = loadMarks();
        if (marks.isEmpty()) { System.out.println("\n  No academic records found."); return; }

        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════════════╗");
        System.out.printf ("  ║  Student : %-47s║%n", student.getName() + " " + student.getSurname());
        System.out.printf ("  ║  ID      : %-47s║%n", student.getStudentId());
        System.out.printf ("  ║  Year    : %-47s║%n", student.getYearOfStudy());
        System.out.println("  ╚══════════════════════════════════════════════════════════╝");

        System.out.printf("%n  %-30s %6s %6s %6s %6s %6s %5s%n",
            "Course", "Att1", "Att2", "Final", "Total", "Grade", "GPA");
        System.out.println("  " + "─".repeat(72));

        int totalCreditsEarned = 0, totalCreditsAttempted = 0;

        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            Course c = entry.getKey();
            Mark   m = entry.getValue();
            System.out.printf("  %-30s %6.1f %6.1f %6.1f %6.1f %6s %5.2f%n",
                truncate(c.getCourseName(), 30),
                m.getFirstAttestation(), m.getSecondAttestation(),
                m.getFinalExam(), m.getTotal(), m.getLetterGrade(), m.getGpa());
            totalCreditsAttempted += c.getCredits();
            if (m.getTotal() >= 50) totalCreditsEarned += c.getCredits();
        }

        System.out.println("  " + "─".repeat(72));
        double gpa = calculateGpa(marks);
        System.out.println("\n  " + "═".repeat(72));
        System.out.printf("  CUMULATIVE GPA:          %.2f%n", gpa);
        System.out.printf("  TOTAL CREDITS EARNED:    %d%n",   totalCreditsEarned);
        System.out.printf("  TOTAL CREDITS ATTEMPTED: %d%n",   totalCreditsAttempted);
        String standing = gpa >= 3.5 ? "Dean's List" : gpa >= 2.0 ? "Good Standing" : "Academic Probation";
        System.out.printf("  ACADEMIC STANDING:       %s%n",   standing);
        System.out.println("  " + "═".repeat(72));
    }

    private void viewCourseMarkDetails() {
        Map<Course, Mark> marks = loadMarks();
        if (marks.isEmpty()) { System.out.println("\n  No courses found."); return; }

        List<Course> courseList = new ArrayList<>(marks.keySet());

        System.out.println("\n  Your courses:");
        for (int i = 0; i < courseList.size(); i++) {
            Course c = courseList.get(i);
            Mark   m = marks.get(c);
            System.out.printf("    %2d. %-35s [%s]%n", i + 1, c.getCourseName(), m.getLetterGrade());
        }
        System.out.println("     0. Cancel");
        System.out.print("  Select course: ");

        int idx = readInt();
        if (idx == 0 || idx < 1 || idx > courseList.size()) return;

        Course c = courseList.get(idx - 1);
        Mark   m = marks.get(c);

        System.out.println("\n  ┌────────────────────────────────────────────────┐");
        System.out.printf ("  │  Course : %-38s│%n", truncate(c.getCourseName(), 38));
        System.out.printf ("  │  Credits: %-38s│%n", c.getCredits() + " credits");
        System.out.println("  ├──────────────────────────────┬─────────────────┤");
        printMarkRow("  1st Att", m.getFirstAttestation(),  30.0);
        printMarkRow("  2nd Att", m.getSecondAttestation(), 30.0);
        printMarkRow("  Final",   m.getFinalExam(),          40.0);
        System.out.println("  ├──────────────────────────────┼─────────────────┤");
        System.out.printf ("  │  TOTAL        %5.1f / 100    │  %-14s │%n", m.getTotal(), m.getLetterGrade());
        System.out.printf ("  │  GPA          %-15.2f│  %-14s │%n", m.getGpa(),
            m.getTotal() >= 50 ? "PASSED" : "FAILED");
        System.out.println("  └──────────────────────────────┴─────────────────┘");
    }

    private void printTranscript() {
        Map<Course, Mark> marks = loadMarks();
        if (marks.isEmpty()) { System.out.println("\n  No academic records found."); return; }

        String line = "═".repeat(80);
        System.out.println("\n" + line);
        System.out.println(center("OFFICIAL ACADEMIC TRANSCRIPT", 80));
        System.out.println(center("Research-Oriented University", 80));
        System.out.println(line);
        System.out.printf("  %-20s %s %s%n", "Student Name:", student.getName(), student.getSurname());
        System.out.printf("  %-20s %s%n", "Student ID:",   student.getStudentId());
        System.out.printf("  %-20s Year %d%n", "Study Year:", student.getYearOfStudy());
        System.out.println("─".repeat(80));
        System.out.printf("  %-32s %5s %5s %5s %5s %5s %4s%n",
            "Course", "Att1", "Att2", "Fin", "Tot", "Gr", "GPA");
        System.out.println("  " + "─".repeat(76));

        int totalEarned = 0, totalAttempted = 0;
        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            Course c = entry.getKey();
            Mark   m = entry.getValue();
            System.out.printf("  %-32s %5.1f %5.1f %5.1f %5.1f %5s %4.2f%n",
                truncate(c.getCourseName(), 32),
                m.getFirstAttestation(), m.getSecondAttestation(),
                m.getFinalExam(), m.getTotal(), m.getLetterGrade(), m.getGpa());
            totalAttempted += c.getCredits();
            if (m.getTotal() >= 50) totalEarned += c.getCredits();
        }

        System.out.println("─".repeat(80));
        System.out.printf("  CUMULATIVE GPA         : %.2f%n", calculateGpa(marks));
        System.out.printf("  TOTAL CREDITS EARNED   : %d%n",   totalEarned);
        System.out.printf("  TOTAL CREDITS ATTEMPTED: %d%n",   totalAttempted);
        System.out.println(line);
    }

    private Map<Course, Mark> loadMarks() {
        Map<Course, Mark> marks = student.getMarks();
        if (marks.isEmpty()) {
            Vector<Course> courses = db.getStudentsCourses(student);
            for (Course c : courses) {
                Vector<Mark> courseMarks = db.getMarksByCourse(c);
                for (Mark m : courseMarks) {
                    if (m.getStudent().getId().equals(student.getId())) {
                        student.addMark(c, m);
                    }
                }
            }
        }
        return student.getMarks();
    }

    private double calculateGpa(Map<Course, Mark> marks) {
        double weightedSum  = 0;
        int    totalCredits = 0;
        for (Map.Entry<Course, Mark> e : marks.entrySet()) {
            int credits = e.getKey().getCredits();
            weightedSum  += e.getValue().getGpa() * credits;
            totalCredits += credits;
        }
        return totalCredits == 0 ? 0.0 : weightedSum / totalCredits;
    }

    private void printMarkRow(String label, double score, double max) {
        int filled = (int) Math.round((score / max) * 20);
        String bar = "█".repeat(Math.min(filled, 20)) + "░".repeat(Math.max(0, 20 - filled));
        System.out.printf("  │  %-10s %5.1f/%-5.1f  %s │%n", label, score, max, bar);
    }

    private String truncate(String s, int maxLen) {
        if (s == null) return "";
        return s.length() <= maxLen ? s : s.substring(0, maxLen - 1) + "…";
    }

    private String center(String s, int width) {
        if (s.length() >= width) return s;
        int pad = (width - s.length()) / 2;
        return " ".repeat(pad) + s;
    }

    private int readInt() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}
