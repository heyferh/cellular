package ru.tsystems.javaschool.cellular.dao.impl;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.cellular.dao.api.CommonDAO;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.*;

/**
 * Created by ferh on 22.10.14.
 */
public abstract class CommonDAOImpl<T> implements CommonDAO<T> {
    protected final Logger logger = Logger.getLogger("DAO");
    EntityManager entityManager;
    private Class<T> type;

    protected CommonDAOImpl(EntityManager entityManager, Class<T> type) {
        this.entityManager = entityManager;
        this.type = type;
    }

    @Override
    public void create(T object) throws DAOException {
        try {
            logger.info("Creating: " + type.getSimpleName());
            entityManager.persist(object);
        } catch (EntityExistsException e) {
            logger.error("Object: " + object + " is already exist.");
            throw new DAOException(OBJECT_ALREADY_EXISTS, e);
        } catch (PersistenceException e) {
            logger.error("Persistence exception while creating: " + object);
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public T get(long id) throws DAOException {
        T object = null;
        try {
            logger.info("Getting: " + type.getSimpleName() + " with id: " + id);
            object = (T) entityManager.find(type, id);
            if (object == null) {
                logger.error("Object with id: " + id + " not found.");
                throw new DAOException(OBJECT_NOT_FOUND);
            }
            return object;
        } catch (PersistenceException e) {
            logger.error("Getting " + type.getSimpleName() + " with id " + id + " fails.");
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public List<T> getAll() throws DAOException {
        try {
            logger.info("GetAll for " + type.getSimpleName());
            return entityManager.createNamedQuery(type.getSimpleName() + ".getAll", type).getResultList();
        } catch (PersistenceException e) {
            logger.error("GetAll for " + type.getSimpleName() + " fails.");
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public void update(T object) throws DAOException {
        try {
            logger.info("Updating " + object);
            entityManager.merge(object);
        } catch (PersistenceException e) {
            logger.error("Updating " + object + " fails.");
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public void delete(T object) throws DAOException {
        try {
            logger.info("Deleting " + object);
            entityManager.remove(object);
        } catch (PersistenceException e) {
            logger.error("Deleting " + object + " fails.");
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }
}
