package services;

import java.util.Scanner;

public class ReadService {
    public ReadService(){

    }

    public static ReadService getInstance(){
        return new ReadService();
    }
    public String readLine(String message, Scanner scanner) {
        System.out.print(message);
        return scanner.nextLine();
    }
    public  int readInt(String message, Scanner scanner) {
    while (true) {
        try {
            System.out.print(message);
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter integer number.");
        }
    }
}
    public double readDouble(String message, Scanner scanner) {
        while (true) {
            try {
                System.out.print(message);
                double value = Double.parseDouble(scanner.nextLine());
                System.out.println();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter number.");
            }
        }
    }
}
