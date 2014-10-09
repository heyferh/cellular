package ru.tsystems.javaschool.cellular.dao.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by ferh on 09.10.14.
 */
public class AbstractDAO<T> {
    protected final EntityManager entityManager;
    protected Class<T> type;

    public AbstractDAO(EntityManager entityManager, Class<T> type) {
        this.entityManager = entityManager;
        this.type = type;
    }

    public void create(T object) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

    public T read(long id) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        T element;
        try {
            entityManager.getTransaction().begin();
            element = entityManager.find(type, id);
            entityManager.getTransaction().commit();
            return element;
        } catch (RuntimeException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

    public void update(T o) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(o);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {

            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

    public void delete(T persistentObject) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(persistentObject);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

}