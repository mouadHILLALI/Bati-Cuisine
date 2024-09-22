package controllers;

import DAO.material.MaterialDaoImpl;
import entity.*;
import repository.Labourer.LabourerRepositoryImpl;
import services.DevisServices;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DevisController {
    private Project project;
    private Client client;
    private double materialTotalCostWithoutTax = 0.0 ;
    private double materialTotalCostWithTax = 0;
    private double labourerTotalCostWithoutTax = 0.0 ;
    private double labourerTotalCostWithTax = 0;
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
            registerDevis();
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
              materialTotalCostWithoutTax = (material.getUnitCost()*material.getQuantity()*material.getQualityCoefficient()) + material.getTransportCost();
              System.out.println("Material Name: " + material.getName()+ " " + materialTotalCostWithoutTax + "(quantity: "+material.getQuantity()+", unit cost :" + material.getUnitCost()+ ",quality :" + material.getQualityCoefficient()+"transport :"+material.getTransportCost()+")\n" );
              materialTotalCostWithTax += materialTotalCostWithoutTax ;
          }
          System.out.println("Materials Total Cost without tax:" + materialTotalCostWithTax + "\n");
          System.out.println("Materials Total Cost with taxes: " + (materialTotalCostWithTax + (materialTotalCostWithTax * (project.getTaxRate()/100))) + "\n");
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void calculateLabourersCost(){
        LabourerRepositoryImpl labourerRepository = new LabourerRepositoryImpl();
        List<Labourer> labourers = labourerRepository.getLabourers(project);
        try {
            System.out.println("Labourers :\n");
            System.out.println("Total Materials: " + labourers.size() + "\n");
            for (Labourer labourer : labourers) {
                labourerTotalCostWithoutTax = (labourer.getHourlyRate()*labourer.getTotalHours()*labourer.getProductivityCoefficient());
                System.out.println("Labourer specialty: " + labourer.getSpecialty()+ " " + labourerTotalCostWithoutTax + "(quantity: "+labourer.getTotalHours()+ ", unit cost : " + labourer.getHourlyRate()+ " ,quality : " + labourer.getProductivityCoefficient()+" transport :"+")\n" );
                labourerTotalCostWithTax += labourerTotalCostWithoutTax ;
            }
            System.out.println("Labourers Total Cost without tax: " + labourerTotalCostWithoutTax + "\n");
            System.out.println("Labourers Total Cost with taxes: " + (labourerTotalCostWithTax + (labourerTotalCostWithTax * (project.getTaxRate()/100))) + "\n");
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void calculateDevisTotal(){
        try {
            System.out.println("Total cost without beneficial margin: " + (labourerTotalCostWithTax+materialTotalCostWithTax) + "DH \n");
            System.out.println("beneficial margin: " + ((labourerTotalCostWithTax+materialTotalCostWithTax)*(project.getBeneficialMargin()/100))+ "DH \n");
            System.out.println("Total cost with beneficial margin: ("+ project.getBeneficialMargin()+"%)" + (labourerTotalCostWithTax+materialTotalCostWithTax) +
                    ((labourerTotalCostWithTax+materialTotalCostWithTax)*(project.getBeneficialMargin()/100))+ "DH \n");
            totalCost =  (labourerTotalCostWithTax+materialTotalCostWithTax) + ((labourerTotalCostWithTax+materialTotalCostWithTax)*(project.getBeneficialMargin()/100));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void registerDevis() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("----Saving the Devis----\n");
            System.out.println("Enter the emission date (dd/MM/yyyy):\n");
            String emissionDateInput = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate emissionLocalDate = LocalDate.parse(emissionDateInput, formatter);
            Date emissionDate = java.sql.Date.valueOf(emissionLocalDate);
            LocalDate currentDate = LocalDate.now();
            if (emissionLocalDate.isBefore(currentDate)) {
                System.out.println("Emission date cannot be before the current date. Please enter a valid date.");
                registerDevis();
                return;
            }
            System.out.println("Enter the expiration date (dd/MM/yyyy):\n");
            String expirationDateInput = scanner.nextLine();
            LocalDate expirationLocalDate = LocalDate.parse(expirationDateInput, formatter);
            Date expirationDate = java.sql.Date.valueOf(expirationLocalDate);
            if (expirationLocalDate.isBefore(emissionLocalDate)) {
                System.out.println("Expiration date cannot be before the emission date. Please enter a valid date.");
                registerDevis();
                return;
            }
            System.out.println("Emission date: " + emissionDate);
            System.out.println("Expiration date: " + expirationDate);
            boolean isAccepted = false;
            Devis devis = new Devis(totalCost, emissionDate, expirationDate, isAccepted);
            DevisServices devisServices = new DevisServices();
            boolean flag = devisServices.saveDevis(devis, project);
            if (flag) {
                System.out.println("Devis successfully registered.");
            } else {
                System.out.println("Devis could not be registered.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the date in the correct format.");
            e.printStackTrace();
        }
    }




}
