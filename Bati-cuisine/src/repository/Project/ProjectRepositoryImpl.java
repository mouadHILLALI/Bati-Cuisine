package repository.Project;

import DAO.project.ProjectDaoImpl;
import entity.Project;

public class ProjectRepositoryImpl {
    public boolean create(Project project , int clientID) {
        try {
            ProjectDaoImpl projectDao = new ProjectDaoImpl();
            return projectDao.addProject(project, clientID);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
