package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.helper.Manager;
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
    TariffService tariffService = new TariffServiceImpl(Manager.getEntityManager());
    ContractService contractService = new ContractServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Contract contract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
            Tariff tariff = tariffService.getTariffById(Long.parseLong(request.getParameter("new_tariff")));
            contractService.changeTariff(contract, tariff);
            contractService.updateContract(contract);
            request.setAttribute("contract", contract);
            request.getRequestDispatcher("contract_details.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tariff> tariffList = null;
        try {
            tariffList = tariffService.getAllTariffs();
        } catch (TariffException e) {
            e.printStackTrace();
        }
        Contract currentContract = null;
        try {
            currentContract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
        } catch (ContractException e) {
            e.printStackTrace();
        }
        tariffList.remove(currentContract.getTariff());
        request.setAttribute("tariffList", tariffList);
        request.setAttribute("contract_id", request.getParameter("contract_id"));
        request.getRequestDispatcher("select_tariff.jsp").forward(request, response);
    }
}
