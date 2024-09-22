package DAO.material;
import configuration.DatabaseConnection;
import entity.Material;
import entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaterialDaoImpl implements MaterialDao  {
    @Override
    public boolean addMaterial(Material material , int projectID) {
    try {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String sql = "insert into materials(materialname, unitcost,quantity,tax,transcost,qualitycoeff,projectid) values(?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, material.getName());
        preparedStatement.setDouble(2, material.getUnitCost());
        preparedStatement.setDouble(3, material.getQuantity());
        preparedStatement.setDouble(4, material.getTaxRate());
        preparedStatement.setDouble(5, material.getTransportCost());
        preparedStatement.setDouble(6, material.getQualityCoefficient());
        preparedStatement.setInt(7, projectID);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }catch (SQLException e){
        e.printStackTrace();
    }        
    return false;
    }
    public List<Material> getAll(Project project) {
        List<Material> materials = new ArrayList<>();
            try {
                DatabaseConnection db = new DatabaseConnection();
                Connection connection = db.getConnection();

                // Query to fetch materials associated with the given project ID
                String sql = "SELECT * FROM materials WHERE projectid = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, project.getId());  // Set the project ID in the query
                ResultSet rs = ps.executeQuery();

                // Iterate through the result set and create Material objects
                while (rs.next()) {
                    String name = rs.getString("materialname");
                    double unitCost = rs.getDouble("unitcost");
                    double quantity = rs.getDouble("quantity");
                    double taxRate = rs.getDouble("tax");
                    double transportCost = rs.getDouble("transcost");
                    double qualityCoeff = rs.getDouble("qualitycoeff");
                    // Create a new Material object and add it to the list
                    Material material = new Material(name, unitCost, quantity, taxRate, transportCost, qualityCoeff);
                    materials.add(material);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        return materials;  // Return the list of materials
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


}
