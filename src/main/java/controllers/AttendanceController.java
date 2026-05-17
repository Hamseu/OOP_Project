package controllers;

import java.util.Scanner;

import services.AttendanceService;
import system.Active;

public class AttendanceController {
    private final AttendanceService attendanceService = new AttendanceService();

    public AttendanceController() {
    }

    public void manageAttendance(Active teacher, Scanner scanner) {
        attendanceService.manageAttendance(teacher, scanner);
    }
}
