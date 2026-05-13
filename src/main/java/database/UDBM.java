package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

import java.sql.Date;
import java.time.LocalDate;
import research.ResearchPaper;

import users.Employee;
import users.Manager;
import users.Student;
import users.Teacher;
import users.User;
import system.UserType;
import academic.Course;
import academic.Mark;
import enums.LessonType;
import academic.Lesson;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;

public class UDBM {
    Connection conn;
        public UDBM(){ 
            try {
            this.conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/university",
                "postgres",
                "Horse"
            );
            System.out.println("Connected!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection failed");
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
    String id = rs.getString("user_id");
    String login = rs.getString("username");
    String email = rs.getString("email");
    String profile = rs.getString("profile_type");
    String name = rs.getString("name");
    String surname = rs.getString("surname");

    return new User(id, email, login, name, surname, profile);
}

private int executeById(String sql, String id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate();
        }
    }

    private boolean existsById(String sql, String id) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Vector<User> getUsers(){
         Vector <User> users = new Vector<User>();
         String sql = """
        SELECT * FROM users;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()){
    
            while (rs.next()){
                String username = rs.getString("username");
                String id = rs.getString("user_id");
                String email = rs.getString("email");
                String profile = rs.getString("profile_type");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                
                users.add(new User(id, email, username, name, surname, profile));
            }
    }
    catch (SQLException s ){
       System.out.println("Unexpected Error");
    }    
    return users;
} 

   public User getUserByID(String ide){
         String sql = """
        SELECT * FROM users WHERE user_id = ?;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, ide);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return mapUser(rs);}
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("No such user");
    }
    return null;
}

public User getUserByUsername(String usernamer){
         String sql = """
        SELECT * FROM users WHERE username = ?;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, usernamer);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return mapUser(rs);}
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("No such user");
    }
    return null;
}

// ---------------------------------------------------------------


 public String getPasswordByID(String ide){
    String pass;
    String sql = """
        SELECT * FROM passwords WHERE user_id = ?;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, ide);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                pass = rs.getString("pass");
                return pass;    
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("No such password or Connection Loss");
    }
    return null;
 }

 public void updatePassword(String userId, String oldPassword, String newPassword) {
        String sql = """
            UPDATE passwords
            SET pass = ?
            WHERE user_id = ? AND pass = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, userId);
            ps.setString(3, oldPassword);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update password.");
        }
    }

    public boolean removePassword(String userId, String password) {
        String sql = """
            DELETE FROM passwords
            WHERE user_id = ? AND pass = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, password);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove password.");
            return false;
        }
    }
    // ----------------------------------------------------------------
 
 public void addActiveSession(User user){
    String sql = """
        INSERT INTO active_sessions (user_id, username, profile_type)
        VALUES (?, ?, ?)
        ON CONFLICT (user_id)
        DO UPDATE SET 
            username = EXCLUDED.username,
            profile_type = EXCLUDED.profile_type
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, user.getId());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getProfile().name());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("User already logged in");
    }
 }
 public boolean removeActiveSession(String userId) {
        String sql = """
            DELETE FROM active_sessions
            WHERE user_id = ?
        """;

        try {
            return executeById(sql, userId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove active session.");
            return false;
        }
    }

 public void closeCon(){
    try {
      this.conn.close();
    }
    catch (SQLException s){
        System.out.println("No current connection");
    }
 }

 public void addUser(User user, String password) {
    String userSql = """
        INSERT INTO users (user_id, username, email, profile_type, name, surname)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    String passwordSql = """
        INSERT INTO passwords (pass, user_id)
        VALUES (?, ?)
    """;

    try {
        conn.setAutoCommit(false);

        try (
            PreparedStatement userPs = conn.prepareStatement(userSql);
            PreparedStatement passPs = conn.prepareStatement(passwordSql)
        ) {
            userPs.setString(1, user.getId());
            userPs.setString(2, user.getUsername());
            userPs.setString(3, user.getEmail());
            userPs.setString(4, user.getProfile().name());
            userPs.setString(5, user.getName());
            userPs.setString(6, user.getSurname());

            userPs.executeUpdate();

            passPs.setString(1, password);
            passPs.setString(2, user.getId());

            passPs.executeUpdate();

            conn.commit();
        }

    } catch (SQLException e) {
        try {
            conn.rollback();
        } catch (SQLException rollbackException) {
            rollbackException.printStackTrace();
        }

        e.printStackTrace();
        System.out.println("Failed to add user.");

    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


public void addEmployee(Employee employee, String password) {
    addUser(employee, password);

    String employeeSql = """
        INSERT INTO employee (user_id, employee_id, salary, departmenttype)
        VALUES (?, ?, ?, ?)
    """;

    try (PreparedStatement ps = conn.prepareStatement(employeeSql)) {
        ps.setString(1, employee.getId());
        ps.setString(2, employee.getEmployeeId());
        ps.setDouble(3, employee.getSalary());
        ps.setString(4, employee.getDepartment().name());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to add employee.");
    }
}

public void updateEmployee(String userId, String employeeId, double salary, String departmentType) {
        String sql = """
            UPDATE employee
            SET employee_id = ?, salary = ?, departmenttype = ?
            WHERE user_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employeeId);
            ps.setDouble(2, salary);
            ps.setString(3, departmentType);
            ps.setString(4, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update employee.");
        }
    }

    public boolean removeEmployee(String userId) {
        String sql = """
            DELETE FROM employee
            WHERE user_id = ?
        """;

        try {
            return executeById(sql, userId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove employee.");
            return false;
        }
    }

public void addStudent(Student student, String password) {
    addUser(student, password);

    String sql = """
        INSERT INTO student (user_id, year, GPA, h_index, is_researcher, current_supervisor)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, student.getId());
        ps.setDouble(2, student.getYearOfStudy());
        ps.setDouble(3, student.getGpa());
        ps.setDouble(4, student.getHIndex());
        ps.setBoolean(5, student.is_researcher);

        if (student.getSupervisor() == null) {
            ps.setNull(6, java.sql.Types.VARCHAR);
        } else if (student.getSupervisor() instanceof User u){
            ps.setString(6, u.getId());
        }

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to add student.");
    }
}

