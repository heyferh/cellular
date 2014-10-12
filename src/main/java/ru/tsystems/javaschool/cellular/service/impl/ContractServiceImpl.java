package ru.tsystems.javaschool.cellular.service.impl;

import ru.tsystems.javaschool.cellular.dao.impl.ContractDAO;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Manager;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.ContractService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

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
    public void forceBlock(Contract contract) {
        if (contract.isBlockedByOperator()) {
            throw new DAOException("Phone number: " + contract.getPhoneNumber() + " is already blocked by operator");
        } else {
            contract.setBlockedByOperator(true);
        }

    }

    @Override
    public void forceUnblock(Contract contract) {
        if (!contract.isBlockedByOperator()) {
            throw new DAOException("Phone number: " + contract.getPhoneNumber() + " is already unblocked by operator");
        } else {
            contract.setBlockedByOperator(false);
        }

    }

    @Override
    public void block(Contract contract) {
        if (!contract.isBlockedByClient()) {
            contract.setBlockedByClient(true);
        } else {
            throw new DAOException("Phone number: " + contract.getPhoneNumber() + " is already blocked by client");
        }

    }

    @Override
    public void unblock(Contract contract) {
        if (contract.isBlockedByOperator()) {
            throw new DAOException("Unavailable operation. Phone number: " + contract.getPhoneNumber() + " is blocked by operator");
        } else if (contract.isBlockedByClient()) {
            contract.setBlockedByClient(false);
        } else {
            throw new DAOException("Phone number: " + contract.getPhoneNumber() + " is already unblocked by client");
        }
    }
}
