package ru.tsystems.javaschool.cellular.dao.api;

import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.DAOException;

/**
 * Created by ferh on 17.10.14.
 */
public interface UserDAO {
    public User getUserByEmail(String email) throws DAOException;
}
