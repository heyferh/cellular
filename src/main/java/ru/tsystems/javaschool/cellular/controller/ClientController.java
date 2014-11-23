package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.helper.AuthHelper;
import ru.tsystems.javaschool.cellular.service.api.ClientService;
import ru.tsystems.javaschool.cellular.service.api.ContractService;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ferh on 22.11.14.
 */
@Controller
public class ClientController {

    @Autowired
    OptionService optionService;

    @Autowired
    ContractService contractService;

    @Autowired
    ClientService clientService;

    @Autowired
    TariffService tariffService;


    @Autowired
    AuthHelper authHelper;

    @ModelAttribute("currentUser")
    public Client getCurrentUser() {
        return (Client) authHelper.getCurrentUser();
    }

    @RequestMapping(value = "account", method = RequestMethod.GET)
    public ModelAndView getAccountView() {
        ModelAndView modelAndView = new ModelAndView("account");
        modelAndView.addObject("client", (Client) authHelper.getCurrentUser());
        return modelAndView;
    }

    @PreAuthorize("hasRole('User') && this.currentUser.id == #clientID")
    @RequestMapping(value = "account_details", method = RequestMethod.GET)
    public ModelAndView getAccountDetails(@RequestParam("id") long id,
                                          @RequestParam("clientID") long clientID) {
        ModelAndView modelAndView = new ModelAndView("account_details");
        try {
            Contract contract = contractService.getContractById(id);
            Set<Option> optionList = contract.getTariff().getOptions();
            optionList.removeAll(contract.getOptions());
            List<Tariff> tariffList = tariffService.getAllTariffs();
            tariffList.remove(contract.getTariff());
            modelAndView.addObject("contract", contract);
            modelAndView.addObject("client", contract.getClient());
            modelAndView.addObject("tariffList", tariffList);
            modelAndView.addObject("optionList", optionList);
        } catch (ContractException e) {
            e.printStackTrace();
        } catch (TariffException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @PreAuthorize("hasRole('User') && this.currentUser.id == #clientID")
    @RequestMapping(value = "block", method = RequestMethod.GET)
    public String block(@RequestParam("id") long id,
                        @RequestParam("clientID") long clientID) {
        try {
            Contract contract = contractService.getContractById(id);
            contractService.block(contract);
            contractService.updateContract(contract);
        } catch (ContractException e) {
            return e.getMessage();
        }
        return "redirect:account_details?id=" + id + "&clientID=" + clientID;
    }

    @PreAuthorize("hasRole('User') && this.currentUser.id == #clientID")
    @RequestMapping(value = "unblock", method = RequestMethod.GET)
    public String unBlock(@RequestParam("id") long id,
                          @RequestParam("clientID") long clientID) {
        try {
            Contract contract = contractService.getContractById(id);
            contractService.unblock(contract);
            contractService.updateContract(contract);
        } catch (ContractException e) {
            return e.getMessage();
        }
        return "redirect:account_details?id=" + id + "&clientID=" + clientID;
    }

    @PreAuthorize("hasRole('User') && this.currentUser.id == #client_id")
    @RequestMapping(value = "enable_option", method = RequestMethod.GET)
    @ResponseBody
    public String enableOption(@RequestParam("contract_id") long contract_id,
                               @RequestParam("client_id") long client_id,
                               @RequestParam("option_id") long option_id) {
        try {
            Contract contract = contractService.getContractById(contract_id);
            if (contract.isBlockedByOperator()) {
                return "This contract is blocked by operator.";
            }
            Option option = optionService.getOptionById(option_id);
            contractService.enableOption(contract, option);
            contractService.updateContract(contract);
        } catch (ContractException e) {
            return e.getMessage();
        } catch (OptionException e) {
            return e.getMessage();
        }
        return "";
    }

    @PreAuthorize("hasRole('User') && this.currentUser.id == #client_id")
    @RequestMapping(value = "disable_option", method = RequestMethod.GET)
    @ResponseBody
    public String disableOption(@RequestParam("contract_id") long contract_id,
                                @RequestParam("client_id") long client_id,
                                @RequestParam("option_id") long option_id) {
        try {
            Contract contract = contractService.getContractById(contract_id);
            if (contract.isBlockedByOperator()) {
                return "This contract is blocked by operator.";
            }
            Option option = optionService.getOptionById(option_id);
            contractService.disableOption(contract, option);
            contractService.updateContract(contract);
        } catch (ContractException e) {
            return e.getMessage();
        } catch (OptionException e) {
            return e.getMessage();
        }
        return "";
    }

    @PreAuthorize("hasRole('User') && this.currentUser.id == #client_id")
    @RequestMapping(value = "change_tariff", method = RequestMethod.POST)
    @ResponseBody
    public String changeTariff(@RequestParam("contract_id") long contract_id,
                               @RequestParam("client_id") long client_id,
                               @RequestParam("tariff_id") long tariff_id,
                               @RequestParam("option_id") long[] options) {
        List<Option> optionList = new ArrayList<Option>();
        try {
            for (long id : options) {
                optionList.add(optionService.getOptionById(id));
            }
            Contract contract = contractService.getContractById(contract_id);
            if (contract.isBlockedByOperator()) {
                return "This contract is blocked by operator.";
            }
            Tariff tariff = tariffService.getTariffById(tariff_id);
            contractService.changeTariff(contract, tariff, optionList);
            contractService.updateContract(contract);
        } catch (OptionException e) {
            return e.getMessage();
        } catch (ContractException e) {
            return e.getMessage();
        } catch (TariffException e) {
            return e.getMessage();
        }
        return "";
    }
}
