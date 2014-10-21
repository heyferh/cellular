package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.UserDAO;
import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * Created by ferh on 17.10.14.
 */
public class UserDAOImpl implements UserDAO {
    private EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        try {
            return (User) entityManager.createQuery("select user from User user where user.email=:email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new DAOException("User with email: " + email + " doesn't exist.", e);
        }
    }
}
