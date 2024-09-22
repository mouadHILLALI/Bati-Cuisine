package controllers;
import entity.Client;
import entity.Project;
import enums.ProjectStatusEnum;
import services.ProjectServices;
import java.util.Scanner;

public class ProjectController {
    private Client client ;
    final ProjectServices projectService = new ProjectServices();
    public ProjectController(Client client) {
        this.client = client;
    }
    public ProjectController(){

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
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }while (userChoice < 1 || userChoice > 3);
        }catch (RuntimeException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
