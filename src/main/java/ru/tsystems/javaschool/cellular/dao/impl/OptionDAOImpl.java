package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.OptionDAO;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class OptionDAOImpl implements OptionDAO {
    EntityManager entityManager;

    public OptionDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Option option) throws DAOException {
        try {
            entityManager.persist(option);
        } catch (PersistenceException e) {
            throw new DAOException("Unable to create option: " + option, e);
        }
    }

    @Override
    public Option read(long id) throws DAOException {
        try {
            return entityManager.find(Option.class, id);
        } catch (PersistenceException e) {
            throw new DAOException("Unable to read option with id: " + id, e);
        }
    }

    @Override
    public void update(Option option) throws DAOException {
        try {
            entityManager.merge(option);
        } catch (PersistenceException e) {
            throw new DAOException("Unable to update option: " + option, e);
        }
    }

    @Override
    public void delete(Option option) {
        try {
            entityManager.remove(option);
        } catch (PersistenceException e) {

        }
    }

    public List<Option> getAll() {
        return entityManager.createNamedQuery("Option.getAll", Option.class).getResultList();
    }

    public List<Option> getOptionsForTariff(String title) {
        Query query = entityManager.createQuery("select t.options from Tariff t where t.title=:tariff_title")
                .setParameter("tariff_title", title);
        return query.getResultList();
    }
}
