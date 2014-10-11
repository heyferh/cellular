package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Client;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface ClientService {

    public void createClient(Client client);

    public Client getClientById(long id);

    public List<Client> getAllClients();

    public void updateClient(Client client);

    public void deleteClient(Client client);

    public Client findClientByNumber(String number);
}
