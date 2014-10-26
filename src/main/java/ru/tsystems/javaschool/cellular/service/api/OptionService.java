package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.OptionException;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface OptionService {
    /**
     * Creates new option.
     *
     * @param option given option.
     * @throws OptionException if unable to create option.
     */
    public void createOption(Option option) throws OptionException;

    /**
     * Gets option by id.
     *
     * @param id
     * @return Option object with given id.
     * @throws OptionException if there's no such option.
     */
    public Option getOptionById(long id) throws OptionException;

    /**
     * Gets all options.
     *
     * @return List of all existing options.
     * @throws OptionException if unable to get options.
     */
    public List<Option> getAllOptions() throws OptionException;

    /**
     * Updates information about given option.
     *
     * @param option
     * @throws OptionException if there is a persistence exception.
     */
    public void updateOption(Option option) throws OptionException;

    /**
     * Deletes given option from database.
     *
     * @param option
     * @throws OptionException if there are dependent objects such as clients or tariffs.
     */
    public void deleteOption(Option option) throws OptionException;

    /**
     * Gets all options which given tariff has.
     *
     * @param tariff_id
     * @return List of all options which given tariff has.
     * @throws OptionException if unable to get options.
     */
    public List<Option> getOptionsForTariff(long tariff_id) throws OptionException;

    /**
     * Set incompatible options to option with given id.
     *
     * @param id  source option.
     * @param ids new options ids.
     * @throws OptionException if there are exceptions while getting options.
     */
    public void manageIncompatibleOptions(long id, String[] ids) throws OptionException;
    /**
     * Set required options to option with given id.
     *
     * @param id  source option.
     * @param ids new options ids.
     * @throws OptionException if there are exceptions while getting options.
     */
    public void manageRequiredOptions(long id, String[] ids) throws OptionException;
}
