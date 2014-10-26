package ru.tsystems.javaschool.cellular.dao.api;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.DAOException;

/**
 * Created by ferh on 16.10.14.
 */
public interface ClientDAO extends CommonDAO<Client> {

    /**
     * Finds client by its email.
     *
     * @param email client's email.
     * @return Client object if it exists.
     * @throws DAOException if there's no such email in database.
     */
    public Client findClientByEmail(String email) throws DAOException;

    /**
     * Finds client by phone number.
     *
     * @param phoneNumber phone number which depends on single contract in database.
     * @return Object of type Client.
     * @throws DAOException if there's no contract with given phone number.
     */
    public Client getClientByNumber(String phoneNumber) throws DAOException;
}