package controllers;

import java.util.List;

import academic.Course;
import exceptions.CreditLimitException;
import services.CourseService;
import users.Student;
import users.Teacher;

public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public void createCourse(Course course) {
        courseService.createCourse(course);
    }

    public void assignTeacher(Course course, Teacher teacher) {
        courseService.assignTeacher(course, teacher);
    }

    public void registerStudent(Student student, Course course) throws CreditLimitException {
        courseService.registerStudent(student, course);
    }

    public List<Course> viewCourses() {
        return courseService.getCourseDB();
    }
}
