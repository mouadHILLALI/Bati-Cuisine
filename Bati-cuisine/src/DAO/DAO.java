package DAO;

import java.util.List;

public interface DAO {
    public <T> boolean create(T t);
    public <T> boolean update(T t);
    public <T> boolean delete(T t);
    public <T> boolean find(T t);
    public <T> List<T> getAll(T t);
}
