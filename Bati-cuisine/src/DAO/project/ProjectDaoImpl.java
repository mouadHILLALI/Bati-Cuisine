package DAO.project;

import configuration.DatabaseConnection;
import entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDaoImpl implements ProjectDao {

    @Override
    public boolean addProject(Project project, int clientID) {
        String query = "INSERT INTO projects (projectName, surface, projectStatus, clientid) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;

        try {
            DatabaseConnection db = new DatabaseConnection();
            connection = db.getConnection();
            ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            // Set the parameters for the project
            ps.setString(1, project.getProjectName());
            ps.setDouble(2, project.getSurface());
            ps.setString(3, String.valueOf(project.getStatus()));
            ps.setInt(4, clientID);

            // Execute the query and get the affected rows count
            int rowsAffected = ps.executeUpdate();

            // If rows were affected, retrieve the generated keys
            if (rowsAffected > 0) {
                generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    // Set the project ID with the auto-generated value
                    project.setId(generatedKeys.getInt(1));
                } else {
                    System.out.println("No generated key returned.");
                }
            } else {
                System.out.println("No rows affected.");
            }
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public <T> boolean create(T t ) {
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
