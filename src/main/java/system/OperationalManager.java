package system;

public class OperationalManager{
       public UserType switcher;

       public OperationalManager(){

       }

       public void setType(UserType a){
          this.switcher = a;
       }

       public UserType getSwitch(){
        return this.switcher;
       }

       public static OperationalManager getInstance(UserType a){
            OperationalManager inst = new OperationalManager();
            inst.setType(a);
            return inst;
       }

       public void operationFrame() {
    int counter = 0;

    System.out.println("Please, select the command");

    counter++;
    System.out.println(counter + ". Exit");

    if (switcher == UserType.ADMIN || switcher == UserType.MANAGER) {
        counter++;
        System.out.println(counter + ". Register new manager");

        counter++;
        System.out.println(counter + ". Register new admin");

        counter++;
        System.out.println(counter + ". Register new teacher");

        counter++;
        System.out.println(counter + ". Register new student");

        counter++;
        System.out.println(counter + ". Register new researcher");

        counter++;
        System.out.println(counter + ". Register new course");

        counter++;
        System.out.println(counter + ". Assign head lector");

        counter++;
        System.out.println(counter + ". Assign teacher to course");

        counter++;
        System.out.println(counter + ". Register student to course");

        counter++;
        System.out.println(counter + ". Create lesson");

        counter++;
        System.out.println(counter + ". Assign supervisor");

        counter++;
        System.out.println(counter + ". Print all users");

        counter++;
        System.out.println(counter + ". Print all courses");

        counter++;
        System.out.println(counter + ". Print all papers");

        counter++;
        System.out.println(counter + ". Print top cited researcher");
        counter++;
        System.out.println(counter + ". Create research project");
        counter++;
        System.out.println(counter + ". Make request");
        counter++;
        System.out.println(counter + ". Get own requests");
        counter++;
        System.out.println(counter + ". Get received requests");
        System.out.println("0. Close console");
    }

    if (switcher == UserType.TEACHER) {
        counter++;
        System.out.println(counter + ". Put mark");

        counter++;
        System.out.println(counter + ". Print marks report");

        counter++;
        System.out.println(counter + ". Print all courses");
    }

    if (switcher == UserType.STUDENT) {
        counter++;
        System.out.println(counter + ". Print all courses");
        counter++;
        System.out.println(counter + ". Sign up for a course");
        counter++;
        System.out.println(counter + ". Print marks");
        counter++; 
        System.out.println(counter + ". Print all courses");
        counter++;
        System.out.println(counter + ". Print your classes");
        counter++;
        System.out.println(counter + ". Print lessons");
    }

    if (switcher == UserType.RESEARCHER || switcher == UserType.STUDENT || switcher == UserType.TEACHER){
        counter++;
        System.out.println(counter + ". Become Researcher");
        counter++;
        System.out.println(counter + ". Join research project");
        counter++;
        System.out.println(counter + ". Start research project");
        counter++;
        System.out.println(counter + ". Create research paper");
        if (switcher == UserType.RESEARCHER || switcher == UserType.TEACHER){
        counter++;
        System.out.println(counter + ". Make request");
        counter++;
        System.out.println(counter + ". Get own requests");
        counter++;
        System.out.println(counter + ". Get received requests");
    }
        System.out.println("0. Close console");
    }

    
}

}