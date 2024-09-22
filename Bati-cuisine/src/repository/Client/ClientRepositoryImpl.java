package repository.Client;

import DAO.client.ClientDaoImpl;
import entity.Client;

public class ClientRepositoryImpl implements ClientRepository {
    final ClientDaoImpl clientDao = new ClientDaoImpl();
    public boolean addClient(Client client) {
        try {
            return clientDao.create(client);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Client find(String name) {
        try {
            return clientDao.find(name).get();
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
