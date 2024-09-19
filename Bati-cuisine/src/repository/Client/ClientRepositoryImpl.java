package repository.Client;

import DAO.client.ClientDaoImpl;
import entity.Client;

public class ClientRepositoryImpl {
    public boolean addClient(Client client) {
        try {
            ClientDaoImpl clientDao = new ClientDaoImpl();
            return clientDao.create(client);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
