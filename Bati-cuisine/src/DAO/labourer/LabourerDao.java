package DAO.labourer;

import DAO.DAO;
import entity.Labourer;

public interface LabourerDao extends DAO {
    public boolean addLabourer(Labourer labourer , int projectID);
}
