package services;

import entity.Project;
import repository.Project.ProjectRepositoryImpl;

import java.util.HashMap;
import java.util.List;

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
    public HashMap< Integer , Project> getAllWithDevis (){
        try {
            return repository.getAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Project> getAllProject (){
        try {
            return repository.getAllProjects();
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
