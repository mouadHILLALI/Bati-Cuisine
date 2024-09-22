package controllers;
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
            System.out.println(client.getName()+client.getPhone());
            ProjectStatusEnum projectStatus = ProjectStatusEnum.PENDING;
            System.out.println("Enter the name of the project:");
            String name = sc.nextLine();
            System.out.println("Enter the " + name + " surface (m²):");
            double surface = sc.nextDouble();
            int status;
            do {
                System.out.println("Enter the " + name + " project status:\n1. Pending\n2. Canceled\n3. Finished");
                status = sc.nextInt();
                switch (status) {
                    case 1:
                        projectStatus = ProjectStatusEnum.PENDING;
                        break;
                    case 2:
                        projectStatus = ProjectStatusEnum.CANCELLED;
                        break;
                    case 3:
                        projectStatus = ProjectStatusEnum.FINISHED;
                        break;
                    default:
                        System.out.println("Invalid status. Please enter 1, 2, or 3.");
                }
            } while (status < 1 || status > 3);
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
        try {
            System.out.println("Calculating total cost\n");
            System.out.println("do you wish to add Tax to the project?(y/n)\n");
            if (sc.nextLine().toLowerCase().equals("y")) {
                System.out.println("Enter the tax rate:\n");
                double tax = sc.nextDouble();
                project.setTaxRate(tax);
                sc.nextLine();
            }
            System.out.println("do you wish to add a beneficial margin to the project?(y/n)\n");
            if (sc.nextLine().toLowerCase().equals("y")) {
                System.out.println("Enter the beneficial margin:\n");
                double beneficialMargin = sc.nextDouble();
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
                // Retrieve client and devis information
                Client client = project.getClient();
                Devis devis = project.getDevis();

                // Print the project details in a formatted way with aligned columns
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
