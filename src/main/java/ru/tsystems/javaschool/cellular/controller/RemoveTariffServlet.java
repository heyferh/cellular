package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Manager;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.service.api.TariffService;
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
public class RemoveTariffServlet extends HttpServlet {
    private TariffService tariffService = new TariffServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Tariff tariff = null;
        List<Tariff> tariffList = null;
        try {
            tariff = tariffService.getTariffById(Long.parseLong(request.getParameter("tariff_id")));
            tariffService.deleteTariff(tariff);
            tariffList = tariffService.getAllTariffs();
        } catch (TariffException e) {
            e.printStackTrace();
        }
        request.setAttribute("tariffList", tariffList);
        request.getRequestDispatcher("all_tariffs.jsp").forward(request, response);
    }
}
