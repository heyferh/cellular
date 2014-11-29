package ru.tsystems.javaschool.cellular.service.impl.test;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.tsystems.javaschool.cellular.dao.impl.ClientDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.ContractDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.OptionDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.TariffDAOImpl;
import ru.tsystems.javaschool.cellular.service.impl.ContractServiceImpl;

/**
 * Created by ferh on 27.11.14.
 */
@RunWith(MockitoJUnitRunner.class)

public class ContractServiceTest {
    @Mock
    private Logger logger;
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

}
