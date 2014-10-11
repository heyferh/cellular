package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Client;

/**
 * Created by ferh on 08.10.14.
 */
public interface ClientService {

    public void createClient(Client client);

    public Client getClientById(long id);

    public void updateClient(Client client);

    public void deleteClient(Client client);

    public Client findClientByNumber(String number);
}
