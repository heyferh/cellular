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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferh on 15.10.14.
 */
public class CreateNewTariffServlet extends HttpServlet {
    OptionService optionService = new OptionServiceImpl();
    TariffService tariffService = new TariffServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Option> optionList = new ArrayList<Option>();
        for (String id : request.getParameterValues("options")) {
            try {
                optionList.add(optionService.getOptionById(Long.parseLong(id)));
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
        Tariff tariff = new Tariff(request.getParameter("title"), Integer.valueOf(request.getParameter("price")));
        for (Option option : optionList) {
            tariff.addOptions(option);
        }
        tariffService.createTariff(tariff);
        try {
            List<Tariff> tariffList = tariffService.getAllTariffs();
            request.setAttribute("tariffList", tariffList);
            request.getRequestDispatcher("all_tariffs.jsp").forward(request, response);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Option> optionList = optionService.getAllOptions();
            request.setAttribute("optionList", optionList);
            request.getRequestDispatcher("create_new_tariff.jsp").forward(request, response);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
