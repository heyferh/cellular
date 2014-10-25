package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.cellular.dao.api.ContractDAO;
import ru.tsystems.javaschool.cellular.dao.api.OptionDAO;
import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.dao.impl.ContractDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.OptionDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.TariffDAOImpl;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferh on 11.10.14.
 */
public class TariffServiceImpl implements TariffService {
    private final Logger logger = Logger.getLogger(TariffService.class);
    private EntityManager entityManager;
    private TariffDAO tariffDAO;
    private ContractDAO contractDAO;
    private OptionDAO optionDAO;

    public TariffServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.tariffDAO = new TariffDAOImpl(entityManager);
        this.contractDAO = new ContractDAOImpl(entityManager);
        this.optionDAO = new OptionDAOImpl(entityManager);
    }

    @Override
    public void createTariff(Tariff tariff, String[] optionId) throws TariffException {
        logger.info("Creating tariff: " + tariff);
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            tariffDAO.create(tariff);

            List<Option> optionList = new ArrayList<Option>();
            for (String id : optionId) {
                optionList.add(optionDAO.get(Long.parseLong(id)));
            }
            for (Option option : optionList) {
                for (Option requiredOption : option.getRequiredOptions()) {
                    if (!optionList.contains(requiredOption)) {
                        throw new TariffException("Unable to create tariff." + " Option: " + option + " reqiores: " + requiredOption);
                    }
                }
            }
            for (Option option : optionList) {
                tariff.addOptions(option);
            }
            tariffDAO.update(tariff);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while creating tariff: " + tariff);
            throw new TariffException();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public Tariff getTariffById(long id) throws TariffException {
        logger.info("Getting tariff by id: " + id);
        try {
            return tariffDAO.get(id);
        } catch (DAOException e) {
            logger.error("Error while getting tariff by id: " + id);
            throw new TariffException();
        }
    }

    @Override
    public List<Tariff> getAllTariffs() throws TariffException {
        logger.info("Getting all tariffs");
        try {
            return tariffDAO.getAll();
        } catch (DAOException e) {
            logger.error("Error while getting all tariffs");
            throw new TariffException();
        }
    }

    @Override
    public void updateTariff(Tariff tariff) throws TariffException {
        logger.info("Updating tariff");
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            tariffDAO.update(tariff);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while updating tariff");
            throw new TariffException();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void deleteTariff(Tariff tariff) throws TariffException {
        logger.info("Deleting tariff");
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (Contract contract : contractDAO.getAll()) {
                if (contract.getTariff().equals(tariff)) {
                    logger.error("Unable to delete tariff: " + tariff + ". There is a contract: " + contract + " that uses this tariff.");
                    throw new TariffException("Unable to delete tariff: " + tariff + ". There is a contract: " + contract + " that uses this tariff.");
                }
            }
            tariffDAO.delete(tariff);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while deleting tariff");
            throw new TariffException();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void addOptionForTariff(String tariff_id, String[] optionId) throws OptionException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Tariff tariff = tariffDAO.get(Long.parseLong(tariff_id));
            List<Option> optionList = new ArrayList<Option>();
            for (String id : optionId) {
                optionList.add(optionDAO.get(Long.parseLong(id)));
            }
            for (Option option : optionList) {
                for (Option requiredOption : option.getRequiredOptions()) {
                    if (!tariff.getOptions().contains(requiredOption) && !optionList.contains(requiredOption)) {
                        throw new OptionException("Unable to add option: " + option + ".Requires: " + requiredOption);
                    }
                }
            }
            for (Option option : optionList) {
                tariff.addOptions(option);
            }
            tariffDAO.update(tariff);
            entityTransaction.commit();
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void deleteTariffOption(String tariff_id, String option_id) throws OptionException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Tariff tariff = tariffDAO.get(Long.parseLong(tariff_id));
            Option option = optionDAO.get(Long.parseLong(option_id));

            for (Option srcOption : tariff.getOptions()) {
                if (srcOption.getRequiredOptions().contains(option)) {
                    throw new OptionException("Unable to delete option while it is required for: " + srcOption);
                }
            }
            tariff.getOptions().remove(option);
            tariffDAO.update(tariff);
            entityTransaction.commit();
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

}
