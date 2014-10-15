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

/**
 * Created by ferh on 15.10.14.
 */
public class ChangeTariffServlet extends HttpServlet {
    ContractService contractService = new ContractServiceImpl();
    TariffService tariffService = new TariffServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Contract contract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
            Tariff tariff = tariffService.getTariffById(Long.parseLong(request.getParameter("new_tariff")));
            contractService.changeTariff(contract, tariff);
            contractService.updateContract(contract);
            request.setAttribute("contract", contract);
            request.getRequestDispatcher("operator_contract.jsp").forward(request, response);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
