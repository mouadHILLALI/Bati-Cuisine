package services;

import entity.Project;
import repository.Project.ProjectRepositoryImpl;

public class ProjectServices {
    public boolean createProject(Project project , int clientID) {
        try {
            ProjectRepositoryImpl repository = new ProjectRepositoryImpl();
            return repository.create(project , clientID);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