public void addTeacher(Teacher teacher, String password) {
    addEmployee(teacher, password);

    String sql = """
        INSERT INTO teachers (user_id, rank, is_researcher, h_index)
        VALUES (?, ?, ?, ?)
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, teacher.getId());
        ps.setString(2, teacher.getRank().name());
        ps.setBoolean(3, teacher.is_researcher);
        ps.setDouble(4, teacher.getHIndex());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to add teacher.");
    }
}

public void updateTeacher(String userId, String rank, boolean isResearcher, double hIndex) {
        String sql = """
            UPDATE teachers
            SET rank = ?, is_researcher = ?, h_index = ?
            WHERE user_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rank);
            ps.setBoolean(2, isResearcher);
            ps.setDouble(3, hIndex);
            ps.setString(4, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update teacher.");
        }
    }

    public boolean removeTeacher(String userId) {
        String sql = """
            DELETE FROM teachers
            WHERE user_id = ?
        """;

        try {
            return executeById(sql, userId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove teacher.");
            return false;
        }
    }

public void addManager(Manager manager, String password) {
    addEmployee(manager, password);

    String sql = """
        INSERT INTO manager (user_id, department)
        VALUES (?, ?)
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, manager.getId());
        ps.setString(2, manager.getDepartment().name());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to add manager.");
    }
}

public void updateManager(String userId, String department) {
        String sql = """
            UPDATE manager
            SET department = ?
            WHERE user_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department);
            ps.setString(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update manager.");
        }
    }

    public boolean removeManager(String userId) {
        String sql = """
            DELETE FROM manager
            WHERE user_id = ?
        """;

        try {
            return executeById(sql, userId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove manager.");
            return false;
        }
    }

public void addResearcher(String userId, double hIndex) {
    String sql = """
        INSERT INTO researchers (user_id, h_index, researcher_active)
        VALUES (?, ?, ?)
        ON CONFLICT (user_id)
        DO UPDATE SET
            h_index = EXCLUDED.h_index,
            researcher_active = EXCLUDED.researcher_active
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, userId);
        ps.setDouble(2, hIndex);
        ps.setBoolean(3, true);

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to add researcher.");
    }
}
public void updateResearcher(String userId, double hIndex, boolean active) {
        String sql = """
            UPDATE researchers
            SET h_index = ?, researcher_active = ?
            WHERE user_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, hIndex);
            ps.setBoolean(2, active);
            ps.setString(3, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update researcher.");
        }
    }

    public boolean removeResearcher(String userId) {
        String sql = """
            DELETE FROM researchers
            WHERE user_id = ?
        """;

        try {
            return executeById(sql, userId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove researcher.");
            return false;
        }
    }

 public void updateStudent(String userId, double year, double gpa, double hIndex, boolean isResearcher, String supervisorId) {
        String sql = """
            UPDATE student
            SET year = ?, GPA = ?, h_index = ?, is_researcher = ?, current_supervisor = ?
            WHERE user_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, year);
            ps.setDouble(2, gpa);
            ps.setDouble(3, hIndex);
            ps.setBoolean(4, isResearcher);

            if (supervisorId == null || supervisorId.isBlank()) {
                ps.setNull(5, java.sql.Types.VARCHAR);
            } else {
                ps.setString(5, supervisorId);
            }

            ps.setString(6, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update student.");
        }
    }

    public boolean removeStudent(String userId) {
        String sql = """
            DELETE FROM student
            WHERE user_id = ?
        """;

        try {
            return executeById(sql, userId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove student.");
            return false;
        }
    }

public boolean removeUser(String userId) {
    String sql = """
        DELETE FROM users
        WHERE user_id = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, userId);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to remove user.");
        return false;
    }
}
public void assignSupervisor(String studentId, String supervisorId) {
        String checkSql = """
            SELECT h_index
            FROM researchers
            WHERE user_id = ? AND researcher_active = true
        """;

        String updateSql = """
            UPDATE student
            SET current_supervisor = ?
            WHERE user_id = ?
        """;

        try {
            conn.setAutoCommit(false);

            double hIndex;

            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setString(1, supervisorId);

                try (ResultSet rs = checkPs.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Supervisor is not active researcher.");
                    }
                    hIndex = rs.getDouble("h_index");
                }
            }

            if (hIndex < 3) {
                throw new SQLException("Supervisor h-index must be at least 3.");
            }

            try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                updatePs.setString(1, supervisorId);
                updatePs.setString(2, studentId);
                updatePs.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            System.out.println("Failed to assign supervisor.");
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

