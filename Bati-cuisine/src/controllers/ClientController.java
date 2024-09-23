package controllers;

import entity.Client;
import services.ClientServices;

import java.util.Scanner;

public class ClientController {
    final  ClientServices clientServices = new ClientServices();
    private Client client = new Client();
    private ProjectController projectController = new ProjectController(client);
    public void addClient(Scanner scanner) {
        try {
            boolean clientExists = false;
            Client client = new Client();
                System.out.println("Please enter the client name: ");
                String name = scanner.nextLine().trim().toLowerCase();

                client = clientServices.find(name);
                if (client != null) {
                    System.out.println("This client already exists!");
                    System.out.println("Do you wish to continue with this client or enter a new one? (y/n)");
                    String answer = scanner.nextLine().trim().toLowerCase();
                    if (answer.equals("y")) {
                        projectController.createProject(scanner, client);
                        return;
                    } else if (answer.equals("n")) {
                       addClient(scanner);
                    }
                }
            System.out.println("Please enter the client address: ");
            String address = scanner.nextLine().trim().toLowerCase();
            System.out.println("Please enter the client phone: ");
            String phone = scanner.nextLine().trim().toLowerCase();
            System.out.println("Is the client a professional? (y/n): ");
            boolean isProfessional = askProfessionalStatus(scanner);
            client = new Client();
            client.setName(name);
            client.setAddress(address);
            client.setPhone(phone);
            client.setProfessional(isProfessional);
            if (clientServices.createClient(client)) {
                System.out.println("Client added successfully!\n");
                System.out.println("Do you wish to continue with this client? (y/n)\n");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("y")) {
                    projectController.createProject(scanner, client);
                } else {
                    System.out.println("Returning to main menu...");
                }
            } else {
                System.out.println("Client couldn't be added!");
            }
        } catch (RuntimeException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private boolean askProfessionalStatus(Scanner scanner) {
        boolean professional;
        while (true) {
            String answer = scanner.nextLine().trim().toLowerCase();
            switch (answer) {
                case "y":
                    professional = true;
                    return professional;
                case "n":
                    professional = false;
                    return professional;
                default:
                    System.out.println("Please enter 'y' for Yes or 'n' for No.");
            }

        }
    }

    public void find(Scanner scanner) {
        try {
            System.out.println("Please enter the client name: ");
            String name = scanner.nextLine().toLowerCase();
            client =  clientServices.find(name);
            if (client == null) {
                System.out.println("Client not found!\nDo you wish to add this client ? (y/n)\n");
                String answer = scanner.nextLine().toLowerCase();
                if (answer.equals("y")) {
                    addClient(scanner);
                }else if (answer.equals("n")) {
                    System.out.println("Returning to main menu...");
                }
            }else {
                System.out.println("Client found!");
                System.out.println(String.format("%-10s %-20s %-30s %-15s %-15s",
                        "Client ID", "Client Name", "Client Address", "Client Phone", "Professional"));

                System.out.println(String.format("%-10d %-20s %-30s %-15s %-15s",
                        client.getId(),
                        client.getName(),
                        client.getAddress(),
                        client.getPhone(),
                        client.isProfessional() ? "Yes" : "No"));
                System.out.println("Do you wish to add a project to this client? (y/n)\n");
                String answer = scanner.nextLine().toLowerCase();
                if (answer.equals("y")) {
                    projectController.createProject(scanner , client);
                }else if (answer.equals("n")) {
                    System.out.println("Returning to main menu...");
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
