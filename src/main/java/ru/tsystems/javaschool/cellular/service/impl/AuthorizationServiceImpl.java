package ru.tsystems.javaschool.cellular.service.impl;

import org.apache.log4j.Logger;
import ru.tsystems.javaschool.cellular.dao.api.UserDAO;
import ru.tsystems.javaschool.cellular.dao.impl.UserDAOImpl;
import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.AuthorizationException;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.AuthorizationService;

import javax.persistence.EntityManager;

/**
 * Created by ferh on 17.10.14.
 */
public class AuthorizationServiceImpl implements AuthorizationService {
    private final Logger logger = Logger.getLogger(AuthorizationService.class);
    EntityManager entityManager;
    UserDAO userDAO;

    public AuthorizationServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.userDAO = new UserDAOImpl(entityManager);

    }

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
}
