package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class TariffDAOImpl implements TariffDAO {
    EntityManager entityManager;

    public TariffDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Tariff tariff) throws DAOException {
        try {
            entityManager.persist(tariff);
        } catch (PersistenceException e) {
            throw new DAOException("Unable to create tariff: " + tariff, e);
        }
    }

    @Override
    public Tariff read(long id) throws DAOException {
        try {
            return entityManager.find(Tariff.class, id);
        } catch (PersistenceException e) {
            throw new DAOException("Unable to get tariff with id: " + id, e);
        }
    }

    @Override
    public void update(Tariff tariff) throws DAOException {
        try {
            entityManager.merge(tariff);
        } catch (PersistenceException e) {
            throw new DAOException("Unable to update tariff: " + tariff, e);
        }
    }

    @Override
    public void delete(Tariff tariff) throws DAOException {
        try {
            entityManager.remove(tariff);
        } catch (PersistenceException e) {
            throw new DAOException("Unable to delete tariff: " + tariff, e);
        }
    }

    public List<Tariff> getAll() throws DAOException {
        try {
            return entityManager.createNamedQuery("Tariff.getAll", Tariff.class).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Unable to get all tariffs", e);
        }
    }
}
