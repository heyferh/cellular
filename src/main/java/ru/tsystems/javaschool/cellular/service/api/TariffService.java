package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface TariffService {
    public void createTariff(Tariff tariff);

    public Tariff getTariffById(long id) throws DAOException;

    public List<Tariff> getAllTariffs() throws DAOException;

    public void updateTariff(Tariff tariff);

    public void deleteTariff(Tariff tariff);

    public void addOptionForTariff(Tariff tariff, Option option);

    public void deleteTariffOption(Tariff tariff, Option option);

}
