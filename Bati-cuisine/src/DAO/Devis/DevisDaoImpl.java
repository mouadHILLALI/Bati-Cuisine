package DAO.Devis;

import configuration.DatabaseConnection;
import entity.Devis;
import entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DevisDaoImpl implements DevisDao {

    @Override
    public <T> boolean create(T t) {
        return false;
    }

    @Override
    public <T> boolean update(T t) {
        return false;
    }

    @Override
    public <T> boolean delete(T t) {
        return false;
    }

    @Override
    public <T> boolean find(T t) {
        return false;
    }

    @Override
    public <T> List<T> getAll(T t) {
        return Collections.emptyList();
    }

    @Override
    public boolean create(Devis devis, Project project) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection conn = db.getConnection();
            String sql = "insert into devis(estimatedamount , emissiondate ,expirationdate , isaccepted , projectid) values(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, devis.getEstimatedAmount());
            ps.setDate(2,devis.getEmissionDate());
            ps.setDate(3,devis.getExpirationDate());
            ps.setBoolean(4,devis.isAccepted());
            ps.setInt(5 , project.getId());
            int row = ps.executeUpdate();
            if (row > 0) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
