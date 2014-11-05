package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.service.api.ContractService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

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
    @Autowired
    TariffService tariffService;
    @Autowired
    ContractService contractService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("tariff_id") == null) {
            request.setAttribute("message", "Choose tariff! ");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        try {
            Contract contract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
            Tariff tariff = tariffService.getTariffById(Long.parseLong(request.getParameter("new_tariff")));
            contractService.changeTariff(contract, tariff);
            contractService.updateContract(contract);
            request.setAttribute("contract", contract);
            request.getRequestDispatcher("contract_details.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Contract currentContract = null;
        try {
            currentContract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
        } catch (ContractException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        if (currentContract.isBlockedByOperator() && request.getRequestURI().contains("/user")) {
            request.setAttribute("message", "This number is blocked by operator!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        List<Tariff> tariffList = null;
        try {
            tariffList = tariffService.getAllTariffs();
        } catch (TariffException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        tariffList.remove(currentContract.getTariff());
        request.setAttribute("tariffList", tariffList);
        request.setAttribute("contract_id", request.getParameter("contract_id"));
        request.getRequestDispatcher("select_tariff.jsp").forward(request, response);
    }
}
