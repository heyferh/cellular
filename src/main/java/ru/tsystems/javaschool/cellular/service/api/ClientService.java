package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.ClientException;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface ClientService {

    public void createClient(Client client) throws ClientException;

    public Client getClientById(long id) throws ClientException;

    public Client getClientByEmail(String email) throws ClientException;

    public List<Client> getAllClients() throws ClientException;

    public void updateClient(Client client) throws ClientException;

    public void deleteClient(Client client) throws ClientException;
}
