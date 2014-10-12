package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.OptionException;

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

    public void forceBlock(Contract contract) throws ContractException;

    public void forceUnblock(Contract contract) throws ContractException;

    public void block(Contract contract);

    public void unblock(Contract contract) throws ContractException;

    public void changeTariff(Contract contract, Tariff tariff);

    public void disableOption(Contract contract, Option option) throws OptionException;

    public void enableOption(Contract contract, Option option) throws OptionException;

}
