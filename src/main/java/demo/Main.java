package demo;

import java.util.Scanner;

import database.UDBM;
import services.ReadService;
import storage.ManualDataStorage;
import system.Active;
import system.Authenticate;
import system.OperationalManager;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final UDBM db = new UDBM();
    private static final ReadService RS = ReadService.getInstance();
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" Research-Oriented University System");
        System.out.println(" Console Demo");
        System.out.println("========================================");

         
        
        OperationalManager op = OperationalManager.getInstance(db);
        boolean loginer = Authenticate.consolewin(db, scanner);
        
        while (op.running) {
            
            if (loginer){
                Active actuser = ManualDataStorage.loadSession();
                op.setType(actuser.profile);
                System.out.println("Hello, " + actuser.username + " Welcome to University system, please, choose an action available for you: ");
                op.operationFrame(); 
                int choice = RS.readInt("Enter your command: ", scanner);  
                if (choice != 1){
                try {
                    op.executeOperation(choice, actuser.profile, actuser, scanner);
                }
                catch (Exception e) {
                     System.out.println(e.getMessage()); //TODO, Make More exceptions
                }
            }
            else{
                loginer = false;
            }
        }
                
             else{
                loginer = Authenticate.consolewin(db, scanner);
             }

            System.out.println();
        }
    }
}

    
    

    

    

    


    






   

    
 