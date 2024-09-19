package entity;

public class Material {
    private int id;
    private String name;
    private double unitCost;
    private double quantity;
    private double taxRate;
    private double transportCost ;
    private double qualityCoefficient;
    public Material(int id , String name, double unitCost, double quantity, double taxRate, double transportCost, double qualityCoefficient) {
        this.id = id;
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.taxRate = taxRate;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }
    public Material(String name , double unitCost, double quantity, double taxRate, double transportCost, double qualityCoefficient) {
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.taxRate = taxRate;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getUnitCost() {
        return unitCost;
    }
    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }
    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public double getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
    public double getTransportCost() {
        return transportCost;
    }
    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }
    public double getQualityCoefficient() {
        return qualityCoefficient;
    }
    public void setQualityCoefficient(double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }
}
