package controllers;
import Helpers.InputValidator;
import entity.Client;
import services.ClientServices;
import java.util.Scanner;
public class ClientController {
    final ClientServices clientServices = new ClientServices();
    private Scanner scanner;
    private Client client = new Client();
    private ProjectController projectController = new ProjectController(client);
    public ClientController(Scanner scanner) {
        this.scanner = scanner;
    }
    public void addClient() {
        try {
            InputValidator inputValidator = new InputValidator(scanner);
            boolean clientExists = false;
            String name = inputValidator.validateString("Please enter the client name:");
            client = clientServices.find(name);
                if (client != null) {
                    System.out.println("This client already exists!");
                    String answer =inputValidator.ValidatePrompt("Do you wish to continue with this client or enter a new one? (y/n)");
                    if (answer.equals("y")) {
                        projectController.createProject(scanner, client);
                        return;
                    } else if (answer.equals("n")) {
                       addClient();
                       return;
                    }
                }
            String address = inputValidator.validateAddress("Please enter the client address: ");
            String phone = inputValidator.phoneValidator("Please enter the client phone:");
            boolean isProfessional = askProfessionalStatus();
            Client client = new Client();
            client.setName(name);
            client.setAddress(address);
            client.setPhone(phone);
            client.setProfessional(isProfessional);
            if (clientServices.createClient(client)) {
                System.out.println("Client added successfully!\n");
                String answer =inputValidator.ValidatePrompt("Do you wish to continue with this client? y/n");
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
    private boolean askProfessionalStatus() {
        boolean professional;
        InputValidator inputValidator = new InputValidator(scanner);
        while (true) {
            String answer =inputValidator.ValidatePrompt("Is the client a professional? (y/n):") ;
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
    public void find() {
        try {
            InputValidator inputValidator = new InputValidator(scanner);
            String name = inputValidator.validateString("Please enter the client name: ");
            client =  clientServices.find(name);
            if (client == null) {
                String answer = inputValidator.ValidatePrompt("Do you wish to continue with this client? y/n");
                if (answer.equals("y")) {
                    addClient();
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
                String answer =inputValidator.ValidatePrompt("Do you wish to continue with this client? y/n");
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
