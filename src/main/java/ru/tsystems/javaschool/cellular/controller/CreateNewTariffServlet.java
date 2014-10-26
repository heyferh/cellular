package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.helper.Validator;
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
 * Created by ferh on 15.10.14.
 */
public class CreateNewTariffServlet extends HttpServlet {
    private OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());
    private TariffService tariffService = new TariffServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(Validator.checkString(request.getParameter("title")));
            builder.append(Validator.checkNumber(request.getParameter("price")));
            if (request.getParameterValues("options") == null) {
                builder.append("Choose options! ");
            }
            if (builder.length() > 0) {
                request.setAttribute("message", builder.toString());
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            Tariff tariff = new Tariff(request.getParameter("title"), Integer.valueOf(request.getParameter("price")));
            tariffService.createTariff(tariff, request.getParameterValues("options"));


            List<Tariff> tariffList = tariffService.getAllTariffs();
            request.setAttribute("tariffList", tariffList);
            request.getRequestDispatcher("all_tariffs.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Option> optionList = null;
        try {
            optionList = optionService.getAllOptions();
        } catch (OptionException e) {
            e.printStackTrace();
        }
        request.setAttribute("optionList", optionList);
        request.getRequestDispatcher("create_tariff.jsp").forward(request, response);
    }
}
