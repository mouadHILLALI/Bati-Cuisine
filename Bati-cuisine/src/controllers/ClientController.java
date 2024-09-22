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
              client.setName(name);
              client.setAddress(address);
              client.setPhone(phone);
              client.setProfessional(isProfessional);

            boolean flag = clientServices.createClient(client);
            if (flag) {
                System.out.println("Client added successfully!\n");
                System.out.println("Do you wish to continue with this client? (y/n)\n");
                String answer = scanner.nextLine().toLowerCase();
                System.out.println(answer);
                if (answer.equals("y")) {
                    projectController.createProject(scanner ,client);
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
                System.out.println("Client ID\t Client Name\t Client Address\t Client Phone\t Client Professional");
                System.out.println(client.getId() + client.getName() + client.getAddress() + client.getPhone() + (client.isProfessional()? "Yes" : "No" ));
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
