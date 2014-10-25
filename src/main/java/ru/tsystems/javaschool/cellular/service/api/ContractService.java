package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Client;
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

    public void createContract(Contract contract) throws ContractException;

    public Contract getContractById(long id) throws ContractException;

    public List<Contract> getAllContracts() throws ContractException;

    public void updateContract(Contract contract) throws ContractException;

    public void deleteContract(Contract contract) throws ContractException;

    public void forceBlock(Contract contract) throws ContractException;

    public void forceUnblock(Contract contract) throws ContractException;

    public void block(Contract contract) throws ContractException;

    public void unblock(Contract contract) throws ContractException;

    public void changeTariff(Contract contract, Tariff tariff) throws ContractException;

    public void disableOption(Contract contract, Option option) throws OptionException;

    public void enableOption(Contract contract, Option option) throws OptionException, ContractException;

    public void addContract(Contract contract, Client client, long tariffId, long[] optionIds) throws ContractException, OptionException;

}
