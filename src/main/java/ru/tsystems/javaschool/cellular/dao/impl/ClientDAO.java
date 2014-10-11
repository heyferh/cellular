package ru.tsystems.javaschool.cellular.dao.impl;

import ru.tsystems.javaschool.cellular.dao.api.AbstractDAO;
import ru.tsystems.javaschool.cellular.entity.Client;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by ferh on 09.10.14.
 */
public class ClientDAO extends AbstractDAO<Client> {

    public ClientDAO(EntityManager entityManager, Class<Client> type) {
        super(entityManager, type);
    }

    public Client findClientByPhoneNumber(String phoneNumber) {
        Query query = entityManager.createQuery("select c.client from Contract c where c.client.id=:number")
                .setParameter("number", phoneNumber);
        return (Client) query.getSingleResult();
    }

    public List<Client> getAll() {
        return entityManager.createNamedQuery("Client.getAll", Client.class).getResultList();
    }
}
