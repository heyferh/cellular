package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Administrator;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.AuthorizationException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.service.api.AuthorizationService;
import ru.tsystems.javaschool.cellular.service.api.ClientService;
import ru.tsystems.javaschool.cellular.service.impl.AuthorizationServiceImpl;
import ru.tsystems.javaschool.cellular.service.impl.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by ferh on 21.10.14.
 */
public class LoginServlet extends HttpServlet {
    ClientService clientService = new ClientServiceImpl(Manager.getEntityManager());
    AuthorizationService authorizationService = new AuthorizationServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user;
        HttpSession session = null;
        try {
            user = authorizationService.getUserByEmail(email);
            if (user instanceof Client) {
                session = request.getSession();
                session.setAttribute("client", user);
                session.setAttribute("role", "user");
                response.addCookie(new Cookie("email", user.getEmail()));
                response.sendRedirect("/user/account?client_id=" + user.getId());
            }
            if (user instanceof Administrator) {
                session = request.getSession();
                session.setAttribute("role", "admin");
                response.addCookie(new Cookie("email", email));
                response.sendRedirect("/admin/all_contracts");
            }
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}