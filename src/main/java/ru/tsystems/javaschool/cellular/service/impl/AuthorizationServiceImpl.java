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

    private final static Logger logger = Logger.getLogger(AuthorizationService.class);

    @Autowired
    UserDAO userDAO;

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) throws AuthorizationException {
        try {
            logger.info("Getting user with email: " + email);
            return userDAO.getUserByEmail(email);
        } catch (DAOException e) {
            logger.error("Getting user with email: " + email + " fails with DAOException.");
            throw new AuthorizationException("Getting user by email: " + email + " fails.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserBean userBean = null;
        try {
            logger.info("Loading user with email: " + s);
            userBean = new UserBean(userDAO.getUserByEmail(s));
        } catch (DAOException e) {
            logger.error("Loading user with email: " + s + " fails. UserNotFoundException.");
            throw new UsernameNotFoundException(s);
        }
        return userBean;
    }

}

