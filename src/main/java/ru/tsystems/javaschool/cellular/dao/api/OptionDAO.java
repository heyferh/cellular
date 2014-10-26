package ru.tsystems.javaschool.cellular.dao.api;


import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 16.10.14.
 */
public interface OptionDAO extends CommonDAO<Option> {
    /**
     * Gets list of available options, which given tariff has.
     *
     * @param title tariff title.
     * @return List of options.
     * @throws DAOException if there is an error while getting options.
     */
    public List<Option> getOptionsForTariff(String title) throws DAOException;

    /**
     * Gets list of available options, which given tariff has.
     *
     * @param tariff_id tariff id.
     * @return List of options.
     * @throws DAOException if there is an error while getting options.
     */
    public List<Option> getOptionsForTariff(long tariff_id) throws DAOException;
}
