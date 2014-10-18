package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
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
    private OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());
    private TariffService tariffService = new TariffServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Tariff tariff = null;
        try {
            tariff = tariffService.getTariffById(Long.parseLong(request.getParameter("tariff_id")));
        } catch (TariffException e) {
            e.printStackTrace();
        }
        Option option = null;
        try {
            option = optionService.getOptionById(Long.parseLong(request.getParameter("option_id")));
        } catch (OptionException e) {
            e.printStackTrace();
        }
        tariffService.deleteTariffOption(tariff, option);
        try {
            tariffService.updateTariff(tariff);
        } catch (TariffException e) {
            e.printStackTrace();
        }
        try {
            optionService.updateOption(option);
        } catch (OptionException e) {
            e.printStackTrace();
        }
        request.setAttribute("tariff", tariff);
        request.getRequestDispatcher("edit_options.jsp").forward(request, response);

    }
}
