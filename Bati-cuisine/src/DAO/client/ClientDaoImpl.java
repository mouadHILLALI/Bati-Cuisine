package DAO.client;

import DAO.DAO;
import configuration.DatabaseConnection;
import entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public Optional<Client> find(String name) {
        Optional<Client> client = Optional.empty(); // Initialize as empty Optional
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            String sql = "SELECT * FROM clients WHERE name = ? OR name LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Client foundClient = new Client();
                foundClient.setId(resultSet.getInt("id"));
                foundClient.setName(resultSet.getString("name"));
                foundClient.setAddress(resultSet.getString("address"));
                foundClient.setPhone(resultSet.getString("phone"));
                foundClient.setProfessional(resultSet.getBoolean("isprofessional"));
                client = Optional.of(foundClient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
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
