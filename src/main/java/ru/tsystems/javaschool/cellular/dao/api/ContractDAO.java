package ru.tsystems.javaschool.cellular.dao.api;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.exception.DAOException;

/**
 * Created by ferh on 16.10.14.
 */
public interface ContractDAO extends CommonDAO<Contract> {
    /**
     * Finds contract by phone number.
     *
     * @param phoneNumber given phone number.
     * @return Contract object with given phone number.
     * @throws DAOException if there's no such contract.
     */
    public Contract getContractByPhoneNumber(String phoneNumber) throws DAOException;

    /**
     * Checks if given phone number is already exist in database.
     *
     * @param number phone number represented as a string.
     * @return true if given phone number is already exist. False - otherwise.
     * @throws DAOException if there is an error.
     */
    public boolean checkIfNumberExists(String number) throws DAOException;
}
