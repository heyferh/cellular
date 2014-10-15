package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;
import ru.tsystems.javaschool.cellular.service.impl.OptionServiceImpl;
import ru.tsystems.javaschool.cellular.service.impl.TariffServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ferh on 15.10.14.
 */
public class RemoveTariffOptionsServlet extends HttpServlet {
    OptionService optionService = new OptionServiceImpl();
    TariffService tariffService = new TariffServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Tariff tariff = tariffService.getTariffById(Long.parseLong(request.getParameter("tariff_id")));
            Option option = optionService.getOptionById(Long.parseLong(request.getParameter("option_id")));
            tariffService.deleteTariffOption(tariff, option);
            tariffService.updateTariff(tariff);
            optionService.updateOption(option);
            request.setAttribute("tariff", tariff);
            request.getRequestDispatcher("edit_options.jsp").forward(request, response);
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }
}
