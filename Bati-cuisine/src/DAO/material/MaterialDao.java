package DAO.material;

import DAO.DAO;
import entity.Material;

public interface MaterialDao extends DAO {
    boolean addMaterial(Material material , int projectID);
}
