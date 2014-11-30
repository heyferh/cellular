package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.cellular.dao.api.ClientDAO;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.ClientException;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.ClientService;

import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
@Transactional
@Service("ClientService")
public class ClientServiceImpl implements ClientService {

    private final static Logger logger = Logger.getLogger(ClientService.class);
    @Autowired
    private ClientDAO clientDAO;

    @Override
    public void createClient(Client client) throws ClientException {
        try {
            logger.info("Creating client: " + client);
            clientDAO.create(client);
        } catch (DAOException e) {
            logger.error("Error while creating: " + client + ". DAOException was caught.");
            throw new ClientException("Error while creating: " + client.getFirstName() + " " + client.getLastName());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClientById(long id) throws ClientException {
        try {
            logger.info("Getting client by id: " + id);
            return clientDAO.get(id);
        } catch (DAOException e) {
            logger.error("Error while getting client with id: " + id + ". DAOException was caught.");
            throw new ClientException("Error while getting client with id: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClientByEmail(String email) throws ClientException {
        try {
            logger.info("Getting client by email: " + email);
            return clientDAO.getClientByEmail(email);
        } catch (DAOException e) {
            logger.error("Error while getting client with email: " + email + ". DAOException was caught.");
            throw new ClientException("Error while getting client with email: " + email);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClientByPhoneNumber(String phoneNumber) throws ClientException {
        try {
            logger.info("Getting client with phone number: " + phoneNumber);
            return clientDAO.getClientByNumber(phoneNumber);
        } catch (DAOException e) {
            logger.error("Error while getting client by phone number: " + phoneNumber + ". DAOException was caught.");
            throw new ClientException("Error while getting client by phone number: " + phoneNumber);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClients() throws ClientException {
        List<Client> lst = null;
        try {
            logger.info("Getting all clients");
            lst = clientDAO.getAll();
            return lst;
        } catch (DAOException e) {
            logger.error("Error while getting all clients. DAOException was caught.");
            throw new ClientException("Error while getting all clients");
        }

    }

    @Override
    public void updateClient(Client client) throws ClientException {
        try {
            logger.info("Updating client: " + client);
            clientDAO.update(client);
        } catch (DAOException e) {
            logger.error("Error while updating client: " + client + ". DAOException was caught.");
            throw new ClientException("Error while updating client: " + client.getFirstName() + " " + client.getLastName());
        }
    }

    @Override
    public void deleteClient(Client client) throws ClientException {
        try {
            logger.info("Deleting client: " + client);
            clientDAO.delete(client);
        } catch (DAOException e) {
            logger.error("Error while deleting client: " + client + ". DAOException was caught.");
            throw new ClientException("Error while deleting client: " + client.getFirstName() + " " + client.getLastName());
        }
    }
}