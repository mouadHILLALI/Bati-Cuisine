package repository.Material;
import DAO.material.MaterialDaoImpl;
import entity.Material;
import entity.Project;

import java.util.List;

public class MaterialRepositoryImpl {
    final MaterialDaoImpl materialDao = new MaterialDaoImpl();
    public boolean createMaterials(List<Material> materials, int projectID) {
        try {

            // Iterate through the materials list and try to create each material
            for (Material material : materials) {
                boolean flag = materialDao.addMaterial(material,projectID);
                // If the creation of any material fails, return false
                if (!flag) {
                    return false;
                }
            }
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred while creating materials: " + e.getMessage(), e);
        }
    }
    public List<Material> getMaterials(Project project) {
        try {
            // Call the materialDao to get the materials associated with the project
            return  materialDao.getAll(project);
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred while fetching materials: " + e.getMessage(), e);
        }
    }
}
