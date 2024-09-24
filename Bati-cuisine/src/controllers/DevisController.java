package controllers;
import DAO.material.MaterialDaoImpl;
import Helpers.InputValidator;
import entity.*;
import enums.ProjectStatusEnum;
import repository.Labourer.LabourerRepositoryImpl;
import services.DevisServices;
import services.ProjectServices;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DevisController {
    private Project project;
    private Client client;
    private double materialTotalCost = 0.0 ;
    private double materialsTotalCost = 0.0 ;
    private double labourerTotalCost = 0.0 ;
    private double labourersTotalCost = 0.0 ;
    private double totalCost = 0.0 ;
    private Scanner scanner;
    final MaterialDaoImpl materialDao = new MaterialDaoImpl();
    public DevisController(Project project, Client client , Scanner scanner) {
        this.project = project;
        this.client = client;
        this.scanner = scanner;
    }

    public void displayDevis() {
        try {
            System.out.println("Project name => " + project.getProjectName() + "\n");
            System.out.println("Client =>" + client.getName() + "\n");
            System.out.println("Client Address =>" + client.getAddress() + "\n");
            System.out.println("Surface =>" + project.getSurface());
            System.out.println("Costs details :\n");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Sleep was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }

            calculateMaterialsCost();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Sleep was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }

            calculateLabourersCost();
            calculateDevisTotal();

        } catch (NullPointerException e) {
            System.out.println("An error occurred while displaying the Devis. Missing data: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("A runtime error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void calculateMaterialsCost() {
        try {
            List<Material> materials = materialDao.getAll(project);
            System.out.println("Total Materials: " + materials.size() + "\n");
            for (Material material : materials) {
                double materialTotalCost = (material.getUnitCost() * material.getQuantity() * material.getQualityCoefficient()) + material.getTransportCost();
                System.out.println("Material Name: " + material.getName() +
                        " Total Cost: " + materialTotalCost +
                        " (Quantity: " + material.getQuantity() +
                        ", Unit Cost: " + material.getUnitCost() +
                        ", Quality Coefficient: " + material.getQualityCoefficient() +
                        ", Transport Cost: " + material.getTransportCost() + ")");

                materialsTotalCost += materialTotalCost;
                Thread.sleep(1000);
            }

            System.out.println("\nMaterials Total Cost: " + materialsTotalCost);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void calculateLabourersCost() {
        LabourerRepositoryImpl labourerRepository = new LabourerRepositoryImpl();
        List<Labourer> labourers = labourerRepository.getLabourers(project);
        try {
            System.out.println("Labourers:\n");
            System.out.println("Total Labourers: " + labourers.size() + "\n");

            for (Labourer labourer : labourers) {
                double labourerTotalCost = (labourer.getHourlyRate() * labourer.getTotalHours() * labourer.getProductivityCoefficient());
                System.out.println("Labourer Specialty: " + labourer.getName() +
                        " Total Cost: " + labourerTotalCost +
                        " (Total Hours: " + labourer.getTotalHours() +
                        ", Hourly Rate: " + labourer.getHourlyRate() +
                        ", Productivity Coefficient: " + labourer.getProductivityCoefficient() + ")");

                labourersTotalCost += labourerTotalCost;
                Thread.sleep(1000);
            }

            System.out.println("\nLabourers Total Cost: " + labourersTotalCost + " DH\n");
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void calculateDevisTotal() {
        try {
            double totalWithoutMargin = labourersTotalCost + materialsTotalCost;
            double beneficialMargin = totalWithoutMargin * (project.getBeneficialMargin() / 100);
            double totalWithMargin = totalWithoutMargin + beneficialMargin;

            System.out.println("Total Cost Without Beneficial Margin: " + totalWithoutMargin + " DH");
            System.out.println("Beneficial Margin (" + project.getBeneficialMargin() + "%): " + beneficialMargin + " DH");
            System.out.println("Total Cost With Beneficial Margin: " + totalWithMargin + " DH");

            totalCost = totalWithMargin;
            System.out.println("Project Total Cost Without Tax: " + totalCost + " DH");
            System.out.println("Project Total Cost With Taxes: " + (totalCost + (totalCost * project.getTaxRate() / 100)) + " DH");

            registerDevis(totalCost, scanner);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void registerDevis(double totalCost, Scanner scanner) {
        System.out.println("---- Saving the Devis ----\n");
        InputValidator inputValidator = new InputValidator(scanner);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate emissionLocalDate = null;
        LocalDate expirationLocalDate = null;

        while (emissionLocalDate == null) {
            try {
                System.out.print("Enter the emission date (dd/MM/yyyy): ");
                String emissionDateInput = scanner.nextLine();
                emissionLocalDate = LocalDate.parse(emissionDateInput, formatter);
                if (emissionLocalDate.isBefore(LocalDate.now())) {
                    System.out.println("Emission date cannot be before the current date. Please enter a valid date.");
                    emissionLocalDate = null;
                }
            } catch (DateTimeException e) {
                System.out.println("Invalid date format. Please enter a valid date in the format dd/MM/yyyy.");
            }
        }

        while (expirationLocalDate == null) {
            try {
                System.out.print("Enter the expiration date (dd/MM/yyyy): ");
                String expirationDateInput = scanner.nextLine();
                expirationLocalDate = LocalDate.parse(expirationDateInput, formatter);
                if (expirationLocalDate.isBefore(emissionLocalDate)) {
                    System.out.println("Expiration date cannot be before the emission date. Please enter a valid date.");
                    expirationLocalDate = null;
                }
            } catch (DateTimeException e) {
                System.out.println("Invalid date format. Please enter a valid date in the format dd/MM/yyyy.");
            }
        }

        Date emissionDate = java.sql.Date.valueOf(emissionLocalDate);
        Date expirationDate = java.sql.Date.valueOf(expirationLocalDate);

        if (client.isProfessional()) {
            String answer = inputValidator.ValidatePrompt(client.getName() + " is a professional. Do you wish to grant a reduction? (y/n): ");
            if (answer.equalsIgnoreCase("y")) {
                double reduction = totalCost * 0.05;
                System.out.printf("Reduction (5%%): %.2f DH%n", reduction);
                totalCost *= 0.95;
                System.out.printf("New Total Cost: %.2f DH%n", totalCost);
            }
        }

        boolean isAccepted = false;
        String answer = inputValidator.ValidatePrompt("Do you accept the Devis? (y/n): ");
        if (answer.equalsIgnoreCase("y")) {
            isAccepted = true;
        } else if (answer.equalsIgnoreCase("n")) {
            isAccepted = false;
            ProjectServices projectServices = new ProjectServices();
            ProjectStatusEnum statusEnum = ProjectStatusEnum.CANCELLED;
            project.setStatus(statusEnum);
            boolean flag = projectServices.updateProject(project);
            if (flag) {
                System.out.println("Project is now canceled.");
            }
        }

        Devis devis = new Devis(totalCost, emissionDate, expirationDate, isAccepted);
        DevisServices devisServices = new DevisServices();
        boolean flag = devisServices.saveDevis(devis, project);
        if (flag) {
            System.out.println("Devis successfully registered.");
        } else {
            System.out.println("Devis could not be registered.");
        }
    }

}
