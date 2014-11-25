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
import ru.tsystems.javaschool.cellular.service.api.OptionService;

import java.util.List;

/**
 * Created by ferh on 11.10.14.
 */
@Transactional
@Service("OptionService")
public class OptionServiceImpl implements OptionService {
    @Autowired
    private Logger logger;

    @Autowired
    private ContractDAO contractDAO;

    @Autowired
    private OptionDAO optionDAO;

    @Autowired
    private TariffDAO tariffDAO;

    @Override
    public void createOption(Option option) throws OptionException {
        logger.info("Creating option: " + option);
        try {
            optionDAO.create(option);
        } catch (DAOException e) {
            logger.error("Unable to create option: " + option);
            throw new OptionException("Unable to create option: " + option.getTitle());
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
        try {
            optionDAO.update(option);
        } catch (DAOException e) {
            logger.error("Unable to update option: " + option);
            throw new OptionException("Unable to update option: " + option.getTitle());
        }
    }

    @Override
    public void deleteOption(Option option) throws OptionException {
        logger.info("Deleting option: " + option);
        try {
            for (Tariff tariff : tariffDAO.getAll()) {
                if (tariff.getOptions().contains(option)) {
                    logger.error("Unable to delete option: " + option.getTitle() + ". There is tariff: " + tariff.getTitle() + " using this option");
                    throw new OptionException("Unable to delete option: " + option.getTitle() + ". There is tariff: " + tariff.getTitle() + " using this option");
                }
            }
            for (Contract contract : contractDAO.getAll()) {
                if (contract.getOptions().contains(option)) {
                    logger.error("Unable to delete option: " + option.getTitle() + ". There is contract: " + contract.getPhoneNumber() + " using this option");
                    throw new OptionException("Unable to delete option: " + option.getTitle() + ". There is contract: " + contract.getPhoneNumber() + " using this option");
                }
            }
            for (Option srcOption : optionDAO.getAll()) {
                if (srcOption.getRequiredOptions().contains(option)) {
                    logger.error("Unable to delete option: " + option.getTitle() + ". It is required for option: " + srcOption.getTitle());
                    throw new OptionException("Unable to delete option: " + option.getTitle() + ". It is required for option: " + srcOption.getTitle());
                }
            }
            for (Option srcOption : optionDAO.getAll()) {
                if (srcOption.getIncompatibleOptions().contains(option)) {
                    srcOption.getIncompatibleOptions().remove(option);
                }
            }
            optionDAO.delete(option);
        } catch (DAOException e) {
            logger.error("Unable to delete option: " + option);
            throw new OptionException("Unable to delete option: " + option.getTitle());
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
    public void manageIncompatibleOptions(long id, long[] ids) throws OptionException {
        logger.info("Managing incompatible options");
        Option option = null;
        try {
            option = optionDAO.get(id);
            for (Option option1 : option.getIncompatibleOptions()) {
                logger.info("Editing incompatible options for: " + option1 + ". Deleting: " + option);
                option1.getIncompatibleOptions().remove(option);
            }
            option.getIncompatibleOptions().clear();
            if (ids != null) {
                Option incompatibleOption = null;
                for (long optionId : ids) {
                    incompatibleOption = optionDAO.get(optionId);
                    logger.info("Editing incompatible options for: " + option + ". Adding: " + incompatibleOption);
                    option.addIncompatibleOptions(incompatibleOption);
                    logger.info("Editing incompatible options for: " + incompatibleOption + ". Adding: " + option);
                    incompatibleOption.addIncompatibleOptions(option);
                    optionDAO.update(incompatibleOption);
                }
            }
            optionDAO.update(option);
        } catch (DAOException e) {
            logger.error("Error while editing incompatible options");
            throw new OptionException("Error while editing incompatible options");
        }
    }

    @Override
    public void manageRequiredOptions(long id, long[] ids) throws OptionException {
        try {
            Option option = optionDAO.get(id);
            option.getRequiredOptions().clear();
            if (ids != null) {
                for (long optionId : ids) {
                    logger.info("Editing required options for: " + option + ". Adding: " + optionId);
                    option.addRequiredOptions(optionDAO.get(optionId));
                }
            }
            optionDAO.update(option);
        } catch (DAOException e) {
            logger.error("Error while editing required options");
            throw new OptionException("Error while editing required options");
        }
    }
}
