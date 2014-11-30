package ru.tsystems.javaschool.cellular.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.service.api.OptionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferh on 09.11.14.
 */
@Controller
@RequestMapping(value = "option")
public class OptionController {

    private final static Logger logger = Logger.getLogger(OptionController.class);

    @Autowired
    OptionService optionService;

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ModelAndView getAllOptions() {
        ModelAndView modelAndView = new ModelAndView("all_options");
        try {
            modelAndView.addObject("optionList", optionService.getAllOptions());
        } catch (OptionException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }

    @PreAuthorize("hasRole('Admin')")
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
            if (iID != null & rID != null)
                for (long incID : iID) {
                    for (long reqID : rID) {
                        if (incID == reqID) {
                            modelAndView.addObject("error", optionService.getOptionById(incID).getTitle() + " could not be both incompatible and required");
                            modelAndView.addObject("optionList", optionService.getAllOptions());
                            return modelAndView;
                        }
                    }
                }
            optionService.createOption(option);
            optionService.manageIncompatibleOptions(option.getId(), iID);
            optionService.manageRequiredOptions(option.getId(), rID);
            modelAndView.addObject("optionList", optionService.getAllOptions());
        } catch (OptionException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }

    @PreAuthorize("hasRole('Admin')")
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

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editOption(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView("edit_options");
        try {
            List<Option> optionList = optionService.getAllOptions();
            optionList.remove(optionService.getOptionById(id));
            modelAndView.addObject("optionList", optionList);
            modelAndView.addObject("option", optionService.getOptionById(id));
        } catch (OptionException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }

    @PreAuthorize("hasRole('Admin')")
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
            logger.error(e.getMessage());
        }
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "get_options", method = RequestMethod.GET)
    @ResponseBody
    public List<Option> getOptionsForTariff(@RequestParam("tariff_id") long id) {
        try {
            List<Option> optionList = new ArrayList<Option>();
            for (Option option : optionService.getOptionsForTariff(id)) {
                optionList.add(new Option(option.getId(), option.getTitle(), option.getCost(), option.getActivationCost()));
            }
            return optionList;
        } catch (OptionException e) {
            return null;
        }
    }
}
