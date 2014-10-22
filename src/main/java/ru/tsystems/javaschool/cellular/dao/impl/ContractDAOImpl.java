package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.ContractDAO;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * Created by ferh on 09.10.14.
 */
public class ContractDAOImpl extends CommonDAOImpl<Contract> implements ContractDAO {
    public ContractDAOImpl(EntityManager entityManager) {
        super(entityManager, Contract.class);
    }

    public Contract getContractByPhoneNumber(String phoneNumber) throws DAOException {
        try {
            return (Contract) entityManager.createQuery("select c from Contract c where c.phoneNumber=:number")
                    .setParameter("number", phoneNumber).getSingleResult();
        } catch (PersistenceException e) {
            throw new DAOException("Unable to get contract with phone number: " + phoneNumber, e);
        }
    }

    @Override
    public boolean checkIfNumberExists(String number) throws DAOException {
        try {
            long count = (Long) entityManager.createQuery("select count(c.phoneNumber) from Contract c where c.phoneNumber=:number")
                    .setParameter("number", number)
                    .getSingleResult();
            return count > 0 ? true : false;
        } catch (PersistenceException e) {
            throw new DAOException("Unable to find: " + number, e);
        }
    }

}
