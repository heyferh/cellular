package ru.tsystems.javaschool.cellular.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.ClientException;
import ru.tsystems.javaschool.cellular.service.api.ClientService;

/**
 * Created by ferh on 08.11.14.
 */
@Controller
@RequestMapping("/welcome")
public class MainController {

    @Autowired
    ClientService clientService;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome() throws ClientException {
        Client client = new Client();
        clientService.createClient(client);
        return "{}";
    }
}
