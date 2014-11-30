package ru.tsystems.javaschool.cellular.dao.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.javaschool.cellular.dao.api.UserDAO;
import ru.tsystems.javaschool.cellular.entity.User;
import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.OBJECT_NOT_FOUND;
import static ru.tsystems.javaschool.cellular.exception.DAOException.ERROR_CODE.PERSISTENCE_EXCEPTION;

/**
 * Created by ferh on 17.10.14.
 */
@Repository("UserDAO")
public class UserDAOImpl extends CommonDAOImpl<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        try {
            return (User) entityManager.createQuery("select user from User user where user.email=:email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new DAOException(OBJECT_NOT_FOUND);
        } catch (PersistenceException e) {
            throw new DAOException(PERSISTENCE_EXCEPTION, e);
        }
    }
}