public void addCourse(Course c){
     String sql = """
             INSERT INTO courses (course_id, course_name, credits) VALUES (?, ?, ?)
             """;
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(3, c.getCredits());
            ps.setString(1, c.getCourseCode());
            ps.setString(2, c.getCourseName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

public boolean removeCourse(Course c){
        String sql = """
                DELETE FROM courses WHERE course_id = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, c.getCourseCode());
            ps.executeUpdate();
            return true;
        }
        catch (SQLException sq) {
         sq.printStackTrace();
         return false;
        }
}

public void updateCourseSeniourLector(User u, Course c){
    String sql = """
            INSERT INTO seniour_lecturers (user_id, course_id) VALUES (?, ?)
            """;
    try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, u.getId());
            ps.setString(2, c.getCourseCode());
            ps.executeUpdate();
    }
    catch (SQLException s){
        s.printStackTrace();
    }
}
public void updateCourse(Course c) {
        String sql = """
            UPDATE courses
            SET course_name = ?, credits = ?
            WHERE course_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCourseName());
            ps.setInt(2, c.getCredits());
            ps.setString(3, c.getCourseCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update course.");
        }
    }

    public void addLesson(Lesson l){
        String sql = """
                INSERT INTO lessons (lesson_id, course_id, lesson_type, time, teacher)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, l.getLessonId());
            ps.setString(2, l.getCourse().getCourseCode());
            ps.setString(3, l.getLessonType().name());
            ps.setString(5, l.getTeacher().getId());
            ps.setTime(4, l.getTime());
            ps.executeUpdate();
        }
        catch (SQLException s)
        {
            s.printStackTrace();
        }
    }

    public void assignStudentToCourse(Student s, Course c){
        String sql = """
                INSERT INTO enrollments (user_id, course_id) VALUES (?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, s.getId());
            ps.setString(2, c.getCourseCode());
            ps.executeUpdate();
        }
        catch (SQLException ser) {
            ser.printStackTrace();
        }
    }

    public void removeRemoveStudentFromCourse(Student s, Course c){
          String sql = """
                  DELETE FROM enrollments WHERE user_id = ? AND course_id = ?
                  """;
          try (PreparedStatement ps = conn.prepareStatement(sql)){
                 ps.setString(1, s.getId());
                 ps.setString(2, c.getCourseCode());
                 ps.executeUpdate();
          } 
          catch (SQLException sq){
            sq.printStackTrace();
          } 
    }

    public void assignTeacherToCourse(Teacher t, Course c){
         String sql = """
                 INSERT INTO teaching (teacher_id, course_id, teaching_type) VALUES (?, ?, ?)
                 """;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, t.getId());
            ps.setString(2, c.getCourseCode());
            ps.setString(3, "TUTOR");
            ps.executeUpdate();
        } 
        catch (SQLException sq){
            sq.printStackTrace();
        }
    }

    public void removeTeacherFromCourse(Teacher t, Course c){
         String sql = """
                 DELETE FROM teaching WHERE teacher_id = ?
                 """;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, t.getId());
        } 
        catch (SQLException sq){
            sq.printStackTrace();
        }
    }

    public void addTeacherToLesson(Teacher t, Lesson l) {
    String sql = """
        UPDATE lessons
        SET teacher = ?
        WHERE lesson_id = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, t.getId());
        ps.setInt(2, l.getLessonId());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to add teacher to lesson.");
    }
}

    public void removeTeacherFromLesson(Teacher t, Lesson l) {
    String sql = """
        UPDATE lessons
        SET teacher = NULL
        WHERE lesson_id = ?
          AND teacher = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, l.getLessonId());
        ps.setString(2, t.getId());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to remove teacher from lesson.");
    }
}

    public Vector<Teacher> getAllTeachers() {
    Vector<Teacher> teachers = new Vector<>();

    String sql = """
        SELECT
            u.user_id,
            u.email,
            u.username,
            u.name,
            u.surname,
            e.employee_id,
            e.salary,
            e.departmenttype,
            t.rank,
            t.h_index
        FROM users u
        JOIN employee e ON e.user_id = u.user_id
        JOIN teachers t ON t.user_id = u.user_id
        ORDER BY u.user_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Teacher teacher = new Teacher(
                    rs.getString("user_id"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("employee_id"),
                    rs.getDouble("salary"),
                    rs.getString("departmenttype"),
                    rs.getString("rank"),
                    rs.getInt("h_index")
            );

            teachers.add(teacher);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get all teachers.");
    }

    return teachers;
}

    public Vector<Manager> getAllManagers() {
    Vector<Manager> managers = new Vector<>();

    String sql = """
        SELECT
            u.user_id,
            u.email,
            u.username,
            u.name,
            u.surname,
            e.employee_id,
            e.salary,
            e.departmenttype,
            m.department
        FROM users u
        JOIN employee e ON e.user_id = u.user_id
        JOIN manager m ON m.user_id = u.user_id
        ORDER BY u.user_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Manager manager = new Manager(
                    rs.getString("user_id"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("employee_id"),
                    rs.getDouble("salary"),
                    rs.getString("departmenttype"),
                    "MANAGER"
            );

            managers.add(manager);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get all managers.");
    }

    return managers;
}

    public Vector<User> getAllResearchers() {
    Vector<User> researchers = new Vector<>();

    String sql = """
        SELECT 
            u.user_id,
            u.username,
            u.email,
            u.profile_type,
            u.name,
            u.surname
        FROM users u
        JOIN researchers r ON u.user_id = r.user_id
        WHERE r.researcher_active = true
        ORDER BY u.user_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            String username = rs.getString("username");
            String id = rs.getString("user_id");
            String email = rs.getString("email");
            String profile = rs.getString("profile_type");
            String name = rs.getString("name");
            String surname = rs.getString("surname");

            researchers.add(new User(id, email, username, name, surname, profile));
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get all researchers.");
    }

    return researchers;
}

public Vector<User> getAllResearchers2() {
    Vector<User> researchers = new Vector<>();

    String sql = """
        SELECT 
            u.user_id,
            u.username,
            u.email,
            u.profile_type,
            u.name,
            u.surname
        FROM users u
        JOIN researchers r ON u.user_id = r.user_id
        WHERE r.researcher_active = true
        ORDER BY u.user_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            String username = rs.getString("username");
            String id = rs.getString("user_id");
            String email = rs.getString("email");
            String profile = rs.getString("profile_type");
            String name = rs.getString("name");
            String surname = rs.getString("surname");

            researchers.add(new User(id, email, username, name, surname, profile));
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get all researchers.");
    }

    return researchers;
}
    public Teacher getTeacherById(String teacherId) {
    String sql = """
        SELECT
            u.user_id,
            u.email,
            u.username,
            u.name,
            u.surname,
            e.employee_id,
            e.salary,
            e.departmenttype,
            t.rank,
            t.h_index
        FROM users u
        JOIN employee e ON e.user_id = u.user_id
        JOIN teachers t ON t.user_id = u.user_id
        WHERE u.user_id = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, teacherId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Teacher(
                        rs.getString("user_id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("employee_id"),
                        rs.getDouble("salary"),
                        rs.getString("departmenttype"),
                        rs.getString("rank"),
                        rs.getInt("h_index")
                );
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get teacher by id.");
    }

    return null;
}

    public Vector<Lesson> getLessonByCourseID(Course c) {
    Vector<Lesson> lessons = new Vector<>();

    String sql = """
        SELECT lesson_id, course_id, lesson_type, time, teacher
        FROM lessons
        WHERE course_id = ?
        ORDER BY time
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, c.getCourseCode());

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Lesson lesson = mapLesson(rs);
                lessons.add(lesson);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get lessons by course id.");
    }

    return lessons;
}
private Lesson mapLesson(ResultSet rs) throws SQLException {
    int lessonId = rs.getInt("lesson_id");
    String type = rs.getString("lesson_type");
    String courseId = rs.getString("course_id");
    String teacherId = rs.getString("teacher");

    Course course = getCourseById(courseId);
    Teacher teacher = null;

    if (teacherId != null) {
        teacher = getTeacherById(teacherId);
    }

    LessonType lessonType = LessonType.valueOf(type.toUpperCase());

    return new Lesson(
            lessonId,
            lessonType,
            course,
            teacher
    );
}

    public Vector<Course> getAllCourses() {
    Vector<Course> courses = new Vector<>();

    String sql = """
        SELECT course_id, course_name, credits
        FROM courses
        ORDER BY course_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Course course = new Course(
                    rs.getString("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("credits")
            );

            courses.add(course);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get all courses.");
    }

    return courses;
}

    public Vector<Course> getStudentsCourses(Student s) {
    Vector<Course> courses = new Vector<>();

    String sql = """
        SELECT course_id, course_name, credits
        FROM student_courses_view
        WHERE student_id = ?
        ORDER BY course_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, s.getId());

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getString("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("credits")
                );

                courses.add(course);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get student's courses.");
    }

    return courses;
}

    public Course getCourseById(String id) {
    String sql = """
        SELECT course_id, course_name, credits 
        FROM courses
        WHERE course_id = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Course(
                        rs.getString("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("credits")
                );
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get course by id.");
    }

    return null;
}

    public Lesson getLessonByID(int id) {
    String sql = """
        SELECT lesson_id, course_id, lesson_type, time, teacher
        FROM lessons
        WHERE lesson_id = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapLesson(rs);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get lesson by id.");
    }

    return null;
}

    public void setAttendance(Student s, Lesson l) {
    String sql = """
        INSERT INTO attendance (student_id, lesson_id, status)
        VALUES (?, ?, ?)
        ON CONFLICT (student_id, lesson_id)
        DO UPDATE SET status = EXCLUDED.status
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, s.getId());
        ps.setInt(2, l.getLessonId());
        ps.setString(3, "PRESENT");

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to set attendance.");
    }
}

    public Vector<String> getAttendancesByCourse(Student s, Course c) {
    Vector<String> attendances = new Vector<>();

    String sql = """
        SELECT lesson_id, lesson_type, time, course_id, course_name, attendance_status
        FROM student_lessons_view
        WHERE student_id = ?
          AND course_id = ?
        ORDER BY time
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, s.getId());
        ps.setString(2, c.getCourseCode());

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String status = rs.getString("attendance_status");

                if (status == null) {
                    status = "NO_DATA";
                }

                attendances.add(
                        "Lesson " + rs.getInt("lesson_id") +
                        " | " + rs.getString("lesson_type") +
                        " | " + rs.getDate("time") +
                        " | " + rs.getString("course_name") +
                        " | attendance: " + status
                );
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get attendances by course.");
    }

    return attendances;
}

    public Vector<String> getMarksByCourseLessons(Student s, Course c) {
    Vector<String> marks = new Vector<>();

    String sql = """
        SELECT lesson_id, lesson_type, time, course_id, course_name, mark
        FROM student_lessons_view
        WHERE student_id = ?
          AND course_id = ?
        ORDER BY time
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, s.getId());
        ps.setString(2, c.getCourseCode());

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String markText;

                double mark = rs.getDouble("mark");

                if (rs.wasNull()) {
                    markText = "NO_MARK";
                } else {
                    markText = String.valueOf(mark);
                }

                marks.add(
                        "Lesson " + rs.getInt("lesson_id") +
                        " | " + rs.getString("lesson_type") +
                        " | " + rs.getDate("time") +
                        " | " + rs.getString("course_name") +
                        " | mark: " + markText
                );
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get lesson marks by course.");
    }

    return marks;
}
    
public Vector<Lesson> getStudentLessonsByCourse(Student s, Course c) {
    Vector<Lesson> lessons = new Vector<>();

    String sql = """
        SELECT
            lesson_id,
            lesson_type,
            teacher,
            time,
            course_id,
            course_name,
            mark,
            attendance_status
        FROM student_lessons_view
        WHERE student_id = ?
          AND course_id = ?
        ORDER BY time
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, s.getId());
        ps.setString(2, c.getCourseCode());

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Lesson l = mapLesson(rs);

                lessons.add(l);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get student's lessons by course.");
    }

    return lessons;
}
  public Vector<ResearchProject> getAllResearchProjects() {
    Vector<ResearchProject> projects = new Vector<>();

    String sql = """
        SELECT project_id, title, budget
        FROM research_projects
        ORDER BY project_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            ResearchProject project = new ResearchProject(
                    rs.getString("project_id"),
                    rs.getString("title"),
                    rs.getDouble("budget")
            );

            projects.add(project);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get all research projects.");
    }

    return projects;
}

private ResearchPaper mapResearchPaper(ResultSet rs) throws SQLException {
    Date sqlDate = rs.getDate("publishing_date");

    LocalDate publishedDate = null;
    if (sqlDate != null) {
        publishedDate = sqlDate.toLocalDate();
    }

    return new ResearchPaper(
            rs.getString("paper_id"),
            rs.getString("author"),
            rs.getString("title"),
            rs.getString("theme"),
            rs.getString("abstractions"),
            rs.getInt("citation_number"),
            rs.getString("doi"),
            rs.getInt("pages"),
            rs.getString("journal"),
            publishedDate
    );
}

  public Vector<ResearchPaper> getAllResearchPaper() {
    Vector<ResearchPaper> papers = new Vector<>();

    String sql = """
        SELECT
            paper_id,
            author,
            title,
            theme,
            abstractions,
            citation_number,
            doi,
            pages,
            journal,
            publishing_date
        FROM research_papers
        ORDER BY citation_number DESC
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            papers.add(mapResearchPaper(rs));
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get all research papers.");
    }

    return papers;
}

  public Vector<ResearchPaper> getPapersByUserId(String userId) {
    Vector<ResearchPaper> papers = new Vector<>();

    String sql = """
        SELECT
            paper_id,
            author,
            title,
            theme,
            abstractions,
            citation_number,
            doi,
            pages,
            journal,
            publishing_date
        FROM research_papers
        WHERE author = ?
        ORDER BY publishing_date DESC
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, userId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                papers.add(mapResearchPaper(rs));
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get papers by user id.");
    }

    return papers;
}

public ResearchPaper getResearchPaperById(String paperId) {
    String sql = """
        SELECT
            paper_id,
            author,
            title,
            theme,
            abstractions,
            citation_number,
            doi,
            pages,
            journal,
            publishing_date
        FROM research_papers
        WHERE paper_id = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, paperId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapResearchPaper(rs);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get research paper by id.");
    }

    return null;
}
public void addResearchPaper(ResearchPaper paper) {
    String sql = """
        INSERT INTO research_papers
        (
            paper_id,
            author,
            title,
            theme,
            abstractions,
            citation_number,
            doi,
            pages,
            journal,
            publishing_date
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, paper.getPaperId());
        ps.setString(2, paper.author_id);
        ps.setString(3, paper.getTitle());
        ps.setString(4, paper.theme);
        ps.setString(5, paper.abstractions);
        ps.setInt(6, paper.getCitations());
        ps.setString(7, paper.getDoi());
        ps.setInt(8, paper.getPages());
        ps.setString(9, paper.getJournal());

        if (paper.getPublishedDate() == null) {
            ps.setNull(10, java.sql.Types.DATE);
        } else {
            ps.setDate(10, Date.valueOf(paper.getPublishedDate()));
        }

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to add research paper.");
    }
}
public boolean removeResearchPaper(String paperId) {
    String sql = """
        DELETE FROM research_papers
        WHERE paper_id = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, paperId);
        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to remove research paper.");
        return false;
    }
}
  public Vector<ResearchProject> getProjectByUserId(String userId) {
    Vector<ResearchProject> projects = new Vector<>();

    String sql = """
        SELECT 
            rp.project_id,
            rp.title,
            rp.budget
        FROM research_project_members rpm
        JOIN research_projects rp ON rp.project_id = rpm.project_id
        WHERE rpm.user_id = ?
        ORDER BY rp.project_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, userId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ResearchProject project = new ResearchProject(
                        rs.getString("project_id"),
                        rs.getString("title"),
                        rs.getDouble("budget")
                );

                projects.add(project);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get projects by user id.");
    }

    return projects;
}
   public void addResearchProjectToDB(ResearchProject r){
       String sql = """
               INSERT INTO research_projects (project_id, title, budget) VALUES (? , ? ,?)
               """;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
             ps.setString(1, r.project_id);
             ps.setString(2, r.getTopic());
             ps.setDouble(3, r.budget);
             
             ps.executeUpdate();
        }
        catch (SQLException sq){
            sq.printStackTrace();
        }
   }

   public void addProjectMemberToDB(String id, String p_id){
         String sql = """
               INSERT INTO research_project_members (project_id, user_id) VALUES (? , ?)
               """;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
             ps.setString(1, p_id);
             ps.setString(2, id);
             ps.executeUpdate();
            }
        catch (SQLException sq){
            sq.printStackTrace();
        }
   }

   public void addMark(Mark m, Student s, Course c) {
    String sql = """
        INSERT INTO marks (
            student_id,
            course_id,
            att1,
            att2,
            midterm,
            endterm,
            final,
            GPA
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        ON CONFLICT (student_id, course_id)
        DO UPDATE SET
            att1 = EXCLUDED.att1,
            att2 = EXCLUDED.att2,
            midterm = EXCLUDED.midterm,
            endterm = EXCLUDED.endterm,
            final = EXCLUDED.final,
            GPA = EXCLUDED.GPA
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, s.getId());
        ps.setString(2, c.getCourseCode());
        ps.setDouble(3, m.getFirstAttestation());
        ps.setDouble(4, m.getSecondAttestation());
        ps.setDouble(5, m.getMidterm());
        ps.setDouble(6, m.getEndterm());
        ps.setDouble(7, m.getFinalExam());
        ps.setDouble(8, m.getGpa());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to add mark.");
    }
}

