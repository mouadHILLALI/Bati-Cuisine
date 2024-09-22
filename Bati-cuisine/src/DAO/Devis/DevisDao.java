package DAO.Devis;

import DAO.DAO;
import entity.Devis;
import entity.Project;

public interface DevisDao  extends DAO {
    public boolean create(Devis devis , Project project);
}
