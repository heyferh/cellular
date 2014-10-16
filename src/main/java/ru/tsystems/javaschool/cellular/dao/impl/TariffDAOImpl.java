package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.entity.Tariff;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class TariffDAOImpl implements TariffDAO {
    EntityManager entityManager;

    public TariffDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Tariff tariff) {
        entityManager.persist(tariff);
    }

    @Override
    public Tariff read(long id) {
        return entityManager.find(Tariff.class, id);
    }

    @Override
    public void update(Tariff tariff) {
        entityManager.merge(tariff);
    }

    @Override
    public void delete(Tariff tariff) {
        entityManager.remove(tariff);
    }

    public List<Tariff> getAll() {
        return entityManager.createNamedQuery("Tariff.getAll", Tariff.class).getResultList();
    }
}
