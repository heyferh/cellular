package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Manager;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
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
public class AddTariffOptionsServlet extends HttpServlet {
    private OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());
    private TariffService tariffService = new TariffServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Option> optionList = new ArrayList<Option>();
            for (String id : request.getParameterValues("options")) {
                optionList.add(optionService.getOptionById(Long.parseLong(id)));
            }
            Tariff tariff = tariffService.getTariffById(Long.parseLong(request.getParameter("tariff_id")));
            for (Option option : optionList) {
                tariffService.addOptionForTariff(tariff, option);
            }
            tariffService.updateTariff(tariff);
            List<Tariff> tariffList = tariffService.getAllTariffs();
            request.setAttribute("tariffs", tariffList);
            request.getRequestDispatcher("all_tariffs.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Option> optionList = optionService.getAllOptions();
            Tariff currentTariff = tariffService.getTariffById(Long.parseLong(request.getParameter("tariff_id")));
            optionList.removeAll(currentTariff.getOptions());
            request.setAttribute("tariff_id", request.getParameter("tariff_id"));
            request.setAttribute("optionList", optionList);
            request.getRequestDispatcher("create_add_option.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
