package DAO.material;
import DAO.DAO;
import configuration.DatabaseConnection;
import entity.Material;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
