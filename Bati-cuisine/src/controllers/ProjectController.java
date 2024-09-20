package controllers;

import entity.Client;
import entity.Project;
import enums.ProjectStatusEnum;
import repository.Project.ProjectRepositoryImpl;
import services.ProjectServices;
import java.util.Scanner;

public class ProjectController {
    final ProjectServices projectService = new ProjectServices();
    private final Client client ;
    public ProjectController(Client client) {
        this.client = client;
    }
    public void createProject(Scanner sc) {
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
        final DevisController devisController = new DevisController(project, client);
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
//    public void updateProject(Scanner sc , boolean isExisiting) {
//        try {
//            if (isExisiting) {
//                System.out.println("Enter the name of the project:");
//                String name = sc.nextLine();
//                System.out.println("Enter the Surface :");
//                double surface = sc.nextDouble();
//
//            }
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
