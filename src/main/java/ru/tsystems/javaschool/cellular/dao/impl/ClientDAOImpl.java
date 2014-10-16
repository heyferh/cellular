package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.ClientDAO;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class ClientDAOImpl implements ClientDAO {
    EntityManager entityManager;

    public ClientDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Client client) throws DAOException {
        try {
            entityManager.persist(client);
        } catch (PersistenceException e) {
            throw new DAOException("Adding " + client + " fails.", e);
        }
    }

    @Override
    public Client read(long id) throws DAOException {
        try {
            return entityManager.find(Client.class, id);
        } catch (PersistenceException e) {
            throw new DAOException("Reading client with id " + id + " fails.", e);
        }
    }

    @Override
    public void update(Client client) throws DAOException {
        try {
            entityManager.merge(client);
        } catch (PersistenceException e) {
            throw new DAOException("Updating " + client + " fails.", e);
        }
    }

    @Override
    public void delete(Client client) throws DAOException {
        try {
            entityManager.remove(client);
        } catch (PersistenceException e) {
            throw new DAOException("Deleting " + client + " fails.", e);
        }

    }

    public Client findClientByPhoneNumber(String phoneNumber) throws DAOException {
        try {
            Query query = entityManager.createQuery("select c.client from Contract c where c.phoneNumber=:number")
                    .setParameter("number", phoneNumber);
            return (Client) query.getSingleResult();
        } catch (PersistenceException e) {
            throw new DAOException("Searching for client with phone number: " + phoneNumber + " fails", e);
        }
    }

    public List<Client> getAll() throws DAOException {
        try {
            return entityManager.createNamedQuery("Client.getAll", Client.class).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Getting list of clients fails", e);
        }
    }
}