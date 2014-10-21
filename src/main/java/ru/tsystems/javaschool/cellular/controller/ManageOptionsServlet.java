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
public class ManageOptionsServlet extends HttpServlet {
    OptionService optionService = new OptionServiceImpl(Manager.getEntityManager());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("action").equals("require")) {
                optionService.manageRequiredOptions(Long.parseLong(request.getParameter("option_id")), request.getParameterValues("required"));
            } else if (request.getParameter("action").equals("incompatible")) {
                optionService.manageIncompatibleOptions(Long.parseLong(request.getParameter("option_id")), request.getParameterValues("incompatible"));
            }
        } catch (OptionException e) {
            e.printStackTrace();
        }
        response.sendRedirect("all_options");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Option option = null;
        List<Option> optionList = null;
        try {
            option = optionService.getOptionById(Long.parseLong(request.getParameter("option_id")));
            optionList = optionService.getAllOptions();
            request.setAttribute("option", option);
        } catch (OptionException e) {
            e.printStackTrace();
        }
        if (request.getParameter("action").equals("require")) {
            optionList.removeAll(option.getRequiredOptions());
            optionList.removeAll(option.getIncompatibleOptions());
            optionList.remove(option);
            request.setAttribute("optionList", optionList);
            request.setAttribute("action", "require");
        } else {
            optionList.removeAll(option.getIncompatibleOptions());
            optionList.removeAll(option.getRequiredOptions());
            optionList.remove(option);
            request.setAttribute("optionList", optionList);
            request.setAttribute("action", "compatible");
        }
        request.getRequestDispatcher("manage_options.jsp").forward(request, response);
    }
}
