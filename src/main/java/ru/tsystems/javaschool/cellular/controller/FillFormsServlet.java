package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;
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
public class FillFormsServlet extends HttpServlet {
    OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());
    TariffService tariffService = new TariffServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Option> optionList = null;
        try {
            optionList = optionService.getOptionsForTariff(Long.parseLong(request.getParameter("tariff_id")));
        } catch (OptionException e) {
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        Tariff tariff = null;
        try {
            tariff = tariffService.getTariffById(Long.parseLong(request.getParameter("tariff_id")));
        } catch (TariffException e) {
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("tariff", tariff);
        request.setAttribute("optionList", optionList);
        request.getRequestDispatcher("create_contract.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
