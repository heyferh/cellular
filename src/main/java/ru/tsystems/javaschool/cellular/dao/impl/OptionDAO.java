package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.AbstractDAO;
import ru.tsystems.javaschool.cellular.entity.Option;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    public List<Option> getOptionsForTariff(String title) {
        Query query = entityManager.createQuery("select t.options from Tariff t where t.title=:tariff_title")
                .setParameter("tariff_title", title);
        return query.getResultList();
    }
}
