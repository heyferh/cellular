package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.ClientException;
import ru.tsystems.javaschool.cellular.service.api.ClientService;

import java.util.List;

/**
 * Created by ferh on 09.11.14.
 */
@RequestMapping(value = "/")
@Controller
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView createClient() throws ClientException {
        ModelAndView modelAndView = new ModelAndView("create_contract");
        modelAndView.addObject("msg","OBJECT_FROM_CONTROLLER");
        return modelAndView;
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public List<Client> getAllClients() throws ClientException {
        return clientService.getAllClients();
    }
}
