package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;
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
    ContractService contractService = new ContractServiceImpl();
    OptionService optionService = new OptionServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Contract contract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
            List<Option> optionList = optionService.getOptionsForTariff(contract.getTariff().getTitle());
            optionList.removeAll(contract.getOptions());
            request.setAttribute("optionList", optionList);
            request.setAttribute("contract_id", contract.getId());
            request.getRequestDispatcher("select_options.jsp").forward(request, response);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
