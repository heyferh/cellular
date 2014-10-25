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
public class OptionEditServlet extends HttpServlet {
    OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Option option = null;
        List<Option> optionList = null;
        try {
            option = optionService.getOptionById(Long.parseLong(request.getParameter("option_id")));
            optionService.deleteOption(option);
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
