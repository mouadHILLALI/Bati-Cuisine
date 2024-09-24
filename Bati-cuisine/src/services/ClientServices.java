package services;
import entity.Client;
import repository.Client.ClientRepositoryImpl;

import java.util.Optional;

public class ClientServices {
    final ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
    public boolean createClient(Client client) {
        try {
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
