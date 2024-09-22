package entity;

import java.util.Date;

public class Devis {
    private int id;
    private double estimatedAmount;
    private Date emissionDate;
    private Date expirationDate;
    private boolean isAccepted;
    public Devis(int id, double estimatedAmount, Date emissionDate, Date expirationDate, boolean isAccepted) {
        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.emissionDate = emissionDate;
        this.expirationDate = expirationDate;
        this.isAccepted = isAccepted;
    }
    public Devis(double estimatedAmount, Date emissionDate, Date expirationDate, boolean isAccepted) {
        this.estimatedAmount = estimatedAmount;
        this.emissionDate = emissionDate;
        this.expirationDate = expirationDate;
        this.isAccepted = isAccepted;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getEstimatedAmount() {
        return estimatedAmount;
    }
    public void setEstimatedAmount(double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }
    public java.sql.Date getEmissionDate() {
        return (java.sql.Date) emissionDate;
    }
    public void setEmissionDate(Date emissionDate) {
        this.emissionDate = emissionDate;
    }
    public java.sql.Date getExpirationDate() {
        return (java.sql.Date) expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    public boolean isAccepted() {
        return isAccepted;
    }
    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
