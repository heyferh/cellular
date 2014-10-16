package ru.tsystems.javaschool.cellular.dao.api;


import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 16.10.14.
 */
public interface TariffDAO {
    public void create(Tariff tariff) throws DAOException;

    public Tariff read(long id) throws DAOException;

    public void update(Tariff tariff) throws DAOException;

    public void delete(Tariff tariff) throws DAOException;

    public List<Tariff> getAll() throws DAOException;
}
