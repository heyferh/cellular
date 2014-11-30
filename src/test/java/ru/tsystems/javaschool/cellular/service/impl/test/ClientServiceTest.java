package ru.tsystems.javaschool.cellular.service.impl.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.tsystems.javaschool.cellular.dao.impl.ClientDAOImpl;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.service.impl.ClientServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by ferh on 27.11.14.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientDAOImpl clientDAO;
    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testCreateClient() throws Exception {
        doNothing().when(clientDAO).create(any(Client.class));
        clientService.createClient(any(Client.class));
        verify(clientDAO, times(1)).create(any(Client.class));
    }

    @Test
    public void testGetClientById() throws Exception {
        when(clientDAO.get(1L)).thenReturn(any(Client.class));
        clientService.getClientById(1L);
        verify(clientDAO, times(1)).get(1L);
    }

    @Test
    public void testGetAllClients() throws Exception {
        List<Client> list = new ArrayList<>();
        list.add(new Client("Ivan", "Ivanov", null, null, null, null, null));
        list.add(new Client("Petr", "Petrov", null, null, null, null, null));
        when(clientDAO.getAll()).thenReturn(list);
        Assert.assertArrayEquals(clientService.getAllClients().toArray(), list.toArray());
    }

    @Test
    public void testUpdateClient() throws Exception {
        doNothing().when(clientDAO).update(any(Client.class));
        clientService.updateClient(any(Client.class));
        verify(clientDAO, times(1)).update(any(Client.class));
    }

    @Test
    public void testDeleteClient_hasNoRequirements() throws Exception {
        doNothing().when(clientDAO).delete(any(Client.class));
        clientService.deleteClient(any(Client.class));
        verify(clientDAO, times(1)).delete(any(Client.class));
    }
}
