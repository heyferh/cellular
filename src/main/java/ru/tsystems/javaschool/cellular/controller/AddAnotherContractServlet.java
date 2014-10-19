package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.service.api.ClientService;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;
import ru.tsystems.javaschool.cellular.service.impl.ClientServiceImpl;
import ru.tsystems.javaschool.cellular.service.impl.OptionServiceImpl;
import ru.tsystems.javaschool.cellular.service.impl.TariffServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ferh on 19.10.14.
 */
public class AddAnotherContractServlet extends HttpServlet {
    TariffService tariffService = new TariffServiceImpl(Manager.getEntityManager());
    ClientService clientService = new ClientServiceImpl(Manager.getEntityManager());
    OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Option> optionList = null;
        try {
            optionList = optionService.getOptionsForTariff(Long.parseLong(request.getParameter("tariff_id")));
        } catch (OptionException e) {
            e.printStackTrace();
        }
        Tariff tariff = null;
        try {
            tariff = tariffService.getTariffById(Long.parseLong(request.getParameter("tariff_id")));
        } catch (TariffException e) {
            e.printStackTrace();
        }
        request.setAttribute("tariff", tariff);
        request.setAttribute("optionList", optionList);
        request.setAttribute("client_id", request.getParameter("client_id"));
        request.getRequestDispatcher("create_another_contract.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tariff> tariffList = null;
        try {
            tariffList = tariffService.getAllTariffs();

        } catch (TariffException e) {
            e.printStackTrace();
        }
        request.setAttribute("tariffList", tariffList);
        request.setAttribute("client_id", request.getParameter("client_id"));
        request.getRequestDispatcher("choose_tariff.jsp").forward(request, response);
    }
}
