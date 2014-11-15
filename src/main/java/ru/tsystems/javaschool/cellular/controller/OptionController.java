package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    @ResponseBody
    public String deleteOption(@RequestParam("id") long id) {
        try {
            optionService.deleteOption(optionService.getOptionById(id));
        } catch (OptionException e) {
            return e.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editOption(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView("edit_options");
        try {
            modelAndView.addObject("optionList", optionService.getAllOptions());
            modelAndView.addObject("option", optionService.getOptionById(id));
        } catch (OptionException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "manage", method = RequestMethod.POST)
    public ModelAndView manageOptions(@RequestParam("option_id") long id,
                                      @RequestParam(value = "required", required = false) long[] requiredOptions,
                                      @RequestParam(value = "incompatible", required = false) long[] incompatibleOptions) {
        ModelAndView modelAndView = new ModelAndView("all_options");
        try {
            optionService.manageIncompatibleOptions(id, incompatibleOptions);
            optionService.manageRequiredOptions(id, requiredOptions);
            modelAndView.addObject("optionList", optionService.getAllOptions());
        } catch (OptionException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}