package ru.tsystems.javaschool.cellular.dao.api;

import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 22.10.14.
 */
public interface CommonDAO<T> {
    public void create(T object) throws DAOException;

    public T get(long id) throws DAOException;

    public List<T> getAll() throws DAOException;

    public void update(T object) throws DAOException;

    public void delete(T object) throws DAOException;
}
