package repository.Project;

import DAO.project.ProjectDaoImpl;
import entity.Project;

import java.util.HashMap;
import java.util.List;

public class ProjectRepositoryImpl {
    final ProjectDaoImpl projectDao = new ProjectDaoImpl();
    public boolean create(Project project , int clientID) {
        try {

            return projectDao.addProject(project, clientID);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean update(Project project) {
        try {
            return projectDao.update(project);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public HashMap<Integer, Project> getAll() {
        try {
            return projectDao.getAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Project> getAllProjects() {
        try {
            return projectDao.getAllProjects();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
