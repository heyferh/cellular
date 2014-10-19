package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.exception.ClientException;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.service.api.ClientService;
import ru.tsystems.javaschool.cellular.service.api.ContractService;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;
import ru.tsystems.javaschool.cellular.service.impl.ClientServiceImpl;
import ru.tsystems.javaschool.cellular.service.impl.ContractServiceImpl;
import ru.tsystems.javaschool.cellular.service.impl.OptionServiceImpl;
import ru.tsystems.javaschool.cellular.service.impl.TariffServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ferh on 19.10.14.
 */
public class CreateAnotherContractServlet extends HttpServlet {
    ClientService clientService = new ClientServiceImpl(Manager.getEntityManager());
    ContractService contractService = new ContractServiceImpl(Manager.getEntityManager());
    TariffService tariffService = new TariffServiceImpl(Manager.getEntityManager());
    OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Client client = null;
        try {
            client = clientService.getClientById(Long.parseLong(request.getParameter("client_id")));
        } catch (ClientException e) {
            e.printStackTrace();
        }

        Contract contract = new Contract();
        contract.setClient(client);
        contract.setPhoneNumber(request.getParameter("phonenumber"));

        try {
            contract.setTariff(tariffService.getTariffById(Long.parseLong(request.getParameter("tariff_id"))));
        } catch (TariffException e) {
            e.printStackTrace();
        }
        for (String optionId : request.getParameterValues("options")) {
            try {
                contract.addOptions(optionService.getOptionById(Long.parseLong(optionId)));
            } catch (OptionException e) {
                e.printStackTrace();
            }
        }
        try {
            contractService.createContract(contract);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        try {
            client.addContract(contract);
            clientService.updateClient(client);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        response.sendRedirect("all_contracts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
