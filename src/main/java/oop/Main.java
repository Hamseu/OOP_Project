package oop;
import java.util.Scanner;

import database.UDBM;
import storage.ManualDataStorage;
import system.Active;
import system.Authenticate;
import system.OperationalManager;

public class Main {

    public static void main(String[] args) {
        UDBM db = new UDBM();
        boolean loginer = Authenticate.consolewin(db);
        Scanner nerr = new Scanner(System.in);
        while (true) {
            
             if (loginer){
                Active actuser = ManualDataStorage.loadSession();
                OperationalManager op = OperationalManager.getInstance(actuser.profile);
                System.out.println("Hello, " + actuser.username + "Welcome to University system, please, choose an action available for you: ");
                op.operationFrame();
                int ch = nerr.nextInt();
             }
             else{
                loginer = Authenticate.consolewin(db);
             }
        } 
    }
}