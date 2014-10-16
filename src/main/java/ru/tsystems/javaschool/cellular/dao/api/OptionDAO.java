package ru.tsystems.javaschool.cellular.dao.api;


import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 16.10.14.
 */
public interface OptionDAO {
    public void create(Option option) throws DAOException;

    public Option read(long id) throws DAOException;

    public void update(Option option) throws DAOException;

    public void delete(Option option);

    public List<Option> getAll();

    public List<Option> getOptionsForTariff(String title);
}
