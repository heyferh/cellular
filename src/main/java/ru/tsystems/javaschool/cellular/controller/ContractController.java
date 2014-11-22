package ru.tsystems.javaschool.cellular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.javaschool.cellular.dao.api.UserDAO;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.*;
import ru.tsystems.javaschool.cellular.service.api.ClientService;
import ru.tsystems.javaschool.cellular.service.api.ContractService;
import ru.tsystems.javaschool.cellular.service.api.OptionService;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ferh on 16.11.14.
 */
@Controller
@RequestMapping(value = "admin/contract")
public class ContractController {
    @Autowired
    ContractService contractService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    TariffService tariffService;

    @Autowired
    OptionService optionService;

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public ModelAndView newContract() {
        ModelAndView modelAndView = new ModelAndView("create_contract");
        try {
            modelAndView.addObject("tariffList", tariffService.getAllTariffs());
        } catch (TariffException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public ModelAndView addNewContract(@Valid @ModelAttribute("clientBean") Client client,
                                       BindingResult result,
                                       @RequestParam("phoneNumber") String phoneNumber,
                                       @RequestParam("tariff_id") long tariff_id,
                                       @RequestParam("option_id") long[] option_id) {
        ModelAndView modelAndView = new ModelAndView("create_contract");
        try {
            if (result.hasErrors() || contractService.checkIfNumberExists(phoneNumber)) {
                modelAndView.addObject("tariffList", tariffService.getAllTariffs());
                return modelAndView;
            }
            contractService.addContract(new Contract(phoneNumber, false, false), client, tariff_id, option_id);
        } catch (ContractException e) {
            e.printStackTrace();
        } catch (TariffException e) {
            e.printStackTrace();
        } catch (OptionException e) {
            e.printStackTrace();
        }
        return new ModelAndView("all_contracts");
    }

    @RequestMapping(value = "check_number", method = RequestMethod.POST)
    @ResponseBody
    public String checkIfNumberExists(@RequestParam("number") String phoneNumber) {
        try {
            if (contractService.checkIfNumberExists(phoneNumber)) {
                return "This number is already used";
            }
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ModelAndView getAllContracts() throws DAOException {
        ModelAndView modelAndView = new ModelAndView("all_contracts");
        try {
            modelAndView.addObject("clientList", clientService.getAllClients());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "add_another", method = RequestMethod.GET)
    public ModelAndView addAnotherContract(@RequestParam("client_id") long id) {
        ModelAndView modelAndView = new ModelAndView("add_contract");
        modelAndView.addObject("client_id", id);
        try {
            modelAndView.addObject("tariffList", tariffService.getAllTariffs());
        } catch (TariffException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "add_another", method = RequestMethod.POST)
    @ResponseBody
    public String addContract(@RequestParam("client_id") long id,
                              @RequestParam("phoneNumber") String phoneNumber,
                              @RequestParam("tariff_id") long tariff_id,
                              @RequestParam("option_id") long[] option_id) {
        ModelAndView modelAndView = new ModelAndView("all_contracts");
        try {
            Client client = clientService.getClientById(id);
            contractService.addOneMoreContract(new Contract(phoneNumber, false, false), client, tariff_id, option_id);
        } catch (ClientException e) {
            return e.getMessage();
        } catch (OptionException e) {
            return e.getMessage();
        } catch (ContractException e) {
            return e.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "contract_details", method = RequestMethod.GET)
    public ModelAndView getDetails(@RequestParam("id") long contract_id) {
        ModelAndView modelAndView = new ModelAndView("contract_details");
        Contract contract = null;
        Set<Option> optionList = null;
        List<Tariff> tariffList = null;
        try {
            contract = contractService.getContractById(contract_id);
            optionList = contract.getTariff().getOptions();
            optionList.removeAll(contract.getOptions());
            tariffList = tariffService.getAllTariffs();
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

    @RequestMapping(value = "block", method = RequestMethod.GET)
    public ModelAndView block(@RequestParam("id") long id) {
        try {
            Contract contract = contractService.getContractById(id);
            contractService.forceBlock(contract);
            contractService.updateContract(contract);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("redirect:contract_details?id=" + id);
        return modelAndView;
    }

    @RequestMapping(value = "unblock", method = RequestMethod.GET)
    public ModelAndView unBlock(@RequestParam("id") long id) {
        try {
            Contract contract = contractService.getContractById(id);
            contractService.forceUnblock(contract);
            contractService.updateContract(contract);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("redirect:contract_details?id=" + id);
        return modelAndView;
    }

    @RequestMapping(value = "enable_option", method = RequestMethod.GET)
    @ResponseBody
    public String enableOption(@RequestParam("contract_id") long contract_id,
                               @RequestParam("option_id") long option_id) {
        try {
            Contract contract = contractService.getContractById(contract_id);
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

    @RequestMapping(value = "disable_option", method = RequestMethod.GET)
    @ResponseBody
    public String disableOption(@RequestParam("contract_id") long contract_id,
                                @RequestParam("option_id") long option_id) {
        try {
            Contract contract = contractService.getContractById(contract_id);
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

    @RequestMapping(value = "change_tariff", method = RequestMethod.POST)
    @ResponseBody
    public String changeTariff(@RequestParam("contract_id") long contract_id,
                               @RequestParam("tariff_id") long tariff_id,
                               @RequestParam("option_id") long[] options) {
        List<Option> optionList = new ArrayList<Option>();
        try {
            for (long id : options) {
                optionList.add(optionService.getOptionById(id));
            }
            Contract contract = contractService.getContractById(contract_id);
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
