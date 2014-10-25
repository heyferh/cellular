package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.helper.Manager;
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
    private ContractService contractService = new ContractServiceImpl(Manager.getEntityManager());
    private OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Contract contract = null;
        Option option = null;
        try {
            contract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
            option = optionService.getOptionById(Long.parseLong(request.getParameter("option_id")));
            contractService.enableOption(contract, option);
            contractService.updateContract(contract);
        } catch (Exception e) {
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("contract", contract);
        request.getRequestDispatcher("contract_details.jsp").forward(request, response);
    }
}
