package ru.tsystems.javaschool.cellular.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, Model model) {
        return "login";
    }
}