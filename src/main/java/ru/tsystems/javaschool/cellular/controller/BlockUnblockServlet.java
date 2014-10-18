package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.service.api.ContractService;
import ru.tsystems.javaschool.cellular.service.impl.ContractServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ferh on 18.10.14.
 */
public class BlockUnblockServlet extends HttpServlet {
    ContractService contractService = new ContractServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Contract contract = null;
        try {
            contract = contractService.getContractById(Long.parseLong(request.getParameter("contract_id")));
        } catch (ContractException e) {
            e.printStackTrace();
        }
        try {
            if (request.getRequestURI().equals("/admin/block")) {
                contractService.forceBlock(contract);
            } else {
                contractService.forceUnblock(contract);
            }
        } catch (ContractException e) {
            e.printStackTrace();
        }
        request.setAttribute("contract", contract);
        request.getRequestDispatcher("contract_details.jsp").forward(request, response);
    }
}