public Vector<Mark> getMarksByCourse(Course c) {
    Vector<Mark> marks = new Vector<>();

    String sql = """
        SELECT *
        FROM marks
        WHERE course_id = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, c.getCourseCode());

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course course = getCourseById(rs.getString("course_id"));
                Mark mark = new Mark(
                        getStudentByID(rs.getString("student_id")),
                        course,
                        rs.getDouble("midterm"),
                        rs.getDouble("endterm"),
                        rs.getDouble("att1"),
                        rs.getDouble("att2"),
                        rs.getDouble("final")
                );

                marks.add(mark);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get marks by course.");
    }

    return marks;
}

public Student getStudentByID(String id) {
    String sql = """
        SELECT 
            u.user_id,
            u.email,
            u.username,
            u.name,
            u.surname,
            s.year,
            s.GPA,
            s.h_index,
            s.is_researcher,
            s.current_supervisor
        FROM users u
        JOIN student s ON s.user_id = u.user_id
        WHERE u.user_id = ?
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Student student = new Student(
                        rs.getString("user_id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("user_id"),
                        rs.getInt("year")
                );

                return student;
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get student by id.");
    }

    return null;
}

public Vector<Student> getStudentsByCourse(String id) {
    Vector<Student> students = new Vector<>();

    String sql = """
        SELECT
            u.user_id,
            u.email,
            u.username,
            u.name,
            u.surname,
            s.year,
            s.GPA,
            s.h_index,
            s.is_researcher
        FROM enrollments e
        JOIN student s ON s.user_id = e.student_id
        JOIN users u ON u.user_id = s.user_id
        WHERE e.course_id = ?
        ORDER BY u.user_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("user_id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("user_id"),  // studentId
                        rs.getInt("year")
                );

                students.add(student);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get students by course.");
    }

    return students;
}

public Vector<Teacher> getTeachersByCourse(String id) {
    Vector<Teacher> teachers = new Vector<>();

    String sql = """
        SELECT
            u.user_id,
            u.email,
            u.username,
            u.name,
            u.surname,
            e.employee_id,
            e.salary,
            e.departmenttype,
            t.rank,
            t.h_index,
            t.is_researcher,
            tc.teaching_type
        FROM teaching tc
        JOIN teachers t ON t.user_id = tc.teacher_id
        JOIN employee e ON e.user_id = t.user_id
        JOIN users u ON u.user_id = t.user_id
        WHERE tc.course_id = ?
        ORDER BY u.user_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getString("user_id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("employee_id"),
                        rs.getDouble("salary"),
                        rs.getString("departmenttype"),
                        rs.getString("rank"),
                        rs.getInt("h_index")
                );

                teachers.add(teacher);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to get teachers by course.");
    }

    return teachers;
}

