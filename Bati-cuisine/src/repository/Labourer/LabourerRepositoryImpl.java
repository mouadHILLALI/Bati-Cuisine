package repository.Labourer;

import DAO.labourer.LabourerDaoImpl;
import entity.Labourer;
import entity.Project;

import java.util.List;

public class LabourerRepositoryImpl {
    final LabourerDaoImpl labourerDao = new LabourerDaoImpl();
    public boolean addLabourer(List<Labourer>labourers , int projectID) {
        try {
            for (Labourer labourer : labourers) {
                boolean flag = labourerDao.addLabourer(labourer , projectID);
                if (!flag) {
                    return false;
                }
            }
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Labourer> getLabourers(Project project) {
        try {
           return labourerDao.getAll(project);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
