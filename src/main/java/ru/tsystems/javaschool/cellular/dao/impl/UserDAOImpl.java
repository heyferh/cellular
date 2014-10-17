package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.UserDAO;
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
    public String getUsersRole(String email) throws DAOException {
        try {
            return (String) entityManager.createQuery("select user.role from User user where user.email=:email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new DAOException("Unable to get user with email: " + email);
        }
    }
}
