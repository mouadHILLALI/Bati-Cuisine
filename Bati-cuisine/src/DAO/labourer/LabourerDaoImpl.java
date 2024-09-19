package DAO.labourer;

import configuration.DatabaseConnection;
import entity.Labourer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
