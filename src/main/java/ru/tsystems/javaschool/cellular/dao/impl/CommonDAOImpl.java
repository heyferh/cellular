package ru.tsystems.javaschool.cellular.dao.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.javaschool.cellular.dao.api.CommonDAO;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.*;

/**
 * Created by ferh on 22.10.14.
 */
@Repository
public abstract class CommonDAOImpl<T> implements CommonDAO<T> {

    @PersistenceContext
    EntityManager entityManager;

    private Class<T> type;

    protected CommonDAOImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T object) throws DAOException {
        try {
            entityManager.persist(object);
        } catch (EntityExistsException e) {
            throw new DAOException(OBJECT_ALREADY_EXISTS, e);
        } catch (PersistenceException e) {
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public T get(long id) throws DAOException {
        T object = null;
        try {
            object = (T) entityManager.find(type, id);
            if (object == null) {
                throw new DAOException(OBJECT_NOT_FOUND);
            }
            return object;
        } catch (PersistenceException e) {
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public List<T> getAll() throws DAOException {
        try {
            return entityManager.createNamedQuery(type.getSimpleName() + ".getAll", type).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public void update(T object) throws DAOException {
        try {
            entityManager.merge(object);
        } catch (PersistenceException e) {
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public void delete(T object) throws DAOException {
        try {
            entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
        } catch (PersistenceException e) {
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }
}
