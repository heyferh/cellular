package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface ClientService {

    public void createClient(Client client);

    public Client getClientById(long id) throws DAOException;

    public List<Client> getAllClients() throws DAOException;

    public void updateClient(Client client);

    public void deleteClient(Client client);

    public Client findClientByNumber(String number) throws DAOException;

}
