package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

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
    public ModelAndView addNewTariff(@RequestParam("title") String title,
                                     @RequestParam("cost") int cost,
                                     @RequestParam(value = "options", required = false) String[] optionsID) {
        ModelAndView modelAndView = new ModelAndView("all_tariffs");
        try {
            tariffService.createTariff(new Tariff(title, cost), optionsID);
            modelAndView.addObject("tariffList", tariffService.getAllTariffs());
            modelAndView.addObject("optionList", optionService.getAllOptions());
        } catch (TariffException e) {
            e.printStackTrace();
        } catch (OptionException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public ModelAndView deleteOption(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView("all_tariffs");
        try {
            tariffService.deleteTariff(tariffService.getTariffById(id));
            modelAndView.addObject(tariffService.getAllTariffs());
        } catch (TariffException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}
