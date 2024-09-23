package services;
import entity.Client;
import repository.Client.ClientRepositoryImpl;

import java.util.Optional;

public class ClientServices {
    final ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
    public boolean createClient(Client client) {
        try {
            if (client.getName() == null || client.getName().isEmpty() ||
                    client.getAddress() == null || client.getAddress().isEmpty() ||
                    client.getPhone() == null || client.getPhone().isEmpty() ||
                    !client.getPhone().matches("^(?:\\+212|0)([5-7]\\d{8})$")) {
                return false;
            }

            return clientRepository.addClient(client);
        } catch (RuntimeException e) {
            System.err.println("Error creating client: " + e.getMessage());
            throw e;
        }
    }
    public Client find(String name) {
       Client client = clientRepository.find(name);
       return client;
    }
}
