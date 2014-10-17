package ru.tsystems.javaschool.cellular.service.impl;

import ru.tsystems.javaschool.cellular.dao.api.ContractDAO;
import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.dao.impl.ContractDAOImpl;
import ru.tsystems.javaschool.cellular.dao.impl.TariffDAOImpl;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.exception.TariffException;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Set;

/**
 * Created by ferh on 11.10.14.
 */
public class TariffServiceImpl implements TariffService {
    private EntityManager entityManager;
    private TariffDAO tariffDAO;
    private ContractDAO contractDAO;

    public TariffServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.tariffDAO = new TariffDAOImpl(entityManager);
        this.contractDAO = new ContractDAOImpl(entityManager);
    }

    @Override
    public void createTariff(Tariff tariff) throws TariffException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            tariffDAO.create(tariff);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new TariffException();
        }
    }

    @Override
    public Tariff getTariffById(long id) throws TariffException {
        try {
            return tariffDAO.read(id);
        } catch (DAOException e) {
            throw new TariffException();
        }
    }

    @Override
    public List<Tariff> getAllTariffs() throws TariffException {
        try {
            return tariffDAO.getAll();
        } catch (DAOException e) {
            throw new TariffException();
        }
    }

    @Override
    public void updateTariff(Tariff tariff) throws TariffException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            tariffDAO.update(tariff);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new TariffException();
        }
    }

    @Override
    public void deleteTariff(Tariff tariff) throws TariffException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            tariffDAO.delete(tariff);
            entityTransaction.commit();
        } catch (DAOException e) {
            throw new TariffException();
        }
    }

    @Override
    public void addOptionForTariff(Tariff tariff, Option option) {
        tariff.addOptions(option);
    }

    @Override
    public void deleteTariffOption(Tariff tariff, Option option) {
        Set<Option> optionSet = tariff.getOptions();
        optionSet.remove(option);
    }
}
