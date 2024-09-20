package repository.Project;

import DAO.project.ProjectDaoImpl;
import entity.Project;

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
}
