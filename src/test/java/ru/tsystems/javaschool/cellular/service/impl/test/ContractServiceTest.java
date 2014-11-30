package ru.tsystems.javaschool.cellular.service.impl.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.tsystems.javaschool.cellular.dao.impl.ClientDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.ContractDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.OptionDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.TariffDAOImpl;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.service.impl.ContractServiceImpl;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by ferh on 27.11.14.
 */
@RunWith(MockitoJUnitRunner.class)

public class ContractServiceTest {

    @Mock
    private ContractDAOImpl contractDAO;
    @Mock
    private ClientDAOImpl clientDAO;
    @Mock
    private OptionDAOImpl optionDAO;
    @Mock
    private TariffDAOImpl tariffDAO;
    @InjectMocks
    private ContractServiceImpl contractService;

    @Test
    public void testCreateClient() throws Exception {
        doNothing().when(contractDAO).create(any(Contract.class));
        contractService.createContract(any(Contract.class));
        verify(contractDAO, times(1)).create(any(Contract.class));
    }

    @Test
    public void testGetClientById() throws Exception {
        when(contractDAO.get(1L)).thenReturn(any(Contract.class));
        contractService.getContractById(1L);
        verify(contractDAO, times(1)).get(1L);
    }

    @Test
    public void testGetAllClients() throws Exception {
        List<Contract> list = new ArrayList<>();
        list.add(new Contract("111", false, false));
        list.add(new Contract("222", false, false));
        when(contractDAO.getAll()).thenReturn(list);
        Assert.assertArrayEquals(contractService.getAllContracts().toArray(), list.toArray());
    }

    @Test
    public void testUpdateClient() throws Exception {
        doNothing().when(contractDAO).update(any(Contract.class));
        contractService.updateContract(any(Contract.class));
        verify(contractDAO, times(1)).update(any(Contract.class));
    }

    @Test
    public void testDeleteClient_hasNoRequirements() throws Exception {
        doNothing().when(contractDAO).delete(any(Contract.class));
        contractService.deleteContract(any(Contract.class));
        verify(contractDAO, times(1)).delete(any(Contract.class));
    }

    @Test
    public void testForceBlock() throws Exception {
        Contract contract = new Contract("111", false, true);
        contractService.forceBlock(contract);
        Assert.assertEquals(contract.isBlockedByOperator(), true);
    }

    @Test(expected = ContractException.class)
    public void testForceBlock_AlreadyBlocked() throws Exception {
        Contract contract = new Contract("111", true, true);
        contractService.forceBlock(contract);
    }

    @Test
    public void testForceUnblock() throws Exception {
        Contract contract = new Contract("111", true, true);
        contractService.forceUnblock(contract);
        Assert.assertEquals(contract.isBlockedByOperator(), false);
    }

    @Test(expected = ContractException.class)
    public void testForceUnblock_AlreadyUnblocked() throws Exception {
        Contract contract = new Contract("111", false, true);
        contractService.forceUnblock(contract);
    }

    @Test
    public void testBlock() throws Exception {
        Contract contract = new Contract("111", false, false);
        contractService.block(contract);
        Assert.assertEquals(contract.isBlockedByClient(), true);
    }

    @Test(expected = ContractException.class)
    public void testBlock_AlreadyBlocked() throws Exception {
        Contract contract = new Contract("111", true, true);
        contractService.block(contract);
    }

    @Test
    public void testUnblock() throws Exception {
        Contract contract = new Contract("111", false, true);
        contractService.unblock(contract);
        Assert.assertEquals(contract.isBlockedByClient(), false);
    }

    @Test(expected = ContractException.class)
    public void testUnblock_BlockedByOperator() throws Exception {
        Contract contract = new Contract("111", true, true);
        contractService.unblock(contract);
    }

    @Test
    public void testChangeTariff() throws Exception {
        Contract contract = new Contract();
        contract.setBalance(100);
        Tariff tariff = new Tariff();
        tariff.setCost(50);
        contractService.changeTariff(contract, tariff, Collections.<Option>emptySet());
        Assert.assertEquals(contract.getBalance(), 50);
        Assert.assertEquals(contract.getTariff(), tariff);
    }

    @Test(expected = ContractException.class)
    public void testChangeTariff_NotEnoughMoney() throws Exception {
        Contract contract = new Contract();
        contract.setBalance(100);
        Tariff tariff = new Tariff();
        tariff.setCost(150);
        contractService.changeTariff(contract, tariff, Collections.<Option>emptySet());
    }

    @Test
    public void testDisableOption() throws Exception {
        Option option1 = new Option(1L, "100SMS", 10, 20);
        Option option2 = new Option(2L, "SMS", 10, 20);
        option1.addRequiredOptions(option2);
        Contract contract = new Contract();
        contract.addOptions(option1);
        contract.addOptions(option2);
        contractService.disableOption(contract, option1);
        Assert.assertEquals(contract.getOptions().size(), 1);
    }

    @Test(expected = OptionException.class)
    public void testDisableOption_HasRequired() throws Exception {
        Option option1 = new Option(1L, "100SMS", 10, 20);
        Option option2 = new Option(2L, "SMS", 10, 20);
        option1.addRequiredOptions(option2);
        Contract contract = new Contract();
        contract.addOptions(option1);
        contract.addOptions(option2);
        contractService.disableOption(contract, option2);
    }

