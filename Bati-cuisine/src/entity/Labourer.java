package entity;

public class Labourer extends Component {
    private double hourlyRate;
    private double totalHours;
    private double productivityCoefficient;
    public Labourer( int id , String name , double hourlyRate , double totalHours , double productivityCoefficient) {
        super(id, name);
        this.hourlyRate = hourlyRate;
        this.totalHours = totalHours;
        this.productivityCoefficient = productivityCoefficient;
    }
    public Labourer( String Name , double hourlyRate , double totalHours ,  double productivityCoefficient) {
        super(0 , Name);
        this.hourlyRate = hourlyRate;
        this.totalHours = totalHours;
        this.productivityCoefficient = productivityCoefficient;
    }

    public Labourer() {
        super(0 , "Labourer");
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
    public double getProductivityCoefficient() {
        return productivityCoefficient;
    }
    public void setProductivityCoefficient(double productivityCoefficient) {
        this.productivityCoefficient = productivityCoefficient;
    }
}
