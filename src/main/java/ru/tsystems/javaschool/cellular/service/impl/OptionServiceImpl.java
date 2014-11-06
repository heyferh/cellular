package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.javaschool.cellular.dao.api.OptionDAO;
import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.service.api.OptionService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ferh on 11.10.14.
 */
@Service("optionService")
public class OptionServiceImpl implements OptionService {
    private final Logger logger = Logger.getLogger(OptionService.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private OptionDAO optionDAO;
    @Autowired
    private TariffDAO tariffDAO;

    @Override
    public void createOption(Option option) throws OptionException {
        logger.info("Creating option: " + option);
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            optionDAO.create(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Unable to create option: " + option);
            throw new OptionException("Unable to create option: " + option.getTitle());
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public Option getOptionById(long id) throws OptionException {
        try {
            logger.info("Getting option by id: " + id);
            return optionDAO.get(id);
        } catch (DAOException e) {
            logger.error("Unable to get option by id: " + id);
            throw new OptionException("Unable to get option by id: " + id);
        }
    }

    @Override
    public List<Option> getAllOptions() throws OptionException {
        try {
            logger.info("Getting all options");
            return optionDAO.getAll();
        } catch (DAOException e) {
            logger.error("Unable to get all options");
            throw new OptionException("Unable to get all options");
        }
    }

    @Override
    public void updateOption(Option option) throws OptionException {
        logger.info("Updating option: " + option);
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            optionDAO.update(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Unable to update option: " + option);
            throw new OptionException("Unable to update option: " + option.getTitle());
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void deleteOption(Option option) throws OptionException {
        logger.info("Deleting option: " + option);
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (Tariff tariff : tariffDAO.getAll()) {
                if (tariff.getOptions().contains(option)) {
                    logger.error("Unable to delete option: " + option.getTitle() + ". There is tariff: " + tariff.getTitle() + " using this option");
                    throw new OptionException("Unable to delete option: " + option.getTitle() + ". There is tariff: " + tariff.getTitle() + " using this option");
                }
            }
            optionDAO.delete(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Unable to delete option: " + option);
            throw new OptionException("Unable to delete option: " + option.getTitle());
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public List<Option> getOptionsForTariff(long tariff_id) throws OptionException {
        logger.info("Getting options for tariff: " + tariff_id);
        try {
            return optionDAO.getOptionsForTariff(tariff_id);
        } catch (DAOException e) {
            logger.error("Unable to get options for tariff: " + tariff_id);
            throw new OptionException("Unable to get options for tariff: " + tariff_id);
        }
    }

    @Override
    public void manageIncompatibleOptions(long id, String[] ids) throws OptionException {
        logger.info("Managing incompatible options");
        Option option = null;
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            option = optionDAO.get(id);
            for (Option option1 : option.getIncompatibleOptions()) {
                logger.info("Editing incompatible options for: " + option1 + ". Deleting: " + option);
                option1.getIncompatibleOptions().remove(option);
            }
            option.getIncompatibleOptions().clear();
            if (ids != null) {
                Option incompatibleOption = null;
                for (String optionId : ids) {
                    incompatibleOption = optionDAO.get(Long.parseLong(optionId));
                    logger.info("Editing incompatible options for: " + option + ". Adding: " + incompatibleOption);
                    option.addIncompatibleOptions(incompatibleOption);
                    logger.info("Editing incompatible options for: " + incompatibleOption + ". Adding: " + option);
                    incompatibleOption.addIncompatibleOptions(option);
                    optionDAO.update(incompatibleOption);
                }
            }
            optionDAO.update(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while editing incompatible options");
            throw new OptionException("Error while editing incompatible options");
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void manageRequiredOptions(long id, String[] ids) throws OptionException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Option option = optionDAO.get(id);
            option.getRequiredOptions().clear();
            if (ids != null) {
                for (String optionId : ids) {
                    logger.info("Editing required options for: " + option + ". Adding: " + optionId);
                    option.addRequiredOptions(optionDAO.get(Long.parseLong(optionId)));
                }
            }
            optionDAO.update(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while editing required options");
            throw new OptionException("Error while editing required options");
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }
}
