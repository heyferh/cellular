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

    public List<Option> getOptionsForTariff(String title) throws OptionException;

    public void addIncompatibleOption(Option src, Option option);

    public void removeIncompatibleOption(Option src, Option option);

    public void addRequiredOption(Option src, Option option);

    public void removeRequiredOption(Option src, Option option);
}
