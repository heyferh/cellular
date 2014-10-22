package ru.tsystems.javaschool.cellular.dao.api;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.exception.DAOException;

/**
 * Created by ferh on 16.10.14.
 */
public interface ContractDAO extends CommonDAO<Contract> {
    public Contract getContractByPhoneNumber(String phoneNumber) throws DAOException;

    public boolean checkIfNumberExists(String number) throws DAOException;
}
