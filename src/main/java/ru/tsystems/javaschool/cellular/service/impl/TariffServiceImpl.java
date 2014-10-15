package ru.tsystems.javaschool.cellular.service.impl;

import ru.tsystems.javaschool.cellular.dao.impl.ContractDAO;
import ru.tsystems.javaschool.cellular.dao.impl.TariffDAO;
import ru.tsystems.javaschool.cellular.entity.Contract;
import ru.tsystems.javaschool.cellular.entity.Manager;
import ru.tsystems.javaschool.cellular.entity.Option;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Set;

/**
 * Created by ferh on 11.10.14.
 */
public class TariffServiceImpl implements TariffService {
    private EntityManager entityManager = Manager.getEntityManager();
    private TariffDAO tariffDAO = new TariffDAO(entityManager, Tariff.class);
    private ContractDAO contractDAO = new ContractDAO(entityManager, Contract.class);

    @Override
    public void createTariff(Tariff tariff) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            tariffDAO.create(tariff);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public Tariff getTariffById(long id) throws DAOException {
//        EntityTransaction entityTransaction = entityManager.getTransaction();
//        try {
//            entityTransaction.begin();
            Tariff tariff = tariffDAO.read(id);
//            entityTransaction.commit();
            if (tariff == null) throw new DAOException("Tariff with id: " + id + " doesn't exist");
            return tariff;
//        } catch (RuntimeException re) {
//            if (entityTransaction.isActive()) {
//                entityTransaction.rollback();
//            }
//            throw re;
//        }
    }

    @Override
    public List<Tariff> getAllTariffs() throws DAOException {
//        EntityTransaction entityTransaction = entityManager.getTransaction();
//        try {
//            entityTransaction.begin();
            List<Tariff> lst = tariffDAO.getAll();
//            entityTransaction.commit();
            if (lst.size() == 0) throw new DAOException("There is no tariffs in database yet");
            return lst;
//        } catch (RuntimeException re) {
//            if (entityTransaction.isActive()) {
//                entityTransaction.rollback();
//            }
//            throw re;
//        }
    }

    @Override
    public void updateTariff(Tariff tariff) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            tariffDAO.update(tariff);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public void deleteTariff(Tariff tariff) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            tariffDAO.delete(tariff);
            entityTransaction.commit();
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
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
