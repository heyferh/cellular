package ru.tsystems.javaschool.cellular.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.helper.UserBean;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, Model model) {
        return "login";
    }

    @RequestMapping(value = "/auth-failure")
    public ModelAndView authFailure() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("error", "Incorrect email or password");
        return modelAndView;
    }

    @RequestMapping(value = "/denied")
    public String accessDenied() {
        return "403";
    }

    @RequestMapping(value = "/home")
    public String userDispatch() {
        if (((UserBean) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUser() instanceof Client) {
            return "redirect:account";
        } else {
            return "redirect:contract/all";
        }
    }


}