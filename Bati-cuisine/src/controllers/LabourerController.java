package controllers;
import entity.Client;
import entity.Labourer;
import entity.Project;
import repository.Labourer.LabourerRepositoryImpl;
import repository.Material.MaterialRepositoryImpl;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LabourerController {
    private Client client ;
    public LabourerController(Client client) {
        this.client = client;
    }
    final ProjectController projectController = new ProjectController(client);
    public void addLabourer(Scanner sc, Project project) {
        try {
            System.out.println("Do you wish to add labourers to the project " + project.getProjectName() + "? (y/n)");
            String answer = sc.nextLine().toLowerCase();
            List<Labourer> labourers = new ArrayList<>();
            if (answer.equals("y")) {
                boolean continueAdding = true;
                while (continueAdding) {
                    System.out.println("Enter labourer speciality:");
                    String speciality = sc.nextLine();
                    System.out.println("Enter labourer hourly rate:");
                    double hourlyRate = sc.nextDouble();
                    System.out.println("Enter labourer total hours:");
                    double totalHours = sc.nextDouble();
                    System.out.println("Enter labourer productivity coefficient (1.0 = standard, > 1.0 = high quality):");
                    double productivityCoeff = sc.nextDouble();
                    sc.nextLine();
                    Labourer labourer = new Labourer(speciality, hourlyRate, totalHours,  productivityCoeff);
                    labourers.add(labourer);
                    System.out.println("Do you wish to add another labourer? (y/n)");
                    answer = sc.nextLine().toLowerCase();
                    if (!answer.equals("y")) {
                        continueAdding = false;
                    }
                }
                int projectID = project.getId();
                LabourerRepositoryImpl labourerRepository = new LabourerRepositoryImpl();
                boolean check = labourerRepository.addLabourer(labourers, projectID);
                if (check) {
                    System.out.println("labourer added successfully.");
                    projectController.continueProject(sc, project , client);
                } else {
                    System.out.println("Failed to add labourer.");
                }
            }else{
                projectController.continueProject(sc, project , client);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input: " + e.getMessage());
            sc.nextLine();
        }
    }
}
