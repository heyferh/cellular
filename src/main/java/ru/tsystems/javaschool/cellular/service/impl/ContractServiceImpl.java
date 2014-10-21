package ru.tsystems.javaschool.cellular.service.impl;

import ru.tsystems.javaschool.cellular.dao.api.ContractDAO;
import ru.tsystems.javaschool.cellular.dao.impl.ContractDAOImpl;
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

    private EntityManager entityManager;
    private ContractDAO contractDAO;

    public ContractServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.contractDAO = new ContractDAOImpl(entityManager);
    }

    @Override
    public void createContract(Contract contract) throws ContractException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            contractDAO.create(contract);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new ContractException();
        }finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public Contract getContractById(long id) throws ContractException {
        try {
            return contractDAO.read(id);
        } catch (DAOException e) {
            throw new ContractException();
        }
    }

    @Override
    public List<Contract> getAllContracts() throws ContractException {
        try {
            return contractDAO.getAll();
        } catch (DAOException e) {
            throw new ContractException();
        }
    }

    @Override
    public void updateContract(Contract contract) throws ContractException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            contractDAO.update(contract);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new ContractException();
        }finally {
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
            contractDAO.delete(contract);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new ContractException();
        }finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void forceBlock(Contract contract) throws ContractException {
        if (contract.isBlockedByOperator()) {
            throw new ContractException("Phone number: " + contract.getPhoneNumber() + " is already blocked by operator");
        } else {
            contract.setBlockedByOperator(true);
        }

    }

    @Override
    public void forceUnblock(Contract contract) throws ContractException {
        if (!contract.isBlockedByOperator()) {
            throw new ContractException("Phone number: " + contract.getPhoneNumber() + " is already unblocked by operator");
        } else {
            contract.setBlockedByOperator(false);
        }

    }

    @Override
    public void block(Contract contract) throws ContractException {
        if (!contract.isBlockedByClient()) {
            contract.setBlockedByClient(true);
        } else {
            throw new ContractException("Contract " + contract + " is already blocked");
        }
    }

    @Override
    public void unblock(Contract contract) throws ContractException {
        if (contract.isBlockedByOperator()) {
            throw new ContractException("Unavailable operation. Phone number: " + contract.getPhoneNumber() + " is blocked by operator");
        } else if (contract.isBlockedByClient()) {
            contract.setBlockedByClient(false);
        }
    }

    @Override
    public void changeTariff(Contract contract, Tariff tariff) {
        contract.setTariff(tariff);
        contract.getOptions().clear();
    }

    @Override
    public void disableOption(Contract contract, Option option) throws OptionException {
        Set<Option> optionSet = contract.getOptions();
        for (Option srcOption : optionSet) {
            if (srcOption.getRequiredOptions().contains(option)) {
                throw new OptionException("Option: " + srcOption.getTitle() + " requires " + option.getTitle());
            }
        }
        optionSet.remove(option);
    }

    @Override
    public void enableOption(Contract contract, Option option) throws OptionException {
        Set<Option> optionSet = contract.getOptions();
        for (Option incompatibleOption : option.getIncompatibleOptions()) {
            if (optionSet.contains(incompatibleOption)) {
                throw new OptionException("Option: " + option.getTitle() + " is incompatible with option: " + incompatibleOption.getTitle());
            }
        }
        for (Option srcOption : optionSet) {
            if (srcOption.getIncompatibleOptions().contains(option)) {
                throw new OptionException("Option: " + srcOption.getTitle() + " contains the: " + option.getTitle() + " as incompatible");
            }
        }
        for (Option requiredOption : option.getRequiredOptions()) {
            if (!optionSet.contains(requiredOption)) {
                throw new OptionException("Option: " + option.getTitle() + " requires " + requiredOption.getTitle() + " to be enable");
            }
        }
        optionSet.add(option);
    }
}