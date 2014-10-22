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
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public Option getOptionById(long id) throws OptionException {
        try {
            return optionDAO.get(id);
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
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
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
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public List<Option> getOptionsForTariff(long tariff_id) throws OptionException {
        try {
            return optionDAO.getOptionsForTariff(tariff_id);
        } catch (DAOException e) {
            throw new OptionException();
        }
    }

    @Override
    public void manageIncompatibleOptions(long id, String[] ids) throws OptionException {
        Option option = null;
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            option = optionDAO.get(id);
            for (Option option1 : option.getIncompatibleOptions()) {
                option1.getIncompatibleOptions().remove(option);
            }
            option.getIncompatibleOptions().clear();
            if (ids != null) {
                Option incompatibleOption = null;
                for (String optionId : ids) {
                    incompatibleOption = optionDAO.get(Long.parseLong(optionId));
                    option.addIncompatibleOptions(incompatibleOption);
                    incompatibleOption.addIncompatibleOptions(option);
                    optionDAO.update(incompatibleOption);
                }
            }
            optionDAO.update(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new OptionException();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    @Override
    public void manageRequiredOptions(long id, String[] ids) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Option option = optionDAO.get(id);
            option.getRequiredOptions().clear();
            if (ids != null) {
                for (String optionId : ids) {
                    option.addRequiredOptions(optionDAO.get(Long.parseLong(optionId)));
                }
            }
            optionDAO.update(option);
            entityTransaction.commit();
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }
}
