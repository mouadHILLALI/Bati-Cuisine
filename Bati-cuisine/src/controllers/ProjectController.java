package controllers;

import entity.Client;
import entity.Project;
import enums.ProjectStatusEnum;
import repository.Project.ProjectRepositoryImpl;
import services.ProjectServices;

import java.util.Scanner;

public class ProjectController {

    public void createProject(Scanner sc , Client client) {
        try {
            System.out.println(client.getId());
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
            ProjectServices projectService = new ProjectServices();
            boolean flag = projectService.createProject(project , client.getId());
            if (flag) {
                System.out.println("Project created successfully.");
                sc.nextLine();
                MaterialController materialController = new MaterialController();
                materialController.addMaterial(sc , project);
            } else {
                System.out.println("Project not created.");
            }
        } catch (RuntimeException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
