package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.entity.Tariff;

import javax.persistence.EntityManager;

/**
 * Created by ferh on 09.10.14.
 */
public class TariffDAOImpl extends CommonDAOImpl<Tariff> implements TariffDAO {
    public TariffDAOImpl(EntityManager entityManager) {
        super(entityManager, Tariff.class);
    }
}
