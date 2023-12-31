package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.UserCredentials;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private PrintWriter out;
    private Scanner in;

    public ConsoleService(PrintWriter out, Scanner in) {
        this.out = out;
        this.in = in;
    }

    public ConsoleService() {
    }

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }
    public Integer getUserInputInteger(String prompt) {
        Integer result = null;
        do {
            out.print(prompt+": ");
            out.flush();
            String userInput = in.nextLine();
            try {
                result = Integer.parseInt(userInput);
            } catch(NumberFormatException e) {
                out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
            }
        } while(result == null);
        return result;
    }
    public String printCurrentBalance() {
        return "Your current account balance is: $";
    }
    public void printViewTransferHistory1() {
        System.out.println("-------------------------------------------\n" + "TRANSFER HISTORY:\n" + "\n" +
                "TRANSFER ID      FROM/TO        AMOUNT\n-------------------------------------------");

    }
    public void printViewTransferHistory2(int id, String user, BigDecimal amount) {
        System.out.println(id + "          From: " + user +
                "       $" + amount + "\n");
    }

    public void printViewTransferHistory2a(int id, String user, BigDecimal amount) {
        System.out.println(id + "          To: " + user +
                "       $" + amount + "\n");
    }
    public void printViewTransferHistory3(int id, String user1, String user2, BigDecimal amount) {
        System.out.println("\nID: " + id + "\nFrom User: " + user1 + "\nTo User: " + user2 +
                "\nTransfer Status: Approved" + "\nFor the Amount of: $" + amount);
    }

    public void printSendBuk() {
        System.out.println("-------------------------------------------\n" + "Users\n" + "ID    Name\n" + "-------------------------------------------\n");
    }

    public void printSendBuk2() {
        System.out.println("Returning to Main Menu");
    }

    public void printSendBuk3() {
        System.out.println("Not a valid User ID!");

    }

    public void printSendBuk4() {
        System.out.println("NOT ENOUGH MONEY!");
    }
    public String getUserInput(String prompt) {
        out.print(prompt+": ");
        out.flush();
        return in.nextLine();
    }
}