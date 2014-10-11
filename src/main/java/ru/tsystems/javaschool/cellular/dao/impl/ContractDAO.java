package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.AbstractDAO;
import ru.tsystems.javaschool.cellular.entity.Contract;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class ContractDAO extends AbstractDAO<Contract> {

    public ContractDAO(EntityManager entityManager, Class<Contract> type) {
        super(entityManager, type);
    }

    public Contract getContractByPhoneNumber(String phoneNumber) {
        return null;
    }

    public List<Contract> getAll() {
        return entityManager.createNamedQuery("Contract.getAll", Contract.class).getResultList();
    }
}
