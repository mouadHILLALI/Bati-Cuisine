package repository.Material;
import DAO.material.MaterialDaoImpl;
import entity.Material;
import java.util.List;

public class MaterialRepositoryImpl {
    public boolean createMaterials(List<Material> materials, int projectID) {
        try {
            MaterialDaoImpl materialDao = new MaterialDaoImpl();
            // Iterate through the materials list and try to create each material
            for (Material material : materials) {
                boolean flag = materialDao.addMaterial(material,projectID);
                // If the creation of any material fails, return false
                if (!flag) {
                    System.out.println("fail");
                    return false;
                }
            }
            // If all materials are successfully created, return true
            System.out.println("success");
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred while creating materials: " + e.getMessage(), e);
        }
    }

}
