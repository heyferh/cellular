package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.helper.Manager;
import ru.tsystems.javaschool.cellular.helper.Validator;
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
    @Autowired
    OptionService optionService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(Validator.checkNumber(request.getParameter("activationcost")));
        builder.append(Validator.checkNumber(request.getParameter("cost")));
        builder.append(Validator.checkString(request.getParameter("title")));
        if (builder.length() > 0) {
            request.setAttribute("message", builder.toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        Option option = new Option();
        option.setActivationCost(Integer.parseInt(request.getParameter("activationcost")));
        option.setCost(Integer.parseInt(request.getParameter("cost")));
        option.setTitle(request.getParameter("title"));
        try {
            optionService.createOption(option);
            request.setAttribute("optionList", optionService.getAllOptions());
        } catch (OptionException e) {
            request.setAttribute("message", e.getMessage());
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
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("optionList", optionList);
        request.getRequestDispatcher("all_options.jsp").forward(request, response);
    }
}
