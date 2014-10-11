package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Tariff;

/**
 * Created by ferh on 08.10.14.
 */
public interface TariffService {
    public void createTariff(Tariff tariff);

    public Tariff getTariffById(long id);

    public void updateTariff(Tariff tariff);

    public void deleteTariff(Tariff tariff);
}
