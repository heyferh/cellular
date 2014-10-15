package ru.tsystems.javaschool.cellular.service.impl;

import ru.tsystems.javaschool.cellular.dao.impl.OptionDAO;
import ru.tsystems.javaschool.cellular.entity.Manager;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.OptionService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Created by ferh on 11.10.14.
 */
public class OptionServiceImpl implements OptionService {
    private EntityManager entityManager = Manager.getEntityManager();
    private OptionDAO optionDAO = new OptionDAO(entityManager, Option.class);

    @Override
    public void createOption(Option option) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            optionDAO.create(option);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public Option getOptionById(long id) throws DAOException {
//        EntityTransaction entityTransaction = entityManager.getTransaction();
//        try {
//            entityTransaction.begin();
            Option option = optionDAO.read(id);
//            entityTransaction.commit();
            if (option == null) throw new DAOException("Option with id: " + id + " doesn't exist");
            return option;
//        } catch (RuntimeException re) {
//            if (entityTransaction.isActive()) {
//                entityTransaction.rollback();
//            }
//            throw re;
//        }
    }

    @Override
    public List<Option> getAllOptions() throws DAOException {
//        EntityTransaction entityTransaction = entityManager.getTransaction();
//        try {
//            entityTransaction.begin();
            List<Option> lst = optionDAO.getAll();
            if (lst.size() == 0) throw new DAOException("There is no options in database yet");
            return lst;
//        } catch (RuntimeException re) {
//            if (entityTransaction.isActive()) {
//                entityTransaction.rollback();
//            }
//            throw re;
//        }
    }

    @Override
    public void updateOption(Option option) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            optionDAO.update(option);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public void deleteOption(Option option) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            optionDAO.delete(option);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public List<Option> getOptionsForTariff(String title) throws DAOException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            List<Option> lst = optionDAO.getOptionsForTariff(title);
            entityTransaction.commit();
            if (lst.size() == 0) throw new DAOException("There is no options for " + title);
            return lst;
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public void addIncompatibleOption(Option src, Option option) {
        src.getIncompatibleOptions().add(option);
    }

    @Override
    public void removeIncompatibleOption(Option src, Option option) {
        src.getIncompatibleOptions().remove(option);
    }

    @Override
    public void addRequiredOption(Option src, Option option) {
        src.getRequiredOptions().add(option);
    }

    @Override
    public void removeRequiredOption(Option src, Option option) {
        src.getRequiredOptions().remove(option);
    }
}
