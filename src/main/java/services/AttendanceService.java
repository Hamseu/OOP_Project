package services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import academic.Course;
import academic.Lesson;
import database.UDBM;
import enums.AttendanceStatus;
import system.Active;
import users.Student;

public class AttendanceService {
    private final UDBM db = new UDBM();

    public void manageAttendance(Active user, Scanner scanner) {
        Course course = selectTeacherCourse(user, scanner);
        if (course == null) return;

        course.fillLessons();
        Lesson lesson = selectLesson(course, scanner);
        if (lesson == null) return;

        course.fillStudents();
        List<Student> students = new ArrayList<>(course.getStudents());
        if (students.isEmpty()) { System.out.println("  No students enrolled."); return; }

        Map<Student, AttendanceStatus> attendance = new LinkedHashMap<>();
        for (Student s : students) {
            attendance.put(s, AttendanceStatus.ABSENT);
        }

        boolean running = true;
        while (running) {
            printAttendanceTable(students, attendance, lesson);
            System.out.println("""

                1. Mark student PRESENT
                2. Mark student ABSENT
                3. Mark student LATE
                4. Save and finish
                0. Cancel
                """);
            System.out.print("Choice: ");

            switch (readInt(scanner)) {
                case 1 -> markStudent(students, attendance, AttendanceStatus.PRESENT, scanner);
                case 2 -> markStudent(students, attendance, AttendanceStatus.ABSENT,  scanner);
                case 3 -> markStudent(students, attendance, AttendanceStatus.LATE,    scanner);
                case 4 -> { saveAttendance(attendance, lesson); running = false; }
                case 0 -> running = false;
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private void markStudent(List<Student> students, Map<Student, AttendanceStatus> attendance,
                             AttendanceStatus status, Scanner scanner) {
        System.out.println("\n  Select student:");
        for (int i = 0; i < students.size(); i++)
            System.out.printf("    %d. %s %s  [%s]%n", i + 1,
                students.get(i).getName(), students.get(i).getSurname(),
                attendance.get(students.get(i)).name());
        System.out.println("    0. Cancel");
        System.out.print("  Select: ");

        int idx = readInt(scanner);
        if (idx == 0 || idx < 1 || idx > students.size()) return;

        Student student = students.get(idx - 1);
        attendance.put(student, status);
        System.out.printf("  %s %s marked as %s%n",
            student.getName(), student.getSurname(), status.name());
    }

    private void saveAttendance(Map<Student, AttendanceStatus> attendance, Lesson lesson) {
        for (Map.Entry<Student, AttendanceStatus> entry : attendance.entrySet()) {
            Student s = entry.getKey();
            AttendanceStatus status = entry.getValue();
            if (status == AttendanceStatus.PRESENT) {
                db.setAttendance(s, lesson);
            }
        }
        System.out.println("\n  Attendance saved successfully!");
    }

    private void printAttendanceTable(List<Student> students,
                                      Map<Student, AttendanceStatus> attendance,
                                      Lesson lesson) {
        System.out.println("\n  ┌─────────────────────────────────────────────────┐");
        System.out.printf ("  │  Lesson : %-39s│%n", lesson.getLessonType() + " #" + lesson.getLessonId());
        System.out.printf ("  │  Course : %-39s│%n", lesson.getCourse().getCourseName());
        System.out.println("  ├────┬──────────────────────────────┬──────────────┤");
        System.out.printf ("  │ %-3s│ %-30s│ %-12s │%n", "#", "Student", "Status");
        System.out.println("  ├────┼──────────────────────────────┼──────────────┤");

        int i = 1;
        for (Student s : students) {
            AttendanceStatus status = attendance.get(s);
            String statusStr = switch (status) {
                case PRESENT -> "✔ PRESENT";
                case ABSENT  -> "✗ ABSENT";
                case LATE    -> "~ LATE";
            };
            System.out.printf("  │ %-3d│ %-30s│ %-12s │%n",
                i++, s.getName() + " " + s.getSurname(), statusStr);
        }

        System.out.println("  └────┴──────────────────────────────┴──────────────┘");

        long present = attendance.values().stream().filter(v -> v == AttendanceStatus.PRESENT).count();
        long late    = attendance.values().stream().filter(v -> v == AttendanceStatus.LATE).count();
        long absent  = attendance.values().stream().filter(v -> v == AttendanceStatus.ABSENT).count();
        System.out.printf("  Present: %d  Late: %d  Absent: %d%n", present, late, absent);
    }

    private Course selectTeacherCourse(Active teacher, Scanner scanner) {
        Vector<Course> courses = db.getCoursesByTeacher(teacher.user_id);
        if (courses.isEmpty()) { System.out.println("  You have no assigned courses."); return null; }

        System.out.println("\n  Your courses:");
        for (int i = 0; i < courses.size(); i++)
            System.out.printf("    %d. %s%n", i + 1, courses.get(i).getCourseName());
        System.out.println("    0. Cancel");
        System.out.print("  Select course: ");

        int idx = readInt(scanner);
        if (idx == 0 || idx < 1 || idx > courses.size()) return null;
        return courses.get(idx - 1);
    }

    private Lesson selectLesson(Course course, Scanner scanner) {
        List<Lesson> lessons = new ArrayList<>(course.getLessons());
        if (lessons.isEmpty()) { System.out.println("  No lessons found for this course."); return null; }

        System.out.println("\n  Lessons:");
        for (int i = 0; i < lessons.size(); i++) {
            Lesson l = lessons.get(i);
            System.out.printf("    %d. %s #%d%n", i + 1, l.getLessonType(), l.getLessonId());
        }
        System.out.println("    0. Cancel");
        System.out.print("  Select lesson: ");

        int idx = readInt(scanner);
        if (idx == 0 || idx < 1 || idx > lessons.size()) return null;
        return lessons.get(idx - 1);
    }

    private int readInt(Scanner scanner) {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}
