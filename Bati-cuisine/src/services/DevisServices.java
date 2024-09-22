package services;

import entity.Devis;
import entity.Project;
import repository.Devis.DevisRepositoryImpl;

public class DevisServices {
    final DevisRepositoryImpl repository = new DevisRepositoryImpl();
    public boolean saveDevis(Devis devis , Project project) {
        try {
           return repository.save(devis , project);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
