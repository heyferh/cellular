package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.ClientDAO;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Created by ferh on 09.10.14.
 */
public class ClientDAOImpl extends CommonDAOImpl<Client> implements ClientDAO {

    public ClientDAOImpl(EntityManager entityManager) {
        super(entityManager, Client.class);
    }

    public Client findClientByEmail(String email) throws DAOException {
        try {
            Query query = entityManager.createQuery("select c from Client c where c.email=:email")
                    .setParameter("email", email);
            return (Client) query.getSingleResult();
        } catch (PersistenceException e) {
            throw new DAOException("Searching for client with phone number: " + email + " fails", e);
        }
    }
}