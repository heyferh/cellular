package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by ferh on 15.10.14.
 */
public class AddTariffOptionsServlet extends HttpServlet {
    @Autowired
    private OptionService optionService;
    @Autowired
    private TariffService tariffService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (request.getParameterValues("options") == null) {
            request.setAttribute("message", "Choose options!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        try {
            tariffService.addOptionForTariff(request.getParameter("tariff_id"), request.getParameterValues("options"));
            List<Tariff> tariffList = tariffService.getAllTariffs();
            response.sendRedirect("all_tariffs");
        } catch (TariffException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        } catch (OptionException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Option> optionList = optionService.getAllOptions();
            Tariff currentTariff = tariffService.getTariffById(Long.parseLong(request.getParameter("tariff_id")));
            optionList.removeAll(currentTariff.getOptions());
            request.setAttribute("tariff_id", request.getParameter("tariff_id"));
            request.setAttribute("optionList", optionList);
            request.getRequestDispatcher("add_option.jsp").forward(request, response);


        } catch (OptionException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        } catch (TariffException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
    }
}
