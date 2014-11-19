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

    /**
     * Creates new contract.
     *
     * @param contract Contract object to create.
     * @throws ContractException if there's an error while persisting object.
     */
    public void createContract(Contract contract) throws ContractException;

    /**
     * Gets contract by id.
     *
     * @param id
     * @return Contract object with given id.
     * @throws ContractException if there's no such contract.
     */
    public Contract getContractById(long id) throws ContractException;

    /**
     * Gets list of all contracts.
     *
     * @return List of all contracts.
     * @throws ContractException if there are errors while getting list.
     */
    public List<Contract> getAllContracts() throws ContractException;

    /**
     * Updates given contract.
     *
     * @param contract contract which needs to be updated.
     * @throws ContractException if there is a persistence error.
     */
    public void updateContract(Contract contract) throws ContractException;

    /**
     * Deletes given contract.
     *
     * @param contract
     * @throws ContractException if there are dependent options or tariffs in database.
     */
    public void deleteContract(Contract contract) throws ContractException;

    /**
     * Blocks contract. This method is called by the operator.
     *
     * @param contract contract to block.
     * @throws ContractException if contract is already blocked.
     */
    public void forceBlock(Contract contract) throws ContractException;

    /**
     * Unblocks the contract. This method is called by operator.
     *
     * @param contract contract to unblock.
     * @throws ContractException if contract is already unblocked.
     */
    public void forceUnblock(Contract contract) throws ContractException;

    /**
     * Blocks contract by client.
     *
     * @param contract contract to block.
     * @throws ContractException if this contract is already blocked by client.
     */
    public void block(Contract contract) throws ContractException;

    /**
     * Unblocks contract by client. Client can't unblock the contract while is was blocked by operator.
     *
     * @param contract
     * @throws ContractException if this contract is blocked by operator.
     */
    public void unblock(Contract contract) throws ContractException;

    public void changeTariff(Contract contract, Tariff tariff, List<Option> options) throws ContractException, OptionException;

    /**
     * Disable option on given contract.
     *
     * @param contract given contract.
     * @param option   option to disable.
     * @throws OptionException if this option required for another option.
     */
    public void disableOption(Contract contract, Option option) throws OptionException;

    /**
     * Enable option on given contract.
     *
     * @param contract given contract.
     * @param option   option to enable.
     * @throws OptionException   if option requires another option to be enabled.
     * @throws ContractException if there's no money to pay for new option.
     */
    public void enableOption(Contract contract, Option option) throws OptionException, ContractException;

    /**
     * Add new contract to an existing client.
     *
     * @param contract  new contract to add.
     * @param client    target client.
     * @param tariffId  tariff which contract is depend on.
     * @param optionIds array of options to enable.
     * @throws ContractException if given contract number is already exists.
     * @throws OptionException   if there are requirement or compatibility errors while adding options.
     */
    public void addContract(Contract contract, Client client, long tariffId, long[] optionIds) throws ContractException, OptionException;

    public void addOneMoreContract(Contract contract, Client client, long tariffId, long[] optionIds) throws ContractException, OptionException;

    public boolean checkIfNumberExists(String number) throws ContractException;
}
