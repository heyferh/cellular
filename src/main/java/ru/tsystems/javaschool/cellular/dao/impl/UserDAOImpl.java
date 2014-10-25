package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.UserDAO;
import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * Created by ferh on 17.10.14.
 */
public class UserDAOImpl extends CommonDAOImpl<User> implements UserDAO {


    public UserDAOImpl(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        try {
            logger.info("Getting user with email: " + email);
            return (User) entityManager.createQuery("select user from User user where user.email=:email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (PersistenceException e) {
            logger.error("User with email: " + email + " doesn't exist.");
            throw new DAOException("User with email: " + email + " doesn't exist.", e);
        }
    }
}
