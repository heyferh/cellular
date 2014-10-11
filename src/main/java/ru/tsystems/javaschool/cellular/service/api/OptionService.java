package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Option;

/**
 * Created by ferh on 08.10.14.
 */
public interface OptionService {
    public void createOption(Option option);

    public Option getOptionById(long id);

    public void updateOption(Option option);

    public void deleteOption(Option option);
}
