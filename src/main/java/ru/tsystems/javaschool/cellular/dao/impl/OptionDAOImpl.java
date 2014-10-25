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
public class OptionDAOImpl extends CommonDAOImpl<Option> implements OptionDAO {
    public OptionDAOImpl(EntityManager entityManager) {
        super(entityManager, Option.class);
    }

    public List<Option> getOptionsForTariff(String title) throws DAOException {
        try {
            logger.info("Getting options for tariff: " + title);
            Query query = entityManager.createQuery("select t.options from Tariff t where t.title=:tariff_title")
                    .setParameter("tariff_title", title);
            return query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Unable to get options for tariff: " + title);
            throw new DAOException("Unable to get options for tariff: " + title, e);
        }
    }

    @Override
    public List<Option> getOptionsForTariff(long tariff_id) throws DAOException {
        try {
            logger.info("Getting options for tariff: " + tariff_id);
            Query query = entityManager.createQuery("select t.options from Tariff t where t.id=:tariff_id")
                    .setParameter("tariff_id", tariff_id);
            return query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Unable to get options for tariff with id: " + tariff_id);
            throw new DAOException("Unable to get options for tariff with id: " + tariff_id, e);
        }
    }
}
