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
            Client client = (Client) query.getSingleResult();
            logger.info("Getting client:" + client);
            return client;
        } catch (PersistenceException e) {
            logger.error("Searching for client with phone number: " + email + " fails");
            throw new DAOException("Searching for client with phone number: " + email + " fails", e);
        }
    }

    @Override
    public Client getClientByNumber(String phoneNumber) throws DAOException {
        try {
            return (Client) entityManager.createQuery("select c.client from Contract c where c.phoneNumber =:number")
                    .setParameter("number", phoneNumber).getSingleResult();
        } catch (PersistenceException e) {
            logger.error("Unable to get contract with phone number: " + phoneNumber);
            throw new DAOException("Unable to get contract with phone number: " + phoneNumber, e);
        }
    }
}