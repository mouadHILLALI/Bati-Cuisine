package controllers;

import entity.Client;
import services.ClientServices;

import java.util.Scanner;

public class ClientController {
    public void addClient(Scanner scanner) {
        try {
            System.out.println("Please enter the client name: ");
            String name = scanner.nextLine().trim().toLowerCase();
            System.out.println("Please enter the client address: ");
            String address = scanner.nextLine().trim().toLowerCase();
            System.out.println("Please enter the client phone: ");
            String phone = scanner.nextLine().trim().toLowerCase();
            System.out.println("Is the client a professional? (y/n): ");
            String professional;
            boolean isProfessional = false;
            do {
                professional = scanner.nextLine().toLowerCase();
                switch (professional) {
                    case "y":
                        isProfessional = true;
                        break;
                    case "n":
                        isProfessional = false;
                        break;
                    default:
                        System.out.println("Please enter 'y' for Yes or 'n' for No.");
                }
            } while (!professional.equals("y") && !professional.equals("n"));

            Client client = new Client(name, address, phone, isProfessional);
            ClientServices clientServices = new ClientServices();
            boolean flag = clientServices.createClient(client);
            if (flag) {
                System.out.println("Client added successfully!\n");
                System.out.println("Do you wish to continue with this client? (y/n)\n");
                String answer = scanner.nextLine().toLowerCase();
                System.out.println(answer);
                if (answer.equals("y")) {
                    ProjectController projectController = new ProjectController();
                    projectController.createProject(scanner,client);
                } else if (answer.equals("n")) {
                    System.out.println("Returning to main menu...");
                }
            } else {
                System.out.println("Client couldn't be added!");
                addClient(scanner);
            }
        } catch (RuntimeException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
