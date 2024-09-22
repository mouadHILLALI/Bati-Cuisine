package DAO.labourer;

import configuration.DatabaseConnection;
import entity.Labourer;
import entity.Material;
import entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LabourerDaoImpl implements LabourerDao {
    @Override
    public boolean addLabourer(Labourer labourer, int projectID) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            DatabaseConnection db = new DatabaseConnection();
            conn = db.getConnection();
            String query = "INSERT INTO labourers(specialty, hourlyrate, totalhours, taxrate, productivitycoefficient, projectid) VALUES (?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            // Set the parameters for the labourer
            ps.setString(1, labourer.getSpecialty());
            ps.setDouble(2, labourer.getHourlyRate());
            ps.setDouble(3, labourer.getTotalHours());
            ps.setDouble(4, labourer.getTaxRate());
            ps.setDouble(5, labourer.getProductivityCoefficient());
            ps.setInt(6, projectID);
            // Execute the query and return true if a row is inserted
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public <T> boolean create(T t) {
        return false;
    }

    @Override
    public <T> boolean update(T t) {
        return false;
    }

    @Override
    public <T> boolean delete(T t) {
        return false;
    }

    @Override
    public <T> boolean find(T t) {
        return false;
    }

    @Override
    public <T> List<T> getAll(T t) {
        return Collections.emptyList();
    }
    public List<Labourer> getAll(Project project) {
        List<Labourer> labourers = new ArrayList<>();
        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection conn = db.getConnection();
            String query = "SELECT * FROM labourers WHERE projectid=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, project.getId());
            ResultSet rs = ps.executeQuery();

            // Loop through the result set and populate the list with Labourer objects
            while (rs.next()) {
                Labourer labourer = new Labourer();
                labourer.setSpecialty(rs.getString("specialty"));
                labourer.setHourlyRate(rs.getDouble("hourlyrate"));
                labourer.setTotalHours(rs.getDouble("totalhours"));
                labourer.setTaxRate(rs.getDouble("taxrate"));
                labourer.setProductivityCoefficient(rs.getDouble("productivitycoefficient"));
                labourers.add(labourer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labourers;
    }

}
