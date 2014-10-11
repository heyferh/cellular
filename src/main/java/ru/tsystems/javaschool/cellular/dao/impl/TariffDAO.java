package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.AbstractDAO;
import ru.tsystems.javaschool.cellular.entity.Tariff;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class TariffDAO extends AbstractDAO<Tariff> {

    public TariffDAO(EntityManager entityManager, Class<Tariff> type) {
        super(entityManager, type);
    }

    public List<Tariff> getAll() {
        return entityManager.createNamedQuery("Tariff.getAll", Tariff.class).getResultList();
    }
}