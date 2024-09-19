package entity;

public class Labourer {
    private int id;
    private String specialty;
    private double hourlyRate;
    private double totalHours;
    private double taxRate;
    private double productivityCoefficient;
    public Labourer(int id , String specialty , double hourlyRate , double totalHours , double taxRate , double productivityCoefficient) {
        this.id = id;
        this.specialty = specialty;
        this.hourlyRate = hourlyRate;
        this.totalHours = totalHours;
        this.taxRate = taxRate;
        this.productivityCoefficient = productivityCoefficient;
    }
    public Labourer( String specialty , double hourlyRate , double totalHours , double taxRate , double productivityCoefficient) {
        this.specialty = specialty;
        this.hourlyRate = hourlyRate;
        this.totalHours = totalHours;
        this.taxRate = taxRate;
        this.productivityCoefficient = productivityCoefficient;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public double getHourlyRate() {
        return hourlyRate;
    }
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    public double getTotalHours() {
        return totalHours;
    }
    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }
    public double getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
    public double getProductivityCoefficient() {
        return productivityCoefficient;
    }
    public void setProductivityCoefficient(double productivityCoefficient) {
        this.productivityCoefficient = productivityCoefficient;
    }
}
