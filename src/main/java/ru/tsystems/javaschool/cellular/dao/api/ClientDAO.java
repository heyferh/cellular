package ru.tsystems.javaschool.cellular.dao.api;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 16.10.14.
 */
public interface ClientDAO extends CommonDAO<Client> {

    public Client findClientByEmail(String email) throws DAOException;

    public Client getClientByNumber(String phoneNumber) throws DAOException;
}