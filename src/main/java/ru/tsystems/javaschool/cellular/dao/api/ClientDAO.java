package ru.tsystems.javaschool.cellular.dao.api;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 16.10.14.
 */
public interface ClientDAO {
    public void create(Client client) throws DAOException;

    public Client read(long id) throws DAOException;

    public void update(Client client) throws DAOException;

    public void delete(Client client) throws DAOException;

    public Client findClientByEmail(String email) throws DAOException;

    public List<Client> getAll() throws DAOException;
}