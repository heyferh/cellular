package ru.tsystems.javaschool.cellular.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.CartException;
import ru.tsystems.javaschool.cellular.exception.ContractException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.service.api.ContractService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ferh on 26.11.14.
 */
public class CartBean {

    @Autowired
    ContractService contractService;

    private Tariff tariff = new Tariff();
    private Set<Option> optionSet = new HashSet<Option>();

    @Transactional
    public void pushItems(long contract_id) throws CartException {
        try {
            Contract contract = contractService.getContractById(contract_id);
            if (contract.isBlockedByOperator()) {
                throw new CartException("This contract is blocked by operator");
            }
            Set<Option> set = new HashSet<Option>();
            set.addAll(optionSet);
            set.addAll(contract.getOptions());
            contractService.validateOptions(set);
            for (Option option : set) {
                contract.addOptions(option);
            }
            contractService.updateContract(contract);
            optionSet.clear();
        } catch (ContractException e) {
            e.printStackTrace();
        } catch (OptionException e) {
            throw new CartException(e.getMessage());
        }
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Set<Option> getOptionSet() {
        return optionSet;
    }

    public void setOptionList(Set<Option> optionSet) {
        this.optionSet = optionSet;
    }

    public void addOption(Option option) {
        this.optionSet.add(option);
    }

    public void removeOption(Option option) {
        this.optionSet.remove(option);
    }
}
