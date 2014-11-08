package ru.tsystems.javaschool.cellular.dao.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.javaschool.cellular.dao.api.TariffDAO;
import ru.tsystems.javaschool.cellular.entity.Tariff;

/**
 * Created by ferh on 09.10.14.
 */
@Repository("TariffDAO")
public class TariffDAOImpl extends CommonDAOImpl<Tariff> implements TariffDAO {

    public TariffDAOImpl() {
        super(Tariff.class);
    }
}
