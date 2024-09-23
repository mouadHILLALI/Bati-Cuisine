package controllers;

import DAO.material.MaterialDaoImpl;
import entity.*;
import repository.Labourer.LabourerRepositoryImpl;
import services.DevisServices;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
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
            System.out.println("Project name: " + project.getProjectName()+"\n");
            System.out.println("Client :" + client.getName()+"\n");
            System.out.println("Client Address: " + client.getAddress()+"\n");
            System.out.println("Surface: "+ project.getSurface());
            System.out.println("Costs details: \n");
            calculateMaterialsCost();
            calculateLabourersCost();
            calculateDevisTotal();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void calculateMaterialsCost(){
        try {
          List<Material> materials = materialDao.getAll(project);
          System.out.println("Total Materials: " + materials.size() + "\n");
          for (Material material : materials) {
              materialTotalCost = (material.getUnitCost()*material.getQuantity()*material.getQualityCoefficient()) + material.getTransportCost();
              System.out.println("Material Name: " + material.getName()+ " " + materialTotalCost + "(quantity: "+material.getQuantity()+", unit cost :" + material.getUnitCost()+ ",quality :" + material.getQualityCoefficient()+"transport :"+material.getTransportCost()+")\n" );
              materialsTotalCost += materialTotalCost ;
          }
          System.out.println("Materials Total Cost :" + materialsTotalCost + "\n");
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void calculateLabourersCost(){
        LabourerRepositoryImpl labourerRepository = new LabourerRepositoryImpl();
        List<Labourer> labourers = labourerRepository.getLabourers(project);
        try {
            System.out.println("Labourers :\n");
            System.out.println("Total Labourers: " + labourers.size() + "\n");
            for (Labourer labourer : labourers) {
                labourerTotalCost = (labourer.getHourlyRate()*labourer.getTotalHours()*labourer.getProductivityCoefficient());
                System.out.println("Labourer specialty: " + labourer.getName()+ " " + labourerTotalCost + "(quantity: "+labourer.getTotalHours()+ ", unit cost : " + labourer.getHourlyRate()+ " ,quality : " + labourer.getProductivityCoefficient()+""+")\n" );
                labourersTotalCost += labourerTotalCost;
            }
            System.out.println("Labourers Total Cost: " + labourersTotalCost + "\n");
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void calculateDevisTotal(){
        try {
            System.out.println("Total cost without beneficial margin: " + (labourerTotalCost+materialsTotalCost) + "DH \n");
            System.out.println("beneficial margin: " + ((labourerTotalCost+materialsTotalCost)*(project.getBeneficialMargin()/100))+ "DH \n");
            System.out.println("Total cost with beneficial margin: ("+ project.getBeneficialMargin()+"%)" + (labourerTotalCost+materialsTotalCost) +
                    ((labourerTotalCost+materialsTotalCost)*(project.getBeneficialMargin()/100))+ "DH \n");
            totalCost =  (labourerTotalCost+materialsTotalCost) + ((labourerTotalCost+materialsTotalCost)*(project.getBeneficialMargin()/100));
            System.out.println("Project total cost without tax:" + totalCost + "\n");
            System.out.println("Project total cost with taxes :" + totalCost +(totalCost*(project.getTaxRate())/100) + "\n");
            registerDevis(totalCost , scanner);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void registerDevis(double totalCost, Scanner scanner) {
        System.out.println("----Saving the Devis----\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate emissionLocalDate = null;
        LocalDate expirationLocalDate = null;
        while (emissionLocalDate == null) {
            try {
                System.out.println("Enter the emission date (dd/MM/yyyy):\n");
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

        // Loop for valid expiration date input
        while (expirationLocalDate == null) {
            try {
                System.out.println("Enter the expiration date (dd/MM/yyyy):\n");
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
        if (client.isProfessional()){
            System.out.println(client.getName() + " is a professional do you wish to grant him a reduction ?(y/n)");
            String answer = scanner.nextLine();
            if (answer.equals("y")){
                System.out.println("reduction is (10%) :" + totalCost/0.9 + "DH");
                totalCost = totalCost * 0.9;
                System.out.println("new total Cost is:" + totalCost + "DH");
            }
        }
        boolean isAccepted = false;
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
