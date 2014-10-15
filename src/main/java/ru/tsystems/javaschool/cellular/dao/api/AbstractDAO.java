package ru.tsystems.javaschool.cellular.dao.api;

import javax.persistence.EntityManager;

/**
 * Created by ferh on 09.10.14.
 */
public class AbstractDAO<T> {
    protected final EntityManager entityManager;
    protected Class<T> type;

    class TransactionManager {
        EntityManager entityManager;

        //begin()
        //commit()
        //rollback()

    }

    public AbstractDAO(EntityManager entityManager, Class<T> type) {
        this.entityManager = entityManager;
        this.type = type;
    }

    public void create(T object) {
        entityManager.persist(object);
    }

    public T read(long id) {
        return entityManager.find(type, id);
    }

    public void update(T o) {
        entityManager.merge(o);
    }

    public void delete(T persistentObject) {
        entityManager.remove(persistentObject);
    }
}