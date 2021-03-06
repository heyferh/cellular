package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.cellular.dao.api.ClientDAO;
import ru.tsystems.javaschool.cellular.dao.api.ContractDAO;
import ru.tsystems.javaschool.cellular.dao.api.OptionDAO;
import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.dao.impl.ClientDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.ContractDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.OptionDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.TariffDAOImpl;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.service.api.ContractService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Set;

/**
 * Created by ferh on 11.10.14.
 */
public class ContractServiceImpl implements ContractService {
    private final Logger logger = Logger.getLogger(ContractService.class);
    private EntityManager entityManager;
    private ContractDAO contractDAO;
    private ClientDAO clientDAO;
    private OptionDAO optionDAO;
    private TariffDAO tariffDAO;

    public ContractServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.contractDAO = new ContractDAOImpl(entityManager);
        this.clientDAO = new ClientDAOImpl(entityManager);
        this.optionDAO = new OptionDAOImpl(entityManager);
        this.tariffDAO = new TariffDAOImpl(entityManager);
    }

    @Override
    public void createContract(Contract contract) throws ContractException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            logger.info("Creating contract: " + contract);
            contractDAO.create(contract);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while creating contract: " + contract);
            throw new ContractException("Error while creating contract: " + contract.getPhoneNumber());
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public Contract getContractById(long id) throws ContractException {
        try {
            logger.info("Getting contract by id: " + id);
            return contractDAO.get(id);
        } catch (DAOException e) {
            logger.error("Error while getting contract by id: " + id);
            throw new ContractException("Error while getting contract by id: " + id);
        }
    }

    @Override
    public List<Contract> getAllContracts() throws ContractException {
        try {
            logger.info("Getting all contracts");
            return contractDAO.getAll();
        } catch (DAOException e) {
            logger.error("Error while getting all contracts");
            throw new ContractException("Error while getting all contracts");
        }
    }

    @Override
    public void updateContract(Contract contract) throws ContractException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            logger.info("Updating contract: " + contract);
            contractDAO.update(contract);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while updating contract: " + contract);
            throw new ContractException("Error while updating contract: " + contract.getPhoneNumber());
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void deleteContract(Contract contract) throws ContractException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            logger.info("Deleting contract: " + contract);
            contractDAO.delete(contract);
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Error while deleting contract: " + contract);
            throw new ContractException("Error while deleting contract: " + contract.getPhoneNumber());
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void forceBlock(Contract contract) throws ContractException {
        if (contract.isBlockedByOperator()) {
            logger.warn("Phone number: " + contract.getPhoneNumber() + " is already blocked by operator");
            throw new ContractException("Phone number: " + contract.getPhoneNumber() + " is already blocked by operator");
        } else {
            logger.info("Blocking contract: " + contract + " by operator");
            contract.setBlockedByOperator(true);
        }
    }

    @Override
    public void forceUnblock(Contract contract) throws ContractException {
        if (!contract.isBlockedByOperator()) {
            logger.warn("Phone number: " + contract.getPhoneNumber() + " is already unblocked by operator");
            throw new ContractException("Phone number: " + contract.getPhoneNumber() + " is already unblocked by operator");
        } else {
            logger.info("Unblocking contract by operator: " + contract);
            contract.setBlockedByOperator(false);
        }

    }

    @Override
    public void block(Contract contract) throws ContractException {
        if (!contract.isBlockedByClient()) {
            logger.info("Blocking contract: " + contract + " by client");
            contract.setBlockedByClient(true);
        } else {
            logger.warn("Contract " + contract + " is already blocked");
            throw new ContractException("Contract " + contract.getPhoneNumber() + " is already blocked");
        }
    }

    @Override
    public void unblock(Contract contract) throws ContractException {
        if (contract.isBlockedByOperator()) {
            logger.warn("Unavailable operation. Phone number: " + contract.getPhoneNumber() + " is blocked by operator");
            throw new ContractException("Unavailable operation. Phone number: " + contract.getPhoneNumber() + " is blocked by operator");
        } else if (contract.isBlockedByClient()) {
            logger.info("Unblocking contract: " + contract + " by client");
            contract.setBlockedByClient(false);
        }
    }

    @Override
    public void changeTariff(Contract contract, Tariff tariff) throws ContractException {
        if (contract.getBalance() < tariff.getCost()) {
            logger.error("Not enough money to connect: " + tariff + " to contract: " + contract);
            throw new ContractException("Not enough money");
        }
        logger.info("Changing tarriff to: " + tariff);
        contract.setBalance(contract.getBalance() - tariff.getCost());
        contract.setTariff(tariff);
        contract.getOptions().clear();
    }

    @Override
    public void disableOption(Contract contract, Option option) throws OptionException {
        logger.info("Disabling option: " + option);
        Set<Option> optionSet = contract.getOptions();
        for (Option srcOption : optionSet) {
            if (srcOption.getRequiredOptions().contains(option)) {
                logger.error("Option: " + srcOption.getTitle() + " requires " + option.getTitle());
                throw new OptionException("Option: " + srcOption.getTitle() + " requires " + option.getTitle());
            }
        }
        logger.info("Disabling option: " + option);
        optionSet.remove(option);
    }

    @Override
    public void enableOption(Contract contract, Option option) throws OptionException, ContractException {
        logger.info("Enabling option: " + option);
        Set<Option> optionSet = contract.getOptions();
        for (Option incompatibleOption : option.getIncompatibleOptions()) {
            if (optionSet.contains(incompatibleOption)) {
                logger.error("Option: " + option.getTitle() + " is incompatible with option: " + incompatibleOption.getTitle());
                throw new OptionException("Option: " + option.getTitle() + " is incompatible with option: " + incompatibleOption.getTitle());
            }
        }
        for (Option srcOption : optionSet) {
            if (srcOption.getIncompatibleOptions().contains(option)) {
                logger.error("Option: " + srcOption.getTitle() + " contains the: " + option.getTitle() + " as incompatible");
                throw new OptionException("Option: " + srcOption.getTitle() + " contains the: " + option.getTitle() + " as incompatible");
            }
        }
        for (Option requiredOption : option.getRequiredOptions()) {
            if (!optionSet.contains(requiredOption)) {
                logger.error("Option: " + option.getTitle() + " requires " + requiredOption.getTitle() + " to be enable");
                throw new OptionException("Option: " + option.getTitle() + " requires " + requiredOption.getTitle() + " to be enable");
            }
        }
        logger.info("Adding option: " + option);
        if (contract.getBalance() < option.getActivationCost() + option.getCost()) {
            logger.error("Not enough money to activate option: " + option + " to contract: " + contract);
            throw new ContractException("Not enough money");
        }
        contract.setBalance(contract.getBalance() - option.getActivationCost() - option.getCost());
        optionSet.add(option);
    }

    @Override
    public void addContract(Contract contract, Client client, long tariffId, long[] optionIds) throws ContractException, OptionException {
        logger.info("Adding new contract: " + contract);
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            if (contractDAO.checkIfNumberExists(contract.getPhoneNumber())) {
                logger.error("Number: " + contract.getPhoneNumber() + " already exists");
                throw new ContractException("Number: " + contract.getPhoneNumber() + " already exists");
            }
            contractDAO.create(contract);
            client.addContract(contract);
            clientDAO.create(client);
            contract.setClient(client);
            contract.setTariff(tariffDAO.get(tariffId));
            for (long id : optionIds) {
                logger.info("Enabling option: " + id);
                enableOption(contract, optionDAO.get(id));
            }
            entityTransaction.commit();
        } catch (DAOException e) {
            logger.error("Unable to add contract");
            throw new ContractException("Unable to add contract");
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

}