package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface OptionService {
    public void createOption(Option option);

    public Option getOptionById(long id) throws DAOException;

    public List<Option> getAllOptions() throws DAOException;

    public void updateOption(Option option);

    public void deleteOption(Option option);

    public List<Option> getOptionsForTariff(String title) throws DAOException;

    public void addIncompatibleOption(Option src, Option option);

    public void removeIncompatibleOption(Option src, Option option);

    public void addRequiredOption(Option src, Option option);

    public void removeRequiredOption(Option src, Option option);
}