    @Test
    public void testEnableOptions() throws Exception {
        Option option1 = new Option(1L, "100SMS", 10, 20);
        Option option2 = new Option(2L, "SMS", 10, 20);
        Set<Option> optionSet = new HashSet<>();
        optionSet.add(option1);
        optionSet.add(option2);
        Contract contract = new Contract();
        contractService.enableOptions(contract, optionSet);
        Assert.assertEquals(contract.getOptions().size(), 2);
        Assert.assertEquals(contract.getBalance(), 1000 - 10 - 20 - 10 - 20);
    }

    @Test(expected = ContractException.class)
    public void testEnableOptions_NotEnoughMoney() throws Exception {
        Option option1 = new Option(1L, "100SMS", 100, 300);
        Option option2 = new Option(2L, "SMS", 100, 600);
        Set<Option> optionSet = new HashSet<>();
        optionSet.add(option1);
        optionSet.add(option2);
        Contract contract = new Contract();
        contractService.enableOptions(contract, optionSet);
    }

    @Test
    public void testAddContract() throws Exception {
        Option option1 = new Option(1L, "Internet", 10, 20);
        Option option2 = new Option(2L, "1 Gb", 10, 20);
        option2.addRequiredOptions(option1);
        when(optionDAO.get(1L)).thenReturn(option1);
        when(optionDAO.get(2L)).thenReturn(option2);
        Contract contract = new Contract();
        Client client = new Client("Firstname", "Lastname", null, null, null, null, "password");
        contractService.addContract(contract, client, 1L, new long[]{1L, 2L});
        Assert.assertEquals(contract.getClient(), client);
        Assert.assertEquals(contract.getOptions().size(), 2);
    }

    @Test(expected = OptionException.class)
    public void testAddContract_NeedRequiredOptions() throws Exception {
        Option option1 = new Option(1L, "Internet", 10, 20);
        Option option2 = new Option(2L, "1 Gb", 10, 20);
        option2.addRequiredOptions(option1);
        when(optionDAO.get(1L)).thenReturn(option1);
        when(optionDAO.get(2L)).thenReturn(option2);
        Contract contract = new Contract();
        Client client = new Client("Firstname", "Lastname", null, null, null, null, "password");
        contractService.addContract(contract, client, 1L, new long[]{2L});
    }

    @Test
    public void testAddOneMoreContract() throws Exception {
        Option option1 = new Option(1L, "Internet", 10, 20);
        Option option2 = new Option(2L, "1 Gb", 10, 20);
        option2.addRequiredOptions(option1);
        when(optionDAO.get(1L)).thenReturn(option1);
        when(optionDAO.get(2L)).thenReturn(option2);
        Contract contract = new Contract();
        Client client = new Client("Firstname", "Lastname", null, null, null, null, "password");
        contractService.addContract(contract, client, 1L, new long[]{1L, 2L});
        Assert.assertEquals(contract.getClient(), client);
        Assert.assertEquals(contract.getOptions().size(), 2);
    }

    @Test(expected = OptionException.class)
    public void testAddOneMoreContract_NeedRequiredOptions() throws Exception {
        Option option1 = new Option(1L, "Internet", 10, 20);
        Option option2 = new Option(2L, "1 Gb", 10, 20);
        option2.addRequiredOptions(option1);
        when(optionDAO.get(1L)).thenReturn(option1);
        when(optionDAO.get(2L)).thenReturn(option2);
        Contract contract = new Contract();
        Client client = new Client("Firstname", "Lastname", null, null, null, null, "password");
        contractService.addContract(contract, client, 1L, new long[]{2L});
    }

    @Test
    public void testCheckIfNumberExists_DoesntExist() throws Exception {
        contractService.checkIfNumberExists("89110009988");
    }

    @Test
    public void testCheckIfNumberExist_Exists() throws Exception {
        when(contractDAO.checkIfNumberExists("89110009988")).thenReturn(true);
        Assert.assertEquals(contractService.checkIfNumberExists("89110009988"), true);
    }

    @Test(expected = ContractException.class)
    public void testCheckIfNumberExist_InvalidFormat() throws Exception {
        contractService.checkIfNumberExists("0098");
    }

    @Test
    public void testValidateOptions_correctInput() throws Exception {
        Option option1 = new Option();
        Option option2 = new Option();
        Option option3 = new Option();
        Option option4 = new Option();
        option1.addRequiredOptions(option2);
        Set<Option> optionSet = new HashSet<>();
        optionSet.add(option1);
        optionSet.add(option2);
        optionSet.add(option3);
        optionSet.add(option4);
        contractService.validateOptions(optionSet);
    }

    @Test(expected = OptionException.class)
    public void testValidateOptions_HasIncompatible() throws Exception {
        Option option1 = new Option();
        Option option2 = new Option();
        option1.addIncompatibleOptions(option2);
        Set<Option> optionSet = new HashSet<>();
        optionSet.add(option1);
        optionSet.add(option2);
        contractService.validateOptions(optionSet);
    }

    @Test(expected = OptionException.class)
    public void testValidateOptions_NeedRequired() throws Exception {
        Option option1 = new Option(1L, "100SMS", 10, 20);
        Option option2 = new Option(2L, "Free Calls", 10, 20);
        Option option3 = new Option(3L, "SMS PACK", 10, 20);
        option1.addRequiredOptions(option3);
        Set<Option> optionSet = new HashSet<>();
        optionSet.add(option1);
        optionSet.add(option2);
        contractService.validateOptions(optionSet);
    }
}
