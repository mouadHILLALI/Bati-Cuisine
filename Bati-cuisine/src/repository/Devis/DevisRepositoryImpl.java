package repository.Devis;

import DAO.Devis.DevisDao;
import DAO.Devis.DevisDaoImpl;
import entity.Devis;
import entity.Project;

public class DevisRepositoryImpl implements DevisRepository {
    final DevisDao dao = new DevisDaoImpl();
    @Override
    public boolean save(Devis devis , Project project) {
        try {
            return dao.create(devis , project);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
