package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.ClientException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.service.api.ClientService;
import ru.tsystems.javaschool.cellular.service.impl.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ferh on 13.10.14.
 */
public class AllContractsServlet extends HttpServlet {
    private ClientService clientService = new ClientServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Client client = null;
        try {
            client = clientService.getClientByPhoneNumber(request.getParameter("phonenumber"));
        } catch (ClientException e) {
            e.printStackTrace();
        }
        request.setAttribute("client", client);
        request.getRequestDispatcher("search_results.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> clientList = null;

        try {
            clientList = clientService.getAllClients();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        request.setAttribute("clientList", clientList);
        request.getRequestDispatcher("all_contracts.jsp").forward(request, response);
    }
}
