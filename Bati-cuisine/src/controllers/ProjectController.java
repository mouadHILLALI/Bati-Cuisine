package controllers;
import Helpers.InputValidator;
import entity.Client;
import entity.Devis;
import entity.Project;
import enums.ProjectStatusEnum;
import services.ProjectServices;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ProjectController {
    private Client client ;
    final ProjectServices projectService = new ProjectServices();
    public ProjectController(Client client) {
        this.client = client;
    }
    public ProjectController(){

    }
    public void createProject(Scanner sc , Client client) {
        try {
            InputValidator inputValidator = new InputValidator(sc);
            ProjectStatusEnum projectStatus = ProjectStatusEnum.PENDING;
            String name = inputValidator.validateString("Enter the name of the project:");
            double surface = inputValidator.ValidateDouble("Enter the " + name + " surface (m²):");
            Project project = new Project(name, projectStatus, surface);
            boolean flag = projectService.createProject(project , client.getId());
            if (flag) {
                System.out.println("Project created successfully.");
                sc.nextLine();
                MaterialController materialController = new MaterialController(client);
                materialController.addMaterial(sc , project);
            } else {
                System.out.println("Project not created.");
            }
        } catch (RuntimeException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    public void continueProject(Scanner sc , Project project , Client client) {
        final DevisController devisController = new DevisController(project, client , sc);
        InputValidator inputValidator = new InputValidator(sc);
        try {
            System.out.println("Calculating total cost\n");
            String answer = inputValidator.ValidatePrompt("do you wish to add Tax to the project?(y/n)");
            if (answer.equals("y")) {
                double tax = inputValidator.ValidateDouble("Enter the tax rate:");
                project.setTaxRate(tax);
                sc.nextLine();
            }
            String ans = inputValidator.ValidatePrompt("do you wish to add a beneficial margin to the project?(y/n)");
            if (ans.equals("y")) {
                double beneficialMargin = inputValidator.ValidateDouble("Enter the beneficial margin:");
                sc.nextLine();
                project.setBeneficialMargin(beneficialMargin);
            }
            boolean flag = projectService.updateProject(project);
            if (flag){
                System.out.println("project updated successfully");
                devisController.displayDevis();
            }
            System.out.println("Calculating in progress...\n");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void displayProjects(Scanner sc){
        try {
            System.out.println("Show existing projects:\n1.projects with devis\n2.show all projects with associated client\n");
            int userChoice = sc.nextInt();
            do {
                switch (userChoice){
                    case 1:
                        displayProjectsWithDevis();
                        break;
                    case 2:
                        displayProjects();
                        break;
                }
            }while (userChoice < 1 || userChoice > 2);
        }catch (RuntimeException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    public void displayProjectsWithDevis() {
        try {
            HashMap<Integer, Project> projects = projectService.getAllWithDevis();
            System.out.printf("%-10s %-20s %-15s %-15s %-20s %-25s %-15s %-20s %-18s %-15s %-15s %-15s\n",
                    "Project ID", "Project Name", "Surface", "Status", "Client Name", "Client Address",
                    "Client Phone", "Is Professional", "Estimated Amount", "Emission Date", "Expiration Date", "Is Accepted");

            projects.forEach((projectID, project) -> {
                Client client = project.getClient();
                Devis devis = project.getDevis();
                System.out.printf("%-10d %-20s %-15.2f %-15s %-20s %-25s %-15s %-20s %-18.2f %-15s %-15s %-15s\n",
                        projectID,
                        project.getProjectName(),
                        project.getSurface(),
                        project.getStatus().toString(),
                        client.getName(),
                        client.getAddress(),
                        client.getPhone(),
                        client.isProfessional() ? "Yes" : "No",
                        devis.getEstimatedAmount(),
                        devis.getEmissionDate().toString(),
                        devis.getExpirationDate().toString(),
                        devis.isAccepted() ? "Yes" : "No"
                );
            });
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void displayProjects() {
        try {
            List<Project> projects = projectService.getAllProject();
            System.out.printf("%-10s %-20s %-15s %-15s %-20s %-25s %-15s %-15s\n",
                    "Project ID", "Project Name", "Surface", "Status", "Client Name", "Client Address",
                    "Client Phone", "Is Professional");
            projects.forEach((project) -> {
                Client client = project.getClient();
                System.out.printf("%-10d %-20s %-15.2f %-15s %-20s %-25s %-15s %-15s\n",
                        project.getId(),
                        project.getProjectName(),
                        project.getSurface(),
                        project.getStatus().toString(),
                        client.getName(),
                        client.getAddress(),
                        client.getPhone(),
                        client.isProfessional() ? "Yes" : "No"
                );
            });
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
