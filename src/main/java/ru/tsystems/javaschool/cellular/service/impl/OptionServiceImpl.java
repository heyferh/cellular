package ru.tsystems.javaschool.cellular.service.impl;

import ru.tsystems.javaschool.cellular.dao.api.OptionDAO;
import ru.tsystems.javaschool.cellular.dao.impl.OptionDAOImpl;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.exception.OptionException;
import ru.tsystems.javaschool.cellular.service.api.OptionService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Created by ferh on 11.10.14.
 */
public class OptionServiceImpl implements OptionService {
    private EntityManager entityManager;
    private OptionDAO optionDAO;

    public OptionServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.optionDAO = new OptionDAOImpl(entityManager);
    }

    @Override
    public void createOption(Option option) throws OptionException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            optionDAO.create(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new OptionException();
        }
    }

    @Override
    public Option getOptionById(long id) throws OptionException {
        try {
            return optionDAO.read(id);
        } catch (DAOException e) {
            throw new OptionException();
        }
    }

    @Override
    public List<Option> getAllOptions() throws OptionException {
        try {
            return optionDAO.getAll();
        } catch (DAOException e) {
            throw new OptionException();
        }
    }

    @Override
    public void updateOption(Option option) throws OptionException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            optionDAO.update(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new OptionException();
        }
    }

    @Override
    public void deleteOption(Option option) throws OptionException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            optionDAO.delete(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new OptionException();
        }
    }

    @Override
    public List<Option> getOptionsForTariff(String title) throws OptionException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        List<Option> optionList;
        try {
            entityTransaction.begin();
            optionList = optionDAO.getOptionsForTariff(title);
            entityTransaction.commit();
            return optionList;
        } catch (DAOException e) {
            throw new OptionException();
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
