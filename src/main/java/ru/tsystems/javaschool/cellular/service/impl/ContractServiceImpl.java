package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.cellular.dao.api.ClientDAO;
import ru.tsystems.javaschool.cellular.dao.api.ContractDAO;
import ru.tsystems.javaschool.cellular.dao.api.OptionDAO;
import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.service.api.ContractService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ferh on 11.10.14.
 */
@Transactional
@Service("ContractService")
public class ContractServiceImpl implements ContractService {

    private static final Logger logger = Logger.getLogger(ContractService.class);

    @Autowired
    private ContractDAO contractDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private OptionDAO optionDAO;
    @Autowired
    private TariffDAO tariffDAO;

    @Override
    public void createContract(Contract contract) throws ContractException {
        try {
            logger.info("Creating contract: " + contract);
            contractDAO.create(contract);
        } catch (DAOException e) {
            logger.error("Error while creating contract: " + contract);
            throw new ContractException("Error while creating contract: " + contract.getPhoneNumber());
        }
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
        try {
            logger.info("Updating contract: " + contract);
            contractDAO.update(contract);
        } catch (DAOException e) {
            logger.error("Error while updating contract: " + contract);
            throw new ContractException("Error while updating contract: " + contract.getPhoneNumber());
        }
    }

    @Override
    public void deleteContract(Contract contract) throws ContractException {
        try {
            logger.info("Deleting contract: " + contract);
            contractDAO.delete(contract);
        } catch (DAOException e) {
            logger.error("Error while deleting contract: " + contract);
            throw new ContractException("Error while deleting contract: " + contract.getPhoneNumber());
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
            updateContract(contract);
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
            updateContract(contract);
        }

    }

    @Override
    public void block(Contract contract) throws ContractException {
        if (!contract.isBlockedByClient()) {
            logger.info("Blocking contract: " + contract + " by client");
            contract.setBlockedByClient(true);
            updateContract(contract);
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
            updateContract(contract);
        }
    }

    @Override
    public void changeTariff(Contract contract, Tariff tariff, Set<Option> options) throws ContractException, OptionException {
        int totalAmount = tariff.getCost();
        for (Option option : options) {
            totalAmount += (option.getCost() + option.getActivationCost());
        }
        if (contract.getBalance() < totalAmount) {
            logger.error("Not enough money to add: " + tariff + " and options to contract: " + contract);
            throw new ContractException("Not enough money. Need: " + totalAmount + ". Got: " + contract.getBalance());
        }
        validateOptions(options);
        logger.info("Changing tarriff to: " + tariff);
        contract.setTariff(tariff);
        contract.getOptions().clear();
        enableOptions(contract, options);
        contract.setBalance(contract.getBalance() - tariff.getCost());
        updateContract(contract);
    }

    @Override
    public void disableOption(Contract contract, Option option) throws OptionException, ContractException {
        Set<Option> optionSet = contract.getOptions();
        for (Option srcOption : optionSet) {
            if (srcOption.getRequiredOptions().contains(option)) {
                logger.error("Option: " + srcOption.getTitle() + " requires " + option.getTitle());
                throw new OptionException("Option: " + srcOption.getTitle() + " requires " + option.getTitle());
            }
        }
        logger.info("Disabling option: " + option);
        optionSet.remove(option);
        updateContract(contract);
    }

    @Override
    public void enableOptions(Contract contract, Set<Option> optionSet) throws ContractException, OptionException {
        int totalAmount = 0;
        for (Option option : optionSet) {
            totalAmount += (option.getCost() + option.getActivationCost());
        }
        if (contract.getBalance() < totalAmount) {
            logger.error("Not enough money. Need: " + totalAmount + ". Got: " + contract.getBalance());
            throw new ContractException("Not enough money. Need: " + totalAmount + ". Got: " + contract.getBalance());
        }
        Set<Option> options = new HashSet<Option>();
        options.addAll(optionSet);
        options.addAll(contract.getOptions());
        validateOptions(options);
        contract.setBalance(contract.getBalance() - totalAmount);
        logger.info("Contract: " + contract.getPhoneNumber() + ". Enabling options: " + optionSet);
        for (Option option : optionSet) {
            contract.addOptions(option);
        }
        updateContract(contract);
    }

    @Override
    public void addContract(Contract contract, Client client, long tariffId, long[] optionIds) throws ContractException, OptionException {
        try {
            Set<Option> optionSet = new HashSet<Option>();
            for (long id : optionIds) {
                optionSet.add(optionDAO.get(id));
            }
            validateOptions(optionSet);
            contractDAO.create(contract);
            client.addContract(contract);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(client.getPassword());
            client.setPassword(hashedPassword);
            clientDAO.create(client);
            contract.setClient(client);
            contract.setTariff(tariffDAO.get(tariffId));
            enableOptions(contract, optionSet);
            contractDAO.update(contract);
            logger.info("New contract was created: " + contract);
        } catch (DAOException e) {
            logger.error("Unable to add contract. DAOException was caught.");
            throw new ContractException("Unable to add contract");
        }
    }

    @Override
    public void addOneMoreContract(Contract contract, Client client, long tariffId, long[] optionIds) throws ContractException, OptionException {
        try {
            if (contractDAO.checkIfNumberExists(contract.getPhoneNumber())) {
                logger.error("Number: " + contract.getPhoneNumber() + " already exists");
                throw new ContractException("Number: " + contract.getPhoneNumber() + " already exists");
            }
            Set<Option> optionSet = new HashSet<Option>();
            for (long id : optionIds) {
                optionSet.add(optionDAO.get(id));
            }
            validateOptions(optionSet);
            contractDAO.create(contract);
            client.addContract(contract);
            contract.setClient(client);
            contract.setTariff(tariffDAO.get(tariffId));
            enableOptions(contract, optionSet);
            contractDAO.update(contract);
            logger.info("Another contract was added: " + contract);
        } catch (DAOException e) {
            logger.error("Unable to add contract");
            throw new ContractException("Unable to add contract");
        }

    }

    @Override
    public boolean checkIfNumberExists(String number) throws ContractException {
        try {
            String regex = "[0-9]+";
            if ((number.length() != 11) || !number.matches(regex)) {
                logger.error("Invalid phone format. Must contain 11 digits");
                throw new ContractException("Invalid phone format. Must contain 11 digits");
            }
            return contractDAO.checkIfNumberExists(number);
        } catch (DAOException e) {
            logger.error("Unable to check number. Caught: DAOException");
            throw new ContractException("Unable to check.");
        }
    }

    @Override
    public void validateOptions(Set<Option> set) throws OptionException {
        for (Option option : set) {
            for (Option incompatibleOption : option.getIncompatibleOptions()) {
                for (Option srcOption : set) {
                    if (srcOption.equals(incompatibleOption)) {
                        logger.error("Option: " + option.getTitle() + " is incompatible with: " + srcOption.getTitle());
                        throw new OptionException("Option: " + option.getTitle() + " is incompatible with: " + srcOption.getTitle());
                    }
                }
            }
            for (Option requiredOption : option.getRequiredOptions()) {
                if (!set.contains(requiredOption)) {
                    logger.error("Option: " + option.getTitle() + " requires: " + requiredOption.getTitle());
                    throw new OptionException("Option: " + option.getTitle() + " requires: " + requiredOption.getTitle());
                }
            }
        }
    }
}