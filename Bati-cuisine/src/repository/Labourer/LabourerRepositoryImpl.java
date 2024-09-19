package repository.Labourer;

import DAO.labourer.LabourerDaoImpl;
import entity.Labourer;

import java.util.List;

public class LabourerRepositoryImpl {
    public boolean addLabourer(List<Labourer>labourers , int projectID) {
        try {
            for (Labourer labourer : labourers) {
                LabourerDaoImpl labourerDao = new LabourerDaoImpl();
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
}
