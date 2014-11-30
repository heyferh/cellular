package ru.tsystems.javaschool.cellular.service.impl.test;

import junit.framework.Assert;
import org.apache.log4j.Logger;
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
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.service.impl.TariffServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by ferh on 25.11.14.
 */
@RunWith(MockitoJUnitRunner.class)
public class TariffServiceTest {

    @Mock
    private OptionDAOImpl optionDAO;
    @Mock
    private TariffDAOImpl tariffDAO;
    @Mock
    private ContractDAOImpl contractDAO;
    @InjectMocks
    private TariffServiceImpl tariffService;

    private List<Option> optionList = new ArrayList<>();
    private List<Tariff> tariffList = new ArrayList<>();
    private List<Contract> contractList = new ArrayList<>();

    @Before
    public void init() {
        Option option1 = new Option(0L, "100 SMS", 10, 20);
        Option option2 = new Option(1L, "200 SMS", 100, 200);
        Option option3 = new Option(2L, "Internet 1GB", 100, 20);
        Option option4 = new Option(3L, "Internet", 100, 20);
        Option option5 = new Option(4L, "Unlim calls", 200, 250);
        option1.addIncompatibleOptions(option2);
        option2.addIncompatibleOptions(option1);
        option3.addRequiredOptions(option4);
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);
        optionList.add(option4);
        optionList.add(option5);

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
    public void testCreateTariff_emptyOptionList() throws Exception {
        tariffService.createTariff(any(Tariff.class), null);
        verify(tariffDAO, times(1)).create(any(Tariff.class));
        verify(tariffDAO, times(1)).update(any(Tariff.class));
    }

    @Test
    public void testCreateTariff_nonNullOptionList() throws Exception {
        Tariff tariff = new Tariff();
        when(tariffDAO.get(3L)).thenReturn(tariff);
        when(optionDAO.get(0L)).thenReturn(optionList.get(0));
        when(optionDAO.get(1L)).thenReturn(optionList.get(1));
        when(optionDAO.get(2L)).thenReturn(optionList.get(2));
        when(optionDAO.get(3L)).thenReturn(optionList.get(3));
        tariffService.createTariff(tariff, new long[]{0, 1, 2, 3});
        Assert.assertEquals(tariffService.getTariffById(3L).getOptions().size(), 4);
    }

    @Test(expected = TariffException.class)
    public void testCreateTariff_TariffException() throws Exception {
        Tariff tariff = new Tariff();
        when(tariffDAO.get(3L)).thenReturn(tariff);
        when(optionDAO.get(0L)).thenReturn(optionList.get(0));
        when(optionDAO.get(1L)).thenReturn(optionList.get(1));
        when(optionDAO.get(2L)).thenReturn(optionList.get(2));
        tariffService.createTariff(tariff, new long[]{0, 1, 2});
    }

    @Test
    public void testGetTariffById() throws Exception {
        Tariff tariff = new Tariff("Title", 20);
        tariff.setId(1L);
        when(tariffDAO.get(1L)).thenReturn(tariff);
        tariffService.getTariffById(tariff.getId());
        verify(tariffDAO, times(1)).get(tariff.getId());
    }

    @Test
    public void testGetAllTariffs() throws Exception {
        List<Tariff> list = new ArrayList<>();
        list.add(new Tariff("Title-1", 20));
        list.add(new Tariff("Title-2", 40));
        when(tariffDAO.getAll()).thenReturn(list);
        org.junit.Assert.assertArrayEquals(tariffService.getAllTariffs().toArray(), list.toArray());
    }

    @Test
    public void testUpdateTariff() throws Exception {
        Tariff tariff = new Tariff("Title", 20);
        doNothing().when(tariffDAO).update(tariff);
        tariffService.updateTariff(tariff);
        verify(tariffDAO, times(1)).update(tariff);
    }

    @Test
    public void testDeleteTariff_HasNoDependencies() throws Exception {
        tariffService.deleteTariff(new Tariff("Title", 100));
        verify(tariffDAO, times(1)).delete(any(Tariff.class));
    }

    @Test(expected = TariffException.class)
    public void testDeleteTariff_DependsOnContract() throws Exception {
        when(contractDAO.getAll()).thenReturn(contractList);
        tariffService.deleteTariff(tariffList.get(0));
    }

    @Test
    public void testAddTariffOption() throws Exception {
        when(optionDAO.get(4L)).thenReturn(optionList.get(4));
        when(tariffDAO.get(0L)).thenReturn(tariffList.get(0));
        tariffService.addOptionForTariff(tariffList.get(0).getId(), new long[]{4});
        Assert.assertEquals(tariffService.getTariffById(0L).getOptions().size(), 4);
    }

    @Test(expected = OptionException.class)
    public void testAddTariffOption_withoutRequired() throws Exception {
        Option option = new Option(6L, "Source", 10, 20);
        Option requiredOption = new Option(7L, "Required", 10, 20);
        option.addRequiredOptions(requiredOption);
        when(tariffDAO.get(1L)).thenReturn(tariffList.get(0));
        when(optionDAO.get(6L)).thenReturn(option);
        when(optionDAO.get(7L)).thenReturn(requiredOption);
        tariffService.addOptionForTariff(1L, new long[]{6});
    }

    @Test
    public void testDeleteTariffOption() throws Exception {
        when(optionDAO.get(0L)).thenReturn(optionList.get(0));
        when(tariffDAO.get(1L)).thenReturn(tariffList.get(0));
        tariffService.deleteTariffOption(1L, 0L);
        Assert.assertEquals(tariffService.getTariffById(1L).getOptions().size(), 2);
    }

    @Test(expected = OptionException.class)
    public void testDeleteTariffOption_WithRequired() throws Exception {
        when(optionDAO.get(3L)).thenReturn(optionList.get(3));
        when(tariffDAO.get(1L)).thenReturn(tariffList.get(0));
        tariffService.deleteTariffOption(1L, 3L);
    }
}
