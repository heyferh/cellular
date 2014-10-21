package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.OptionException;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface OptionService {
    public void createOption(Option option) throws OptionException;

    public Option getOptionById(long id) throws OptionException;

    public List<Option> getAllOptions() throws OptionException;

    public void updateOption(Option option) throws OptionException;

    public void deleteOption(Option option) throws OptionException;

    public List<Option> getOptionsForTariff(long tariff_id) throws OptionException;

    public void manageIncompatibleOptions(long id, String[] ids) throws OptionException;

    public void manageRequiredOptions(long id, String[] ids);
}
