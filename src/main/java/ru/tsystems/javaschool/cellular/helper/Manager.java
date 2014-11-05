package ru.tsystems.javaschool.cellular.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Created by ferh on 11.10.14.
 */
public class Manager {
    //    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    @PersistenceContext(name = "PU")
    private static EntityManager em;

    public static EntityManager getEntityManager() {
        return em;
    }

    private Manager() {
    }
}

