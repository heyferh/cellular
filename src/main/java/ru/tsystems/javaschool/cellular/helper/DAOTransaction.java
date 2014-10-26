package ru.tsystems.javaschool.cellular.helper;

import ru.tsystems.javaschool.cellular.exception.DAOException;

import javax.persistence.EntityManager;

/**
 * Created by ferh on 16.10.14.
 */
public class DAOTransaction {
    TransactionManager transactionManager = new TransactionManager(Manager.getEntityManager());

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    private class TransactionManager {

        private EntityManager entityManager;

        private TransactionManager(EntityManager em) {
            this.entityManager = em;
        }

        public void begin() throws DAOException {
            try {
                entityManager.getTransaction().begin();
            } catch (RuntimeException re) {
                throw new DAOException();
            }
        }

        public void commit() throws DAOException {
            try {
                entityManager.getTransaction().commit();
            } catch (RuntimeException re) {
                throw new DAOException();
            }
        }

        public void rollback() throws DAOException {
            try {
                entityManager.getTransaction().rollback();
            } catch (RuntimeException re) {
                throw new DAOException();
            }
        }
    }
}
