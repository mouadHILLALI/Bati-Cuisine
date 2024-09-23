package controllers;

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
            LabourerController labourerController = new LabourerController(client);
            System.out.println("Do you wish to add material to the project " + project.getProjectName() + "? (y/n)");
            String answer = scanner.nextLine().toLowerCase();
            List<Material> materials = new ArrayList<>();
            if (answer.equals("y")) {
                boolean continueAdding = true;
                while (continueAdding) {
                    System.out.println("Enter material name:");
                    String materialName = scanner.nextLine();

                    System.out.println("Enter material unit cost:");
                    double unitCost = scanner.nextDouble();

                    System.out.println("Enter material quantity:");
                    double quantity = scanner.nextDouble();

                    System.out.println("Enter material transportation cost:");
                    double transportCost = scanner.nextDouble();

                    System.out.println("Enter material quality coefficient (1.0 = standard, > 1.0 = high quality):");
                    double qualityCoeff = scanner.nextDouble();
                    // Clear the buffer after nextDouble()
                    scanner.nextLine();
                    // Create material and add to the list
                    Material material = new Material(materialName, unitCost, quantity,  transportCost, qualityCoeff);
                    materials.add(material);
                    System.out.println("Do you wish to add another material? (y/n)");
                    answer = scanner.nextLine().toLowerCase();
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
            scanner.nextLine(); // Clear the scanner buffer after invalid input
        }
    }

}
