package controllers;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuController {
    private Scanner scanner;

    public MenuController() {
        this.scanner = new Scanner(System.in);
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            try {
                System.out.println("-----Welcome to Bati-cuisine-----\n1. Create a new project\n" +
                        "2. Show existing projects\n3. Calculate project cost\n4. Quit");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        createProject();
                        break;
                    case 2:
                        // Logic for showing existing projects
                        break;
                    case 3:
                        // Logic for calculating project cost
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (RuntimeException e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear the scanner buffer
            }
        }
    }

    private void createProject() {
        try {
            System.out.println("Do you wish to search for an existing client or add a new project?\n1. Search for an existing client\n2. Add a new client\n0. Return to main menu");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer after reading an integer
            switch (choice) {
                case 1:
                    searchExistingClient();
                    break;
                case 2:
                    ClientController clientController = new ClientController();
                    clientController.addClient(scanner);
                case 0:
                    return; // Go back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear the invalid input
        } catch (RuntimeException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void searchExistingClient() {
        // Implement the logic for searching existing clients here
        System.out.println("Searching for existing clients...");
        // ...
    }

}
