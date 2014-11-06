package ru.tsystems.javaschool.cellular.dao.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.javaschool.cellular.dao.api.OptionDAO;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.OBJECT_NOT_FOUND;
import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.PERSISTENCE_EXCEPTION;

/**
 * Created by ferh on 09.10.14.
 */
@Repository("OptionDAO")
public class OptionDAOImpl extends CommonDAOImpl<Option> implements OptionDAO {
    public OptionDAOImpl(EntityManager entityManager) {
        super(Option.class);
    }

    public List<Option> getOptionsForTariff(String title) throws DAOException {
        List<Option> optionList = null;
        try {
            logger.info("Getting options for tariff: " + title);
            Query query = entityManager.createQuery("select t.options from Tariff t where t.title=:tariff_title")
                    .setParameter("tariff_title", title);
            optionList = query.getResultList();
            if (optionList == null || optionList.size() == 0) {
                logger.error("There's no options for tariff: " + title + " yet.");
                throw new DAOException(OBJECT_NOT_FOUND);
            }
            return optionList;
        } catch (PersistenceException e) {
            logger.error("Unable to get options for tariff: " + title);
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public List<Option> getOptionsForTariff(long tariff_id) throws DAOException {
        List<Option> optionList = null;
        try {
            logger.info("Getting options for tariff: " + tariff_id);
            Query query = entityManager.createQuery("select t.options from Tariff t where t.id=:tariff_id")
                    .setParameter("tariff_id", tariff_id);
            optionList = query.getResultList();
            if (optionList == null || optionList.size() == 0) {
                logger.error("There's no options for tariff: " + tariff_id + " yet.");
                throw new DAOException(OBJECT_NOT_FOUND);
            }
            return optionList;
        } catch (PersistenceException e) {
            logger.error("Unable to get options for tariff with id: " + tariff_id);
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }
}
