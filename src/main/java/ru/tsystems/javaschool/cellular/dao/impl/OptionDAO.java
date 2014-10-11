package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.AbstractDAO;
import ru.tsystems.javaschool.cellular.entity.Option;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class OptionDAO extends AbstractDAO<Option> {

    public OptionDAO(EntityManager entityManager, Class<Option> type) {
        super(entityManager, type);
    }

    public List<Option> getAll() {
        return entityManager.createNamedQuery("Option.getAll", Option.class).getResultList();
    }
}
