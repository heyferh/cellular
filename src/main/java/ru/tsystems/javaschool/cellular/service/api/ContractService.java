package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Contract;

import java.util.List;

/**
 * Created by ferh on 11.10.14.
 */
public interface ContractService {

    public void createContract(Contract contract);

    public Contract getContractById(long id);

    public List<Contract> getAllContracts();

    public void updateContract(Contract contract);

    public void deleteContract(Contract contract);
}
