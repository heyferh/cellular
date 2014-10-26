package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.exception.TariffException;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface TariffService {
    /**
     * Persists new tariff to database.
     *
     * @param tariff   tariff to create.
     * @param optionId options which are depend on this tariff.
     * @throws TariffException if options were choosen incorrect.
     */
    public void createTariff(Tariff tariff, String[] optionId) throws TariffException;

    /**
     * Gets tariff by given id.
     *
     * @param id
     * @return Tariff object.
     * @throws TariffException if there's no such tariff.
     */
    public Tariff getTariffById(long id) throws TariffException;

    /**
     * Gets all tariffs.
     *
     * @return List of tariffs.
     * @throws TariffException if there's an exception at persistence level.
     */
    public List<Tariff> getAllTariffs() throws TariffException;

    /**
     * Updates information about given tariff.
     *
     * @param tariff given tariff.
     * @throws TariffException if unable to update.
     */
    public void updateTariff(Tariff tariff) throws TariffException;

    /**
     * Delete tariff.
     *
     * @param tariff
     * @throws TariffException
     */
    public void deleteTariff(Tariff tariff) throws TariffException;

    /**
     * Add option for tariff.
     *
     * @param tariff_id target tariff.
     * @param optionId  array of options.
     * @throws OptionException if there are errors with requirements or incompatibilities.
     */
    public void addOptionForTariff(String tariff_id, String[] optionId) throws OptionException;

    /**
     * Delete tariff option.
     *
     * @param tariff_id target tariff.
     * @param option_id option to remove.
     * @throws OptionException if there are errors with requirements or incompatibilities.
     */
    public void deleteTariffOption(String tariff_id, String option_id) throws OptionException;

}
