package repository.Client;

import entity.Client;

public interface ClientRepository {
    public Client find(String name);
}
