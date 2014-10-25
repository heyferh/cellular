package ru.tsystems.javaschool.cellular.controller;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.impl.OptionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ferh on 19.10.14.
 */
public class AllOptionsServlet extends HttpServlet {
    OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Option option = new Option();
        option.setActivationCost(Integer.parseInt(request.getParameter("activationcost")));
        option.setCost(Integer.parseInt(request.getParameter("cost")));
        option.setTitle(request.getParameter("title"));
        try {
            optionService.createOption(option);
            request.setAttribute("optionList", optionService.getAllOptions());
        } catch (OptionException e) {
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("all_options.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Option> optionList = null;
        try {
            optionList = optionService.getAllOptions();
        } catch (OptionException e) {
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("optionList", optionList);
        request.getRequestDispatcher("all_options.jsp").forward(request, response);
    }
}
