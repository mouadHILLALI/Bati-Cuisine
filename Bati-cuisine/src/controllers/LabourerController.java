package controllers;
import Helpers.InputValidator;
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
            InputValidator inputValidator = new InputValidator(sc);
            String answer = inputValidator.ValidatePrompt("Do you wish to add labourers to the project " + project.getProjectName() + "? (y/n)");
            List<Labourer> labourers = new ArrayList<>();
            if (answer.equals("y")) {
                boolean continueAdding = true;
                while (continueAdding) {
                    String speciality =inputValidator.validateString("Enter labourer speciality: ");
                    double hourlyRate = inputValidator.ValidateDouble("Enter hourly rate: ");
                    double totalHours = inputValidator.ValidateDouble("Enter labourer total hours: ");
                    double productivityCoeff = inputValidator.ValidateDouble("Enter labourer productivity coefficient (1.0 = standard, > 1.0 = high quality):");
                    sc.nextLine();
                    Labourer labourer = new Labourer(speciality, hourlyRate, totalHours,  productivityCoeff);
                    labourers.add(labourer);
                    answer = inputValidator.ValidatePrompt("Do you wish to add another labourer? (y/n)");
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
