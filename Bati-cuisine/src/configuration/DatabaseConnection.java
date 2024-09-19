package configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private String jdbcUrl = "jdbc:postgresql://localhost:5432/baticuisine";
    private String username = "postgres";
    private String password = " "; // Password is a space

    // Connect to the database
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            if (connection != null) {
                System.out.println("Database connection established");
            } else {
                System.out.println("Database connection not established");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;  // Return the connection object
    }
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}