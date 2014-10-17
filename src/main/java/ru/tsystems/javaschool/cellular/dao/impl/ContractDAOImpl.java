package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.ContractDAO;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class ContractDAOImpl implements ContractDAO {
    private EntityManager entityManager;

    public ContractDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Contract contract) throws DAOException {
        try {
            entityManager.persist(contract);
        } catch (PersistenceException e) {
            throw new DAOException("Creating contract: " + contract + " fails.", e);
        }
    }

    @Override
    public Contract read(long id) throws DAOException {
        try {
            return entityManager.find(Contract.class, id);
        } catch (PersistenceException e) {
            throw new DAOException("Reading contract with id: " + id + " fails.", e);
        }
    }

    @Override
    public void update(Contract contract) throws DAOException {
        try {
            entityManager.merge(contract);
        } catch (PersistenceException e) {
            throw new DAOException("Updating contract: " + contract + " fails.", e);
        }
    }

    @Override
    public void delete(Contract contract) throws DAOException {
        try {
            entityManager.remove(contract);
        } catch (PersistenceException e) {
            throw new DAOException("Deleting contract: " + contract + " fails.", e);
        }
    }

    public Contract getContractByPhoneNumber(String phoneNumber) throws DAOException {
        try {
            return (Contract) entityManager.createQuery("select c from Contract c where c.phoneNumber=:number")
                    .setParameter("number", phoneNumber).getSingleResult();
        } catch (PersistenceException e) {
            throw new DAOException("Unable to get contract with phone number: " + phoneNumber, e);
        }
    }

    public List<Contract> getAll() throws DAOException {
        try {
            return entityManager.createNamedQuery("Contract.getAll", Contract.class).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Unable to get all contracts.", e);
        }
    }

}
