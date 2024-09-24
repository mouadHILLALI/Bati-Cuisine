package controllers;

import Helpers.InputValidator;
import entity.Client;
import entity.Material;
import entity.Project;
import repository.Material.MaterialRepositoryImpl;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MaterialController {
    private final Client client;
    public MaterialController(Client client) {
        this.client = client;
    }
    public void addMaterial(Scanner scanner, Project project) {
        try {
            InputValidator inputValidator = new InputValidator(scanner);
            LabourerController labourerController = new LabourerController(client);
            String answer = inputValidator.ValidatePrompt("Do you wish to add material to the project " + project.getProjectName() + "? (y/n)");
            List<Material> materials = new ArrayList<>();
            if (answer.equals("y")) {
                boolean continueAdding = true;
                while (continueAdding) {
                    String materialName = inputValidator.validateString("Enter material name: ");
                    double unitCost = inputValidator.ValidateDouble("Enter material unit cost: ");
                    double quantity = inputValidator.ValidateDouble("Enter material quantity: ");
                    double transportCost = inputValidator.ValidateDouble("Enter material transport cost: ");
                    double qualityCoeff = inputValidator.ValidateDouble("Enter material quality coefficient (1.0 = standard, > 1.0 = high quality): ");
                    scanner.nextLine();
                    Material material = new Material(materialName, unitCost, quantity,  transportCost, qualityCoeff);
                    materials.add(material);
                    answer =inputValidator.ValidatePrompt("Do you wish to add another material? (y/n)");
                    if (!answer.equals("y")) {
                        continueAdding = false;
                    }
                }
                MaterialRepositoryImpl materialRepository = new MaterialRepositoryImpl();
                boolean check = materialRepository.createMaterials(materials, project.getId());
                if (check) {
                    System.out.println("Materials added successfully.");
                    labourerController.addLabourer(scanner, project);
                } else {
                    System.out.println("Failed to add materials.");
                }
            }else {
                labourerController.addLabourer(scanner, project);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input: " + e.getMessage());
            scanner.nextLine();
        }
    }
}
