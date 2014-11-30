package ru.tsystems.javaschool.cellular.dao.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.javaschool.cellular.dao.api.ClientDAO;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.OBJECT_NOT_FOUND;
import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.PERSISTENCE_EXCEPTION;

/**
 * Created by ferh on 09.10.14.
 */
@Repository("ClientDAO")
public class ClientDAOImpl extends CommonDAOImpl<Client> implements ClientDAO {

    public ClientDAOImpl() {
        super(Client.class);
    }

    public Client getClientByEmail(String email) throws DAOException {
        try {
            Query query = entityManager.createQuery("select c from Client c where c.email=:email")
                    .setParameter("email", email);
            Client client = (Client) query.getSingleResult();
            return client;
        } catch (NoResultException e) {
            throw new DAOException(OBJECT_NOT_FOUND, e);
        } catch (PersistenceException e) {
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public Client getClientByNumber(String phoneNumber) throws DAOException {
        try {
            return (Client) entityManager.createQuery("select c.client from Contract c where c.phoneNumber =:number")
                    .setParameter("number", phoneNumber).getSingleResult();
        } catch (NoResultException e) {
            throw new DAOException(OBJECT_NOT_FOUND, e);
        } catch (PersistenceException e) {
            throw new DAOException("Unable to get contract with phone number: " + phoneNumber, e);
        }
    }
}