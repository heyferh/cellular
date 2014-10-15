package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
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
public class EnableOptionServlet extends HttpServlet {
    ContractService contractService = new ContractServiceImpl();
    OptionService optionService = new OptionServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Contract contract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
            Option option = optionService.getOptionById(Long.parseLong(request.getParameter("option_id")));
            contractService.enableOption(contract, option);
            contractService.updateContract(contract);
            request.setAttribute("contract", contract);
            request.getRequestDispatcher("operator_contract.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
