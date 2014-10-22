package ru.tsystems.javaschool.cellular.dao.api;


import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 16.10.14.
 */
public interface OptionDAO extends CommonDAO<Option> {
    public List<Option> getOptionsForTariff(String title) throws DAOException;

    public List<Option> getOptionsForTariff(long tariff_id) throws DAOException;
}
