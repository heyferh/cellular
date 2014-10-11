package ru.tsystems.javaschool.cellular.service.impl;

import ru.tsystems.javaschool.cellular.dao.impl.ClientDAO;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Manager;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.ClientService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class ClientServiceImpl implements ClientService {
    EntityManager entityManager = Manager.getEntityManager();
    private ClientDAO clientDAO = new ClientDAO(entityManager, Client.class);

    @Override
    public void createClient(Client client) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            clientDAO.create(client);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public Client getClientById(long id) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Client client = clientDAO.read(id);
            entityTransaction.commit();
            if (client == null) throw new DAOException("Client with id: " + id + " doesn't exist");
            return client;
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public List<Client> getAllClients() {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            List<Client> lst = clientDAO.getAll();
            if (lst.size() == 0) throw new DAOException("There is no clients in database yet");
            return lst;
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public void updateClient(Client client) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            clientDAO.update(client);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public void deleteClient(Client client) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            clientDAO.delete(client);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public Client findClientByNumber(String number) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Client client = clientDAO.findClientByPhoneNumber(number);
            entityTransaction.commit();
            if (client == null) throw new DAOException("Client with number" + number + "not found");
            return client;
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }
}