public void assignHeadLector(String t, String c) {
    String sql = """
        INSERT INTO seniour_lecturers (user_id, course_id)
        VALUES (?, ?)
        ON CONFLICT (course_id)
        DO UPDATE SET user_id = EXCLUDED.user_id
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, t);
        ps.setString(2, c);

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to assign head lector.");
    }
}

public Vector<Student> getStudentsByLesson(String l_id){
       String sql ="""
               
                SELECT
            u.user_id,
            u.email,
            u.username,
            u.name,
            u.surname,
            s.year,
            s.GPA,
            s.h_index,
            s.is_researcher
        FROM attendance e
        JOIN student s ON s.user_id = e.student_id
        JOIN users u ON u.user_id = s.user_id
        WHERE e.lesson_id = ?
        ORDER BY u.user_id
    """;
    Vector<Student> students = new Vector<>();
    try(PreparedStatement ps = conn.prepareStatement(sql)) {
           ps.setString(1, l_id);
           try(ResultSet rs = ps.executeQuery()){
                  while(rs.next()){
                    students.add(new Student(
                        rs.getString("user_id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("user_id"),  // studentId
                        rs.getInt("year")
                    ));
                        
                  }
                  return students;
           }
           catch (SQLException sq){
              System.out.println(" no lesson");
           }
    }
    catch (SQLException sq){
        sq.printStackTrace();
    }
    return students;
}

}
