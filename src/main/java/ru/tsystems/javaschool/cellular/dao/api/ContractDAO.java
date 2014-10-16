package ru.tsystems.javaschool.cellular.dao.api;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 16.10.14.
 */
public interface ContractDAO {
    public void create(Contract contract) throws DAOException;

    public Contract read(long id) throws DAOException;

    public void update(Contract contract) throws DAOException;

    public void delete(Contract contract) throws DAOException;

    public Contract getContractByPhoneNumber(String phoneNumber) throws DAOException;

    public List<Contract> getAll() throws DAOException;
}
