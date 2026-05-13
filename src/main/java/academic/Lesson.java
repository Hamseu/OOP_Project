package academic;

import java.io.Serializable;
import java.sql.Time;

import enums.LessonType;
import users.Teacher;

public class Lesson implements Serializable {
    public int lessonId;
    public LessonType lessonType;
    public Course course;
    public Teacher teacher;
    private Time lesson_time;

    public Lesson(int lessonId, LessonType lessonType, Course course, Teacher teacher) {
        this.lessonId = lessonId;
        this.lessonType = lessonType;
        this.course = course;
        this.teacher = teacher;
    }

    public int getLessonId() {
        return lessonId;
    }
    public Time getTime(){
        return this.lesson_time;
    }
    public void setTime(Time t){
        this.lesson_time = t;
    }

    public LessonType getLessonType() {
        return this.lessonType;
    }

    public Course getCourse() {
        return course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return lessonType + " for " + course.getCourseName() + " by " + teacher.getFullName();
    }
}
