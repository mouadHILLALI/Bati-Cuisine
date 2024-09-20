package services;

import entity.Project;
import repository.Project.ProjectRepositoryImpl;

public class ProjectServices {
    final ProjectRepositoryImpl repository = new ProjectRepositoryImpl();
    public boolean createProject(Project project , int clientID) {
        try {
            return repository.create(project , clientID);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean updateProject(Project project) {
        try {
            return repository.update(project);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
