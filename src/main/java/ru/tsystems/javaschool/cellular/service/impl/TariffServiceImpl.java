package ru.tsystems.javaschool.cellular.service.impl;

import ru.tsystems.javaschool.cellular.dao.impl.TariffDAO;
import ru.tsystems.javaschool.cellular.entity.Manager;
import ru.tsystems.javaschool.cellular.entity.Tariff;
import ru.tsystems.javaschool.cellular.exception.DAOException;
import ru.tsystems.javaschool.cellular.service.api.TariffService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Created by ferh on 11.10.14.
 */
public class TariffServiceImpl implements TariffService {
    EntityManager entityManager = Manager.getEntityManager();
    private TariffDAO tariffDAO = new TariffDAO(entityManager, Tariff.class);

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
    public Tariff getTariffById(long id) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Tariff tariff = tariffDAO.read(id);
            entityTransaction.commit();
            if (tariff == null) throw new DAOException("Tariff with id: " + id + " doesn't exist");
            return tariff;
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
    }

    @Override
    public List<Tariff> getAllTariffs() {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            List<Tariff> lst = tariffDAO.getAll();
            if (lst.size() == 0) throw new DAOException("There is no clients in database yet");
            return lst;
        } catch (RuntimeException re) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw re;
        }
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
}
