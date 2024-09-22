package entity;

import enums.ProjectStatusEnum;

public class Project {
    private int id;
    private String projectName;
    private double beneficialMargin;
    private double totalCost;
    private double taxRate;
    private double surface;
    private Client client;
    private Devis devis;
    private ProjectStatusEnum status;
    public Project(int id, String projectName, double beneficialMargin, double totalCost, ProjectStatusEnum status , double taxRate , double surface) {
        this.id = id;
        this.projectName = projectName;
        this.beneficialMargin = beneficialMargin;
        this.totalCost = totalCost;
        this.status = status;
        this.taxRate = taxRate;
        this.surface = surface;
    }
    public Project(String projectName , ProjectStatusEnum status , double surface) {
        this.projectName = projectName;
        this.status = status;
        this.surface = surface;
    }
    public Project(String projectName ,Double surface , ProjectStatusEnum status , Client client, Devis devis) {
        this.projectName = projectName;
        this.surface = surface;
        this.client = client;
        this.devis = devis;
        this.status = status;
    }
    public Project(String projectName ,Double surface , ProjectStatusEnum status , Client client) {
        this.projectName = projectName;
        this.surface = surface;
        this.client = client;
        this.status = status;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public double getBeneficialMargin() {
        return beneficialMargin;
    }
    public void setBeneficialMargin(double beneficialMargin) {
        this.beneficialMargin = beneficialMargin;
    }
    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public ProjectStatusEnum getStatus() {
        return status;
    }
    public void setStatus(ProjectStatusEnum status) {
        this.status = status;
    }
    public double getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
    public double getSurface() {
        return surface;
    }
    public void setSurface(double surface) {
        this.surface = surface;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Devis getDevis() {
        return devis;
    }
    public void setDevis(Devis devis) {
        this.devis = devis;
    }

}
