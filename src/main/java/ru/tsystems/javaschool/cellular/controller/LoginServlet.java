package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.tsystems.javaschool.cellular.entity.Administrator;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.AuthorizationException;
import ru.tsystems.javaschool.cellular.service.api.AuthorizationService;
import ru.tsystems.javaschool.cellular.service.api.ClientService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ferh on 21.10.14.
 */
@Controller
public class LoginServlet extends HttpServlet {
    @Autowired
    ClientService clientService;
    @Autowired
    @Qualifier("authorization")
    AuthorizationService authorizationService;

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user;
        HttpSession session = null;
        try {
            user = authorizationService.getUserByEmail(email);
            if (user instanceof Client && user.getPassword().equals(password)) {
                session = request.getSession();
                session.setAttribute("client", user);
                session.setAttribute("role", "user");
                response.sendRedirect("user/account");
            }
            if (user instanceof Administrator && user.getPassword().equals(password)) {
                session = request.getSession();
                session.setAttribute("role", "admin");
                response.sendRedirect("admin/all_contracts");
            }
        } catch (AuthorizationException e) {
            PrintWriter out = response.getWriter();
            out.println("<div align=center><font color=red>Either user name or password is wrong.</font></div>");
            request.getRequestDispatcher("login.html").include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
