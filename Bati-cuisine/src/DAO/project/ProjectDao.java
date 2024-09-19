package DAO.project;

import DAO.DAO;
import entity.Project;

public interface ProjectDao extends DAO {
    public boolean addProject(Project project , int clientID);
}
