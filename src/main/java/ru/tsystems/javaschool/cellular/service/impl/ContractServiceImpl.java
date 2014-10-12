package ru.tsystems.javaschool.cellular.service.impl;

import ru.tsystems.javaschool.cellular.dao.impl.ContractDAO;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Manager;
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

    private EntityManager entityManager = Manager.getEntityManager();
    private ContractDAO contractDAO = new ContractDAO(entityManager, Contract.class);

    @Override
    public void createContract(Contract contract) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            contractDAO.create(contract);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public Contract getContractById(long id) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Contract contract = contractDAO.read(id);
            entityTransaction.commit();
            if (contract == null) throw new DAOException("Contract with id: " + id + " doesn't exist");
            return contract;
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public List<Contract> getAllContracts() {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            List<Contract> lst = contractDAO.getAll();
            entityTransaction.commit();
            if (lst.size() == 0) throw new DAOException("There is no contracts in database yet");
            return lst;
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public void updateContract(Contract contract) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            contractDAO.update(contract);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public void deleteContract(Contract contract) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            contractDAO.delete(contract);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
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
    public void block(Contract contract) {
        if (!contract.isBlockedByClient()) {
            contract.setBlockedByClient(true);
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
        for (Option srcOption : optionSet) {
            if (srcOption.getIncompatibleOptions().contains(option)) {
                throw new OptionException("Option: " + option.getTitle() + " is incompatible with option: " + srcOption.getTitle());
            }
        }
    }
}