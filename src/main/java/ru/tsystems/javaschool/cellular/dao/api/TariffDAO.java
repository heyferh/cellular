package ru.tsystems.javaschool.cellular.dao.api;


import ru.tsystems.javaschool.cellular.entity.Tariff;

import java.util.List;

/**
 * Created by ferh on 16.10.14.
 */
public interface TariffDAO {
    public void create(Tariff tariff);

    public Tariff read(long id);

    public void update(Tariff tariff);

    public void delete(Tariff tariff);

    public List<Tariff> getAll();
}
