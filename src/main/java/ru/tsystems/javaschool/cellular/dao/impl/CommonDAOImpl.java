package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.CommonDAO;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by ferh on 22.10.14.
 */
public abstract class CommonDAOImpl<T> implements CommonDAO<T> {
    EntityManager entityManager;
    private Class<T> type;

    protected CommonDAOImpl(EntityManager entityManager, Class<T> type) {
        this.entityManager = entityManager;
        this.type = type;
    }

    @Override
    public void create(T object) throws DAOException {
        try {
            entityManager.persist(object);
        } catch (PersistenceException e) {
            throw new DAOException("Adding " + object + " fails.", e);
        }
    }

    @Override
    public T get(long id) throws DAOException {
        try {
            return (T) entityManager.find(type, id);
        } catch (PersistenceException e) {
            throw new DAOException("Getting object with id " + id + " fails.", e);
        }
    }

    @Override
    public List<T> getAll() throws DAOException {
        try {
            return entityManager.createNamedQuery(type.getSimpleName() + ".getAll", type).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Getting list of objects fails", e);
        }
    }

    @Override
    public void update(T object) throws DAOException {
        try {
            entityManager.merge(object);
        } catch (PersistenceException e) {
            throw new DAOException("Updating " + object + " fails.", e);
        }
    }

    @Override
    public void delete(T object) throws DAOException {
        try {
            entityManager.remove(object);
        } catch (PersistenceException e) {
            throw new DAOException("Deleting " + object + " fails.", e);
        }
    }
}
