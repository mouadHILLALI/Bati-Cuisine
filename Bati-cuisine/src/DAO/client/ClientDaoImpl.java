package DAO.client;

import DAO.DAO;
import configuration.DatabaseConnection;
import entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDaoImpl implements DAO {
    @Override
    public <T> boolean create(T t) {
        if (t instanceof Client) {
            Client client = (Client) t; // Cast to Client
            try {
                // Establish database connection
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = databaseConnection.getConnection();

                // SQL query to insert client and return generated keys
                String SQL = "INSERT INTO clients (name, address, phone, isProfessional) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    // Set parameters
                    preparedStatement.setString(1, client.getName());
                    preparedStatement.setString(2, client.getAddress());
                    preparedStatement.setString(3, client.getPhone());
                    preparedStatement.setBoolean(4, client.isProfessional());

                    // Execute the query
                    int rowsAffected = preparedStatement.executeUpdate();

                    // If a row was inserted, retrieve the generated ID
                    if (rowsAffected > 0) {
                        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                client.setId(generatedKeys.getInt(1)); // Set the generated ID to the client
                            }
                        }
                    }
                    return rowsAffected > 0; // Return success if rows were affected
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // Return false if the object is not a Client or if an error occurred
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
