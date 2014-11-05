package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.exception.ClientException;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.helper.Validator;
import ru.tsystems.javaschool.cellular.service.api.ClientService;
import ru.tsystems.javaschool.cellular.service.api.ContractService;
import ru.tsystems.javaschool.cellular.service.impl.ClientServiceImpl;
import ru.tsystems.javaschool.cellular.service.impl.ContractServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ferh on 19.10.14.
 */
public class CreateAnotherContractServlet extends HttpServlet {
    @Autowired
    ClientService clientService;
    @Autowired
    ContractService contractService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(Validator.checkPhoneNumber(request.getParameter("phonenumber")));
        if (request.getParameterValues("options") == null) {
            request.setAttribute("message", "Choose options! ");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        if (builder.length() > 0) {
            request.setAttribute("message", builder.toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        Client client = null;
        try {
            client = clientService.getClientById(Long.parseLong(request.getParameter("client_id")));
        } catch (ClientException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        Contract contract = new Contract();
        contract.setPhoneNumber(request.getParameter("phonenumber"));
        long tariff_id = Long.parseLong(request.getParameter("tariff_id"));
        long[] optionId = new long[request.getParameterValues("options").length];
        for (int i = 0; i < optionId.length; i++) {
            optionId[i] = Long.parseLong(request.getParameterValues("options")[i]);
        }
        try {
            contractService.addContract(contract, client, tariff_id, optionId);
        } catch (ContractException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        } catch (OptionException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("all_contracts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
