package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.AuthorizationException;

/**
 * Created by ferh on 17.10.14.
 */
public interface AuthorizationService {
    /**
     * Gets user by it's email.
     *
     * @param email email represented as a string.
     * @return User object.
     * @throws AuthorizationException if there's no such email in database.
     */
    public User getUserByEmail(String email) throws AuthorizationException;
}
