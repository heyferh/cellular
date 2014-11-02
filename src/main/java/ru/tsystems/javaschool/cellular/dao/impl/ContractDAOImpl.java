package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.ContractDAO;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.OBJECT_NOT_FOUND;
import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.PERSISTENCE_EXCEPTION;

/**
 * Created by ferh on 09.10.14.
 */
public class ContractDAOImpl extends CommonDAOImpl<Contract> implements ContractDAO {
    public ContractDAOImpl(EntityManager entityManager) {
        super(entityManager, Contract.class);
    }

    public Contract getContractByPhoneNumber(String phoneNumber) throws DAOException {
        try {
            logger.info("Getting contract " + phoneNumber);
            return (Contract) entityManager.createQuery("select c from Contract c where c.phoneNumber=:number")
                    .setParameter("number", phoneNumber).getSingleResult();
        } catch (NoResultException e) {
            logger.error("Contract with phone number: " + phoneNumber + " not found");
            throw new DAOException(OBJECT_NOT_FOUND, e);
        } catch (PersistenceException e) {
            logger.error("Unable to get contract with phone number: " + phoneNumber);
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }

    @Override
    public boolean checkIfNumberExists(String number) throws DAOException {
        try {
            logger.info("Checking if number already exist." + number);
            long count = (Long) entityManager.createQuery("select count(c.phoneNumber) from Contract c where c.phoneNumber=:number")
                    .setParameter("number", number)
                    .getSingleResult();
            return count > 0 ? true : false;
        } catch (PersistenceException e) {
            logger.error("DAOException. Unable to find: " + number);
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }
}
