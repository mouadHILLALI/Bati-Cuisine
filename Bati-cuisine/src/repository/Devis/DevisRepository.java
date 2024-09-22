package repository.Devis;

import entity.Devis;
import entity.Project;

public interface DevisRepository  {
    public boolean save(Devis devis , Project project);
}
