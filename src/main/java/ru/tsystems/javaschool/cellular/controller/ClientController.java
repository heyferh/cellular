package ru.tsystems.javaschool.cellular.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.CartException;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.helper.AuthHelper;
import ru.tsystems.javaschool.cellular.helper.CartBean;
import ru.tsystems.javaschool.cellular.service.api.ClientService;
import ru.tsystems.javaschool.cellular.service.api.ContractService;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ferh on 22.11.14.
 */
@Controller
public class ClientController {

    private final static Logger logger = Logger.getLogger(ClientController.class);

    @Autowired
    OptionService optionService;

    @Autowired
    ContractService contractService;

    @Autowired
    ClientService clientService;

    @Autowired
    CartBean cartBean;

    @Autowired
    TariffService tariffService;

    @Autowired
    AuthHelper authHelper;

    @PreAuthorize("hasRole('User')")
    @ModelAttribute("currentUser")
    public Client getCurrentUser() {
        return (Client) authHelper.getCurrentUser();
    }

    @PreAuthorize("hasRole('User')")
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
            logger.error(e.getMessage());
        } catch (TariffException e) {
            logger.error(e.getMessage());
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
            Set<Option> optionSet = new HashSet<Option>();
            optionSet.add(optionService.getOptionById(option_id));
            contractService.enableOptions(contract, optionSet);
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
        Set<Option> optionSet = new HashSet<Option>();
        try {
            for (long id : options) {
                optionSet.add(optionService.getOptionById(id));
            }
            Contract contract = contractService.getContractById(contract_id);
            if (contract.isBlockedByOperator()) {
                return "This contract is blocked by operator.";
            }
            Tariff tariff = tariffService.getTariffById(tariff_id);
            contractService.changeTariff(contract, tariff, optionSet);
        } catch (OptionException e) {
            return e.getMessage();
        } catch (ContractException e) {
            return e.getMessage();
        } catch (TariffException e) {
            return e.getMessage();
        }
        return "";
    }

    @PreAuthorize("hasRole('User') && this.currentUser.id == #client_id")
    @RequestMapping(value = "add_to_cart", method = RequestMethod.GET)
    public String addOptionToCart(@RequestParam("option_id") long option_id,
                                  @RequestParam("client_id") long client_id,
                                  @RequestParam("contract_id") long contract_id) {
        try {
            cartBean.setTariff(contractService.getContractById(contract_id).getTariff());
            cartBean.addOption(optionService.getOptionById(option_id));
        } catch (OptionException e) {
            logger.error(e.getMessage());
        } catch (ContractException e) {
            logger.error(e.getMessage());
        }
        return "redirect:account_details?id=" + contract_id + "&clientID=" + client_id;
    }

    @PreAuthorize("hasRole('User') && this.currentUser.id == #client_id")
    @RequestMapping(value = "remove_from_cart", method = RequestMethod.GET)
    public String removeOptionFromCart(@RequestParam("option_id") long option_id,
                                       @RequestParam("client_id") long client_id,
                                       @RequestParam("contract_id") long contract_id) {
        try {
            cartBean.removeOption(optionService.getOptionById(option_id));
        } catch (OptionException e) {
            logger.error(e.getMessage());
        }
        return "redirect:account_details?id=" + contract_id + "&clientID=" + client_id;
    }

    @PreAuthorize("hasRole('User') && this.currentUser.id == #client_id")
    @RequestMapping(value = "push_options", method = RequestMethod.GET)
    @ResponseBody
    public String pushOptionsToCart(@RequestParam("client_id") long client_id,
                                    @RequestParam("contract_id") long contract_id) {
        ModelAndView modelAndView = new ModelAndView("account_details");
        try {
            cartBean.pushItems(contract_id);
        } catch (CartException e) {
            return e.getMessage();
        }
        return "";
    }
}
