package DAO.project;

import configuration.DatabaseConnection;
import entity.Client;
import entity.Devis;
import entity.Project;
import enums.ProjectStatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            DatabaseConnection db = new DatabaseConnection();
            connection = db.getConnection();
            if (t instanceof Project) {
                Project project = (Project) t;
                String sql = "UPDATE projects SET projectname = ?, surface = ?, projectstatus = ?, beneficialmargin = ?, totalcost = ?, taxrate = ? WHERE id = ?";
                ps = connection.prepareStatement(sql);
                // Set parameters for project
                ps.setString(1, project.getProjectName());
                ps.setDouble(2, project.getSurface());
                ps.setString(3, String.valueOf(project.getStatus()));
                ps.setDouble(4, project.getBeneficialMargin());
                ps.setDouble(5, project.getTotalCost());
                ps.setDouble(6, project.getTaxRate());
                ps.setInt(7, project.getId());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Ensure resources are closed
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
    public HashMap<Integer, Project> getAll() {
        HashMap<Integer, Project> projectMap = new HashMap<>();
        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            String query = "SELECT " +
                    "projects.id, " +
                    "projects.projectname, " +
                    "projects.surface, " +
                    "projects.projectstatus, " +
                    "clients.name, " +
                    "clients.address, " +
                    "clients.phone, " +
                    "clients.isprofessional, " + // Add missing comma
                    "devis.estimatedamount, " +
                    "devis.emissiondate, " +
                    "devis.expirationdate, " + // Add missing comma
                    "devis.isaccepted " +
                    "FROM projects " +
                    "INNER JOIN clients ON projects.clientid = clients.id " +
                    "INNER JOIN devis ON projects.id = devis.projectid";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                String projectName = resultSet.getString("projectname");
                double surface = resultSet.getDouble("surface");
                String status = resultSet.getString("projectstatus");

                // Create Client object
                Client client = new Client(
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("isprofessional")
                );

                // Create Devis object
                Devis devis = new Devis(
                        resultSet.getDouble("estimatedamount"),
                        resultSet.getDate("emissiondate"),
                        resultSet.getDate("expirationdate"),
                        resultSet.getBoolean("isaccepted")
                );

                // Convert status string to ProjectStatusEnum
                ProjectStatusEnum projectStatus = ProjectStatusEnum.valueOf(status.toUpperCase());
                // Create Project object and add to the map
                Project project = new Project(projectName, surface, projectStatus, client, devis);
                projectMap.put(projectId, project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectMap;
    }

    public List<Project> getAllProjects() {
        List<Project> projectList = new ArrayList<>();
        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();

            // Corrected SQL query: removed the extra comma before 'FROM'
            String query = "SELECT " +
                    "projects.id, " +
                    "projects.projectname, " +
                    "projects.surface, " +
                    "projects.projectstatus, " +
                    "clients.name, " +
                    "clients.address, " +
                    "clients.phone, " +
                    "clients.isprofessional " + // Corrected comma here
                    "FROM projects " +
                    "INNER JOIN clients ON projects.clientid = clients.id";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                String projectName = resultSet.getString("projectname");
                double surface = resultSet.getDouble("surface");
                String status = resultSet.getString("projectstatus");

                Client client = new Client(
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("isprofessional")
                );

                // Convert status string to ProjectStatusEnum
                ProjectStatusEnum projectStatus = ProjectStatusEnum.valueOf(status.toUpperCase());

                // Create Project object and add to the list
                Project project = new Project(projectName, surface, projectStatus, client);
                project.setId(projectId);
                projectList.add(project); // Add to the list without specifying index
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList; // Return the list of projects
    }

}
