package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.service.api.AuthorizationService;
import ru.tsystems.javaschool.cellular.service.impl.AuthorizationServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ferh on 21.10.14.
 */
public class AuthFilter implements Filter {
    AuthorizationService authorizationService = new AuthorizationServiceImpl(Manager.getEntityManager());

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        if (session == null && !(uri.endsWith("html") || uri.endsWith("login"))) {
            System.err.println("Unauthorized user!");
            request.getRequestDispatcher("login.html").forward(request, response);
            return;
        } else if (session != null) {
            if (request.getSession().getAttribute("role").equals("admin") && (uri.contains("/user"))) {
                response.sendRedirect("/admin/all_contracts");
                return;
            }
            if (request.getSession().getAttribute("role").equals("user") && (uri.contains("/admin"))) {
                Client client = (Client) request.getSession().getAttribute("client");
                response.sendRedirect("/user/account?client_id=" + client.getId());
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
