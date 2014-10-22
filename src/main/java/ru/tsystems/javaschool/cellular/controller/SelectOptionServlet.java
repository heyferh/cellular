package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
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
import java.util.List;

/**
 * Created by ferh on 15.10.14.
 */
public class SelectOptionServlet extends HttpServlet {
    private ContractService contractService = new ContractServiceImpl(Manager.getEntityManager());
    private OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Contract contract = null;
        try {
            contract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
        } catch (ContractException e) {
            e.printStackTrace();
        }
        if (contract.isBlockedByOperator() && request.getRequestURI().contains("/user")) {
            request.setAttribute("message", "This number is blocked by operator!");
            request.getRequestDispatcher("errorpage.jsp").forward(request, response);
            return;
        }
        List<Option> optionList = null;
        try {
            optionList = optionService.getOptionsForTariff(contract.getTariff().getId());
        } catch (OptionException e) {
            e.printStackTrace();
        }
        optionList.removeAll(contract.getOptions());
        request.setAttribute("optionList", optionList);
        request.setAttribute("contract_id", contract.getId());
        request.getRequestDispatcher("select_options.jsp").forward(request, response);
    }
}
