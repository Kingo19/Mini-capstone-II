package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.UserService;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final UserService userService = new UserService(API_BASE_URL);
    private AccountService accountService = new AccountService();
    private AuthenticatedUser currentUser;
    PrintWriter out = new PrintWriter(System.out);
    Scanner in = new Scanner(System.in);
    ConsoleService console = new ConsoleService(out, in);


    public static void main(String[] args) {
        App app = new App();
        app.run();

    }


    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        BigDecimal balance = accountService.getBalance(currentUser.getToken());
        System.out.println("Your current account balance is: $" + balance + "\n");

    }

    private void viewTransferHistory() {
        System.out.println("-------------------------------------------\n" + "TRANSFER HISTORY:\n" + "\n" +
                "TRANSFER ID      FROM/TO        AMOUNT\n-------------------------------------------");
        Transfer[] transferList = accountService.getHistory(currentUser.getToken(), currentUser.getUser().getId());
        for (Transfer transfer : transferList) {

            if (transfer.isWasSentToUs()) {
                System.out.println(transfer.getTransferId() + "          FROM: " + transfer.getOtherUser() +
                        "       $" + transfer.getAmount() + "\n");
            } else {
                System.out.println(transfer.getTransferId() + "          TO:   " + transfer.getOtherUser() +
                        "       $" + transfer.getAmount() + "\n");
            }
        }

        int transferId = console.getUserInputInteger("Enter the transfer ID for the transfer you want to view in detail");

        for (Transfer requestedTransfer : transferList) {
            if (requestedTransfer.getTransferId() == transferId && requestedTransfer.isWasSentToUs()) {
                Transfer transferById = accountService.historyByTransferId(currentUser.getToken(), currentUser.getUser().getId(), transferId);
                System.out.println("\nID: " + transferById.getTransferId() + "\nFrom User: " + transferById.getOtherUser() + "\nTo User: " + currentUser.getUser().getUsername() +
                        "\nTransfer status: Approved" + "\nFor the Amount of: $" + transferById.getAmount());
                break;

            } else if (requestedTransfer.getTransferId() == transferId && !requestedTransfer.isWasSentToUs()) {
                Transfer transferById = accountService.historyByTransferId(currentUser.getToken(), currentUser.getUser().getId(), transferId);
                System.out.println("\nID: " + transferById.getTransferId() + "\nFrom User: " + currentUser.getUser().getUsername() + "\nTo User: " + transferById.getOtherUser() +
                        "\nTransfer Status: Approved" + "\nFor the Amount of: $" + transferById.getAmount());
                break;
            }
        }

    }


    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {
        // TODO Auto-generated method stub

    }

    private void requestBucks() {
        // TODO Auto-generated method stub

    }

}
