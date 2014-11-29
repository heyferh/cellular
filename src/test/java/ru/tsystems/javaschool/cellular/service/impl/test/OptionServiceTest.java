package ru.tsystems.javaschool.cellular.service.impl.test;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.tsystems.javaschool.cellular.dao.impl.ContractDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.OptionDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.TariffDAOImpl;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.service.impl.OptionServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by ferh on 25.11.14.
 */
@RunWith(MockitoJUnitRunner.class)

public class OptionServiceTest {
    @Mock
    private Logger logger;
    @Mock
    private OptionDAOImpl optionDAO;
    @Mock
    private TariffDAOImpl tariffDAO;
    @Mock
    private ContractDAOImpl contractDAO;
    @InjectMocks
    private OptionServiceImpl optionService;

    private List<Option> optionList = new ArrayList<>();
    private List<Tariff> tariffList = new ArrayList<>();
    private List<Contract> contractList = new ArrayList<>();

    @Before
    public void init() {
        Option option1 = new Option(0L, "100 SMS", 10, 20);
        Option option2 = new Option(1L, "200 SMS", 100, 200);
        Option option3 = new Option(2L, "Internet 1GB", 100, 20);
        Option option4 = new Option(3L, "Internet", 100, 20);
        option1.addIncompatibleOptions(option2);
        option2.addIncompatibleOptions(option1);
        option3.addRequiredOptions(option4);
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);
        optionList.add(option4);

        Tariff tariff1 = new Tariff("Basic", 10);
        tariff1.addOptions(option1);
        tariff1.addOptions(option3);
        tariff1.addOptions(option4);
        Tariff tariff2 = new Tariff("Max", 30);
        tariff2.addOptions(option2);
        tariff2.addOptions(option3);
        tariff2.addOptions(option4);
        tariffList.add(tariff1);
        tariffList.add(tariff2);

        Contract contract1 = new Contract("111-111", false, false);
        contract1.setTariff(tariff1);
        contract1.addOptions(option1);
        Contract contract2 = new Contract("222-222", false, false);
        contract2.setTariff(tariff2);
        contract2.addOptions(option2);
        contract2.addOptions(option3);
        contract2.addOptions(option4);
        contractList.add(contract1);
        contractList.add(contract2);
    }

    @Test
    public void testCreateOption() throws Exception {
        Option option = new Option(1L, "Title", 20, 40);
        doNothing().when(optionDAO).create(option);
        optionService.createOption(option);
        verify(optionDAO, times(1)).create(option);
    }

    @Test
    public void testGetOptionById() throws Exception {
        Option option = new Option(1L, "Title", 20, 40);
        when(optionDAO.get(1L)).thenReturn(option);
        optionService.getOptionById(option.getId());
        verify(optionDAO, times(1)).get(option.getId());
    }

    @Test
    public void testGetAllOptions() throws Exception {
        List<Option> list = new ArrayList<>();
        list.add(new Option(1L, "100 SMS", 10, 20));
        list.add(new Option(2L, "200 SMS", 10, 30));
        when(optionDAO.getAll()).thenReturn(list);
        Assert.assertArrayEquals(optionService.getAllOptions().toArray(), list.toArray());
    }

    @Test
    public void testUpdateOption() throws Exception {
        Option option = new Option(1L, "Title", 20, 40);
        doNothing().when(optionDAO).update(option);
        optionService.updateOption(option);
        verify(optionDAO, times(1)).update(option);
    }

    @Test
    public void testDeleteOption_hasNoRequirements() throws Exception {
        when(optionDAO.getAll()).thenReturn(optionList);
        optionService.deleteOption(optionList.get(0));
        verify(optionDAO, times(1)).delete(any(Option.class));
    }

    @Test(expected = OptionException.class)
    public void testDeleteOption_hasRequired() throws Exception {
        when(optionDAO.getAll()).thenReturn(optionList);
        optionService.deleteOption(optionList.get(3));
    }

    @Test
    public void testDeleteOption_hasIncompatible() throws Exception {
        when(optionDAO.getAll()).thenReturn(optionList);
        optionService.deleteOption(optionList.get(0));
        Assert.assertEquals(optionList.get(1).getIncompatibleOptions().contains(optionList.get(0)), false);
        verify(optionDAO, times(1)).delete(any(Option.class));
    }

    @Test(expected = OptionException.class)
    public void testDeleteOption_usedByTariff() throws Exception {
        when(tariffDAO.getAll()).thenReturn(tariffList);
        when(optionDAO.getAll()).thenReturn(optionList);
        optionService.deleteOption(optionList.get(0));
    }

    @Test(expected = OptionException.class)
    public void testDeleteOption_usedByContract() throws Exception {
        when(optionDAO.getAll()).thenReturn(optionList);
        when(contractDAO.getAll()).thenReturn(contractList);
        optionService.deleteOption(optionList.get(0));
    }

    @Test
    public void testGetOptionsForTariff() throws Exception {
        optionService.getOptionsForTariff(1L);
        verify(optionDAO, atMost(1)).getOptionsForTariff(1L);
    }

    @Test
    public void testManageIncompatibleOptions_notNullArray() throws Exception {
        when(optionDAO.get(0L)).thenReturn(optionList.get(0));
        when(optionDAO.get(1L)).thenReturn(optionList.get(1));
        when(optionDAO.get(2L)).thenReturn(optionList.get(2));
        optionService.manageIncompatibleOptions(0L, new long[]{1L, 2L});
        Assert.assertEquals(optionList.get(0).getIncompatibleOptions().size(), 2);
    }

    @Test
    public void testManageIncompatibleOptions_emptyArray() throws Exception {
        when(optionDAO.get(0L)).thenReturn(optionList.get(0));
        optionService.manageIncompatibleOptions(0L, null);
        Assert.assertEquals(optionList.get(0).getIncompatibleOptions().size(), 0);
    }

    @Test
    public void testManageRequiredOptions_notNullArray() throws Exception {
        when(optionDAO.get(0L)).thenReturn(optionList.get(0));
        when(optionDAO.get(2L)).thenReturn(optionList.get(2));
        optionService.manageRequiredOptions(0L, new long[]{2L});
        Assert.assertEquals(optionList.get(0).getRequiredOptions().size(), 1);
    }

    @Test
    public void testManageRequiredOptions_emptyArray() throws Exception {
        when(optionDAO.get(0L)).thenReturn(optionList.get(0));
        optionService.manageRequiredOptions(0L, null);
        Assert.assertEquals(optionList.get(0).getRequiredOptions().size(), 0);
    }
}




