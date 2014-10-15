package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.ContractService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;
import ru.tsystems.javaschool.cellular.service.impl.ContractServiceImpl;
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
public class SelectTariffServlet extends HttpServlet {
    TariffService tariffService = new TariffServiceImpl();
    ContractService contractService = new ContractServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Tariff> tariffList = tariffService.getAllTariffs();
            Contract currentContract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
            tariffList.remove(currentContract.getTariff());
            request.setAttribute("tariffList", tariffList);
            request.setAttribute("contract_id", request.getParameter("contract_id"));
            request.getRequestDispatcher("select_tariff.jsp").forward(request,response);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
