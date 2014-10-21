package ru.tsystems.javaschool.cellular.service.impl;

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
            clientDAO.create(client);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new ClientException();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public Client getClientById(long id) throws ClientException {
        try {
            return clientDAO.read(id);
        } catch (DAOException e) {
            throw new ClientException();
        }
    }

    @Override
    public List<Client> getAllClients() throws ClientException {
        List<Client> lst = null;
        try {
            lst = clientDAO.getAll();
            return lst;
        } catch (DAOException e) {
            throw new ClientException();
        }

    }

    @Override
    public void updateClient(Client client) throws ClientException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            clientDAO.update(client);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new ClientException();
        }finally {
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
            clientDAO.delete(client);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new ClientException();
        }finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public Client findClientByNumber(String number) throws ClientException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Client client = clientDAO.findClientByPhoneNumber(number);
            entityTransaction.commit();
            return client;
        } catch (DAOException e) {
            throw new ClientException();
        }
    }
}
