package entity;

import java.util.Optional;

public class Client {
    private int id;
    private String name;
    private String address;
    private String phone;
    private boolean isProfessional;
    public Client(int id, String name, String address, String phone, boolean isProfessional) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
    }
    public Client(String name, String address, String phone, boolean isProfessional ) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
    }
    public Client() {
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isProfessional() {
        return isProfessional;
    }
    public void setProfessional(boolean isProfessional) {
        this.isProfessional = isProfessional;
    }
}
