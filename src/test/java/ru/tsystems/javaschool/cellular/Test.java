package ru.tsystems.javaschool.cellular.test;

import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.ClientException;
import ru.tsystems.javaschool.cellular.service.api.ClientService;

/**
 * Created by ferh on 03.11.14.
 */
public class Test extends AbstractJUnit4SpringContextTests {
    @Autowired
    ClientService clientService;

    @org.junit.Test
    public void test() throws ClientException {
        Client client = new Client();
        clientService.createClient(client);
        Assert.assertEquals(clientService.getAllClients().size(), 1);
    }
}
