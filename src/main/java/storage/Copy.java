/*  private static LessonType chooseLessonType() {
        System.out.println("Choose lesson type:");
        LessonType[] values = LessonType.values();

        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }

        int choice = readInt("Lesson type: ");
        return values[choice - 1];
    } 
    I/WE
    DONT
    NEED
    IT    
    */  
/* 
    public static boolean executeOperation(int choice, UserType switcher, Active user) throws Exception {
    if (choice == 1) {
        return false;
    }
    if (choice == 0)
    {
        running = false;
    }

    if (switcher == UserType.ADMIN || switcher == UserType.MANAGER) {
        return executeAdminManagerOperation(choice, user);
    }

    if (switcher == UserType.TEACHER) {
        return executeTeacherOperation(choice, user);
    }

    if (switcher == UserType.STUDENT) {
        return executeStudentOperation(choice, user);
    }

    if (switcher == UserType.RESEARCHER) {
        return executeResearcherOperation(choice, user);
    }

    System.out.println("Unknown user type.");
    return true;
}




private static boolean executeTeacherOperation(int choice, Active user) throws Exception {
    switch (choice) {
        case 2:
            putMark();
            break;

        case 3:
            printMarksReport();
            break;

        case 4:
            printAllCourses();
            break;

        case 5:
            becomeResearcher(user);
            break;

        case 6:
            joinResearchProject();
            break;

        case 7:
            createResearchProject(user);
            break;

        case 8:
            createResearchPaper();
            break;
        case 9:
            makeRequest(user);
        case 10:
            viewOwnRequests(user);
            break;
        case 11:
            viewReceivedRequests(user);
            break;

        default:
            System.out.println("It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}





private static boolean executeAdminManagerOperation(int choice, Active user) throws Exception {
    switch (choice) {
        case 2:
            createManager();
            break;

        case 3:
            createAdmin();
            break;

        case 4:
            createTeacher();
            break;

        case 5:
            createStudent();
            break;

        case 6:
            createFullTimeResearcher();
            break;

        case 7:
            createCourse();
            break;

        case 8:
            assignHeadLector();
            break;

        case 9:
            assignTeacherToCourse();
            break;

        case 10:
            registerStudentToCourse();
            break;

        case 11:
            createLesson();
            break;

        case 12:
            assignSupervisor();
            break;

        case 13:
            printAllUsers();
            break;

        case 14:
            printAllCourses();
            break;

        case 15:
            printAllPapers();
            break;

        case 16:
            printTopCitedResearcher();
            break;

        case 17:
            createResearchProject(user);
            break;
        case 18:
            makeRequest(user);
            break;
        case 19:
            viewOwnRequests(user);
            break;
        case 20:
            viewReceivedRequests(user);
            break;
        case 0:
            System.out.println("Goodbye");
        default:
            System.out.println(" It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}

private static boolean executeStudentOperation(int choice, Active user) throws Exception {
    switch (choice) {
        case 2:
            printAllCourses();
            break;

        case 3:
            signUpForCourse(user);
            break;

        case 4:
            printMarks(user);
            break;

        case 5:
            printYourClasses(user);
            break;

        case 6:
            printLessons(user);
            break;
        case 7:
            becomeResearcher(user);
            break;

        case 8:
            joinResearchProject();
            break;

        case 9:
            createResearchProject(user);
            break;

        case 10:
            createResearchPaper();
            break;

        default:
            System.out.println("It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}

private static boolean executeResearcherOperation(int choice, Active user) throws Exception {
    switch (choice) {
        case 2:
            becomeResearcher(user);
            break;
        case 3: 
            joinResearchProject();
            break;
        case 4:
            createResearchProject(user);
            break;

        case 5:
            createResearchPaper();
            break;
        case 6:
            makeRequest(user);
        case 7:
            viewOwnRequests(user);
            break;
        case 8:
            viewReceivedRequests(user);
            break;

        default:
            System.out.println("It was wrong option.");
    }
    String t = scanner.nextLine();
    return true;
}

}
*/