package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Option;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface OptionService {
    public void createOption(Option option);

    public Option getOptionById(long id);

    public List<Option> getAllOptions();

    public void updateOption(Option option);

    public void deleteOption(Option option);

    public List<Option> getOptionsForTariff(String title);
}
