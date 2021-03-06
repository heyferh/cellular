package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.cellular.dao.api.ClientDAO;
import ru.tsystems.javaschool.cellular.dao.impl.ClientDAOImpl;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.ClientException;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.ClientService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class ClientServiceImpl implements ClientService {
    private final Logger logger = Logger.getLogger(ClientService.class);
    private EntityManager entityManager;
    private ClientDAO clientDAO;

    public ClientServiceImpl(EntityManager entityManager) {
        clientDAO = new ClientDAOImpl(entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public void createClient(Client client) throws ClientException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            logger.info("Creating client: " + client);
            clientDAO.create(client);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while creating: " + client);
            throw new ClientException("Error while creating: " + client.getFirstName() + " " + client.getLastName());
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public Client getClientById(long id) throws ClientException {
        try {
            logger.info("Getting client by id: " + id);
            return clientDAO.get(id);
        } catch (DAOException e) {
            logger.error("Error while getting client with id: " + id);
            throw new ClientException("Error while getting client with id: " + id);
        }
    }

    @Override
    public Client getClientByEmail(String email) throws ClientException {
        try {
            logger.info("Getting client by email: " + email);
            return clientDAO.getClientByEmail(email);
        } catch (DAOException e) {
            logger.error("Error while getting client with email: " + email);
            throw new ClientException("Error while getting client with email: " + email);
        }
    }

    @Override
    public Client getClientByPhoneNumber(String phoneNumber) throws ClientException {
        try {
            return clientDAO.getClientByNumber(phoneNumber);
        } catch (DAOException e) {
            logger.error("Error while getting client by phone number: "+phoneNumber);
            throw new ClientException("Error while getting client by phone number: "+phoneNumber);
        }    }

    @Override
    public List<Client> getAllClients() throws ClientException {
        List<Client> lst = null;
        try {
            logger.info("Getting all clients");
            lst = clientDAO.getAll();
            return lst;
        } catch (DAOException e) {
            logger.error("Error while getting all clients");
            throw new ClientException("Error while getting all clients");
        }

    }

    @Override
    public void updateClient(Client client) throws ClientException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            logger.info("Updating client: " + client);
            clientDAO.update(client);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while updating client: " + client);
            throw new ClientException("Error while updating client: " + client.getFirstName() + " " + client.getLastName());
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void deleteClient(Client client) throws ClientException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            logger.info("Deleting client: " + client);
            clientDAO.delete(client);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while deleting client: " + client);
            throw new ClientException("Error while deleting client: " + client.getFirstName()+" "+client.getLastName());
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }
}