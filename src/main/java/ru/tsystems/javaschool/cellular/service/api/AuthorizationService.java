package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.exception.AuthorizationException;

/**
 * Created by ferh on 17.10.14.
 */
public interface AuthorizationService {
    public String getRoleByEmail(String email) throws AuthorizationException;
}
