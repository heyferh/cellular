package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.service.api.OptionService;

import javax.validation.Valid;

/**
 * Created by ferh on 09.11.14.
 */
@Controller
@RequestMapping(value = "option")
public class OptionController {

    @Autowired
    OptionService optionService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ModelAndView getAllOptions() {
        ModelAndView modelAndView = new ModelAndView("all_options");
        try {
            modelAndView.addObject("optionList", optionService.getAllOptions());
            modelAndView.addObject("optionBean", new Option());
        } catch (OptionException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "all", method = RequestMethod.POST)
    public ModelAndView addNewOption(@Valid @ModelAttribute("optionBean") Option option,
                                     BindingResult result,
                                     @RequestParam(value = "incompatibleOptions", required = false) long[] iID,
                                     @RequestParam(value = "requiredOptions", required = false) long[] rID) {
        ModelAndView modelAndView = new ModelAndView("all_options");
        try {
            if (result.hasErrors()) {
                modelAndView.addObject("optionList", optionService.getAllOptions());
                return modelAndView;
            }
            optionService.createOption(option);
            optionService.manageIncompatibleOptions(option.getId(), iID);
            optionService.manageRequiredOptions(option.getId(), rID);
            modelAndView.addObject("optionList", optionService.getAllOptions());
        } catch (OptionException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public ModelAndView deleteOption(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView("all_options");
        modelAndView.addObject("optionBean", new Option());
        try {
            optionService.deleteOption(optionService.getOptionById(id));
            modelAndView.addObject("optionList", optionService.getAllOptions());
        } catch (OptionException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}
