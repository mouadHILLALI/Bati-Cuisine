package DAO;

public interface DAO {
    public <T> boolean create(T t);
    public <T> boolean update(T t);
    public <T> boolean delete(T t);
    public <T> boolean find(T t);
}
