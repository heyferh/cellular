package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.cellular.dao.api.UserDAO;
import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.AuthorizationException;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.helper.UserBean;
import ru.tsystems.javaschool.cellular.service.api.AuthorizationService;

/**
 * Created by ferh on 17.10.14.
 */
@Transactional
@Service("AuthorizationService")
public class AuthorizationServiceImpl implements AuthorizationService, UserDetailsService {
    @Autowired
    private Logger logger;
    @Autowired
    UserDAO userDAO;

    @Override
    public User getUserByEmail(String email) throws AuthorizationException {
        logger.info("Getting user by email: " + email);
        try {
            logger.info("Getting object" + email);
            return userDAO.getUserByEmail(email);
        } catch (DAOException e) {
            logger.error("Getting user by email: " + email + " fails.");
            throw new AuthorizationException("Getting user by email: " + email + " fails.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserBean userBean = null;
        try {
            userBean = new UserBean(userDAO.getUserByEmail(s));
        } catch (DAOException e) {
            throw new UsernameNotFoundException(s);
        }
        return userBean;
    }

}

