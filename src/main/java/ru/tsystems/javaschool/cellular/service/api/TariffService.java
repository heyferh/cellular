package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.TariffException;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface TariffService {
    public void createTariff(Tariff tariff) throws TariffException;

    public Tariff getTariffById(long id) throws TariffException;

    public List<Tariff> getAllTariffs() throws TariffException;

    public void updateTariff(Tariff tariff) throws TariffException;

    public void deleteTariff(Tariff tariff) throws TariffException;

    public void addOptionForTariff(Tariff tariff, Option option);

    public void deleteTariffOption(Tariff tariff, Option option);

}
