package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.cellular.dao.api.ContractDAO;
import ru.tsystems.javaschool.cellular.dao.api.OptionDAO;
import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferh on 11.10.14.
 */
@Transactional
@Service("TariffService")
public class TariffServiceImpl implements TariffService {

    @Autowired
    private Logger logger;

    @Autowired
    private TariffDAO tariffDAO;

    @Autowired
    private ContractDAO contractDAO;

    @Autowired
    private OptionDAO optionDAO;

    @Override
    public void createTariff(Tariff tariff, long[] optionId) throws TariffException {
        logger.info("Creating tariff: " + tariff);
        try {
            List<Option> optionList = new ArrayList<Option>();
            if (optionId != null)
                for (long id : optionId) {
                    optionList.add(optionDAO.get(id));
                }
            for (Option option : optionList) {
                for (Option requiredOption : option.getRequiredOptions()) {
                    if (!optionList.contains(requiredOption)) {
                        throw new TariffException("Unable to create tariff." + " Option: " + option.getTitle() + " requires: " + requiredOption.getTitle());
                    }
                }
            }
            tariffDAO.create(tariff);
            for (Option option : optionList) {
                tariff.addOptions(option);
            }
            tariffDAO.update(tariff);
        } catch (DAOException e) {
            logger.error("Error while creating tariff: " + tariff);
            throw new TariffException("Error while creating tariff: " + tariff.getTitle());
        }
    }

    @Override
    public Tariff getTariffById(long id) throws TariffException {
        logger.info("Getting tariff by id: " + id);
        try {
            return tariffDAO.get(id);
        } catch (DAOException e) {
            logger.error("Error while getting tariff by id: " + id);
            throw new TariffException("Error while getting tariff by id: " + id);
        }
    }

    @Override
    public List<Tariff> getAllTariffs() throws TariffException {
        logger.info("Getting all tariffs");
        try {
            return tariffDAO.getAll();
        } catch (DAOException e) {
            logger.error("Error while getting all tariffs");
            throw new TariffException("Error while getting all tariffs");
        }
    }

    @Override
    public void updateTariff(Tariff tariff) throws TariffException {
        logger.info("Updating tariff");
        try {
            tariffDAO.update(tariff);
        } catch (DAOException e) {
            logger.error("Error while updating tariff");
            throw new TariffException("Error while updating tariff");
        }
    }

    @Override
    public void deleteTariff(Tariff tariff) throws TariffException {
        logger.info("Deleting tariff");
        try {
            for (Contract contract : contractDAO.getAll()) {
                if (contract.getTariff().equals(tariff)) {
                    logger.error("Unable to delete tariff: " + tariff + ". There is a contract: " + contract + " that uses this tariff.");
                    throw new TariffException("Unable to delete tariff: " + tariff.getTitle() + ". There is a contract: " + contract.getPhoneNumber() + " that uses this tariff.");
                }
            }
            tariffDAO.delete(tariff);
        } catch (DAOException e) {
            logger.error("Error while deleting tariff");
            throw new TariffException("Error while deleting tariff");
        }
    }

    @Override
    public void addOptionForTariff(long tariff_id, long[] optionId) throws OptionException {
        try {
            Tariff tariff = tariffDAO.get(tariff_id);
            List<Option> optionList = new ArrayList<Option>();
            for (long id : optionId) {
                optionList.add(optionDAO.get(id));
            }
            for (Option option : optionList) {
                for (Option requiredOption : option.getRequiredOptions()) {
                    if (!tariff.getOptions().contains(requiredOption) && !optionList.contains(requiredOption)) {
                        throw new OptionException("Unable to add option: " + option.getTitle() + ".Requires: " + requiredOption.getTitle());
                    }
                }
            }
            for (Option option : optionList) {
                tariff.addOptions(option);
            }
            tariffDAO.update(tariff);
        } catch (DAOException e) {
            throw new OptionException("Error while adding option to tariff");
        }
    }

    @Override
    public void deleteTariffOption(long tariff_id, long option_id) throws OptionException {
        try {
            Tariff tariff = tariffDAO.get(tariff_id);
            Option option = optionDAO.get(option_id);

            for (Option srcOption : tariff.getOptions()) {
                if (srcOption.getRequiredOptions().contains(option)) {
                    throw new OptionException("Unable to delete option: " + option.getTitle() + " while it is required for: " + srcOption.getTitle());
                }
            }
            tariff.getOptions().remove(option);
            tariffDAO.update(tariff);
        } catch (DAOException e) {
            throw new OptionException("Error while deletin tariff option");
        }
    }

}
