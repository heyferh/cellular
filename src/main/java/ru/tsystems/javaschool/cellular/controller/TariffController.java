package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by ferh on 09.11.14.
 */
@Controller
@RequestMapping(value = "tariff")
public class TariffController {

    @Autowired
    TariffService tariffService;

    @Autowired
    OptionService optionService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ModelAndView getAllTariffs() {
        ModelAndView modelAndView = new ModelAndView("all_tariffs");
        try {
            modelAndView.addObject("tariffList", tariffService.getAllTariffs());
            modelAndView.addObject("optionList", optionService.getAllOptions());
        } catch (TariffException e) {
            e.printStackTrace();
        } catch (OptionException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addNewTariff(@Valid @ModelAttribute("tariffBean") Tariff tariff,
                                     BindingResult result,
                                     @RequestParam(value = "options", required = false) long[] optionsID) {
        ModelAndView modelAndView = new ModelAndView("all_tariffs");
        try {
            modelAndView.addObject("optionList", optionService.getAllOptions());
            if (result.hasErrors()) {
                return modelAndView;
            }
            tariffService.createTariff(tariff, optionsID);
            modelAndView.addObject("tariffList", tariffService.getAllTariffs());
        } catch (TariffException e) {
            e.printStackTrace();
        } catch (OptionException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public String deleteTariff(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView("all_tariffs");
        try {
            tariffService.deleteTariff(tariffService.getTariffById(id));
        } catch (TariffException e) {
            return e.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editTariff(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView("edit_tariff");
        try {
            Tariff tariff = tariffService.getTariffById(id);
            List<Option> optionList = optionService.getAllOptions();
            optionList.removeAll(tariff.getOptions());
            modelAndView.addObject("availableOptions", optionList);
            modelAndView.addObject("tariff", tariff);
        } catch (OptionException e) {
            e.printStackTrace();
        } catch (TariffException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "disable", method = RequestMethod.GET)
    @ResponseBody
    public String disableTariffOption(@RequestParam("id") long id,
                                      @RequestParam("optionID") long optionID) {
        try {
            tariffService.deleteTariffOption(id, optionID);
        } catch (OptionException e) {
            return e.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "enable", method = RequestMethod.GET)
    @ResponseBody
    public String enableTariffOption(@RequestParam("id") long id,
                                     @RequestParam("optionID") long optionID) {
        try {
            tariffService.addOptionForTariff(id, new long[]{optionID});
        } catch (OptionException e) {
            return e.getMessage();
        }
        return "";
    }
}
