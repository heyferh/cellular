package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.service.api.ContractService;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.impl.ContractServiceImpl;
import ru.tsystems.javaschool.cellular.service.impl.OptionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ferh on 15.10.14.
 */
public class DisableOptionServlet extends HttpServlet {
    @Autowired
    private ContractService contractService;
    @Autowired
    private OptionService optionService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Contract contract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
            if (contract.isBlockedByOperator() && request.getRequestURI().contains("/user")) {
                request.setAttribute("message", "This number is blocked by operator!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            Option option = optionService.getOptionById(Long.parseLong(request.getParameter("option_id")));
            contractService.disableOption(contract, option);
            contractService.updateContract(contract);
            request.setAttribute("contract", contract);
            request.getRequestDispatcher("contract_details.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
    }
}
