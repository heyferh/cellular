package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Tariff;
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
import java.util.List;

/**
 * Created by ferh on 18.10.14.
 */
public class CreateContractServlet extends HttpServlet {
    TariffService tariffService = new TariffServiceImpl(Manager.getEntityManager());
    ClientService clientService = new ClientServiceImpl(Manager.getEntityManager());
    OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());
    ContractService contractService = new ContractServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Client client = new Client();
        client.setEmail(request.getParameter("email"));
        client.setAddress(request.getParameter("address"));
        client.setFirstName(request.getParameter("firstname"));
        client.setLastName(request.getParameter("lastname"));
        client.setIdCard(request.getParameter("idcard"));
        client.setDayOfBirth(request.getParameter("dayofbirth"));
        client.setPassword(request.getParameter("password"));
        Contract contract = new Contract();
        contract.setPhoneNumber(request.getParameter("phonenumber"));

        long tariff_id = Long.parseLong(request.getParameter("tariff_id"));
        long[] optionId = new long[request.getParameterValues("options").length];
        for (int i = 0; i < optionId.length; i++) {
            optionId[i] = Long.parseLong(request.getParameterValues("options")[i]);
        }
        try {
            contractService.addContract(contract, client, tariff_id, optionId);
        } catch (ContractException e) {
            e.printStackTrace();
        } catch (OptionException e) {
            e.printStackTrace();
        }
        response.sendRedirect("all_contracts");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tariff> tariffList = null;
        try {
            tariffList = tariffService.getAllTariffs();
        } catch (TariffException e) {
            e.printStackTrace();
        }
        request.setAttribute("tariffList", tariffList);
        request.getRequestDispatcher("choose_tariff.jsp").forward(request, response);
    }
}
