package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.cellular.dao.api.UserDAO;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.AuthorizationException;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.AuthorizationService;

import java.util.Arrays;

/**
 * Created by ferh on 17.10.14.
 */
@Transactional
@Service("AuthorizationService")
public class AuthorizationServiceImpl implements AuthorizationService, UserDetailsService {
    private final Logger logger = Logger.getLogger(AuthorizationService.class);

    @Autowired
    UserDAO userDAO;

    @Override
    public User getUserByEmail(String email) throws AuthorizationException {
        logger.info("Getting user by email: " + email);
        try {
            return userDAO.getUserByEmail(email);
        } catch (DAOException e) {
            logger.error("Getting user by email: " + email + " fails.");
            throw new AuthorizationException("Getting user by email: " + email + " fails.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userDAO.getUserByEmail("hey.ferh@gmail.com");
        } catch (DAOException e) {
            e.printStackTrace();
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, Arrays.asList(new SimpleGrantedAuthority(getRole(user))));
    }

    private String getRole(User user) {
        return (user instanceof Client) ? "User" : "Admin";
    }
}
