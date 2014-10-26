package ru.tsystems.javaschool.cellular.dao.api;

import ru.tsystems.javaschool.cellular.exception.DAOException;

import java.util.List;

/**
 * Created by ferh on 22.10.14.
 */
public interface CommonDAO<T> {
    /**
     * Persists object in database.
     *
     * @param object an entity to be created.
     * @throws DAOException if there is an error while
     *                      creating an object.
     */
    public void create(T object) throws DAOException;

    /**
     * Get object by its id.
     *
     * @param id database key.
     * @return object if there's no error.
     * @throws DAOException if there's an error while getting the object.
     */
    public T get(long id) throws DAOException;

    /**
     * Returns list containing all of the objects of the requested type.
     *
     * @return list of objects.
     * @throws DAOException if there is an error during read from database.
     */
    public List<T> getAll() throws DAOException;

    /**
     * Updates object in database.
     *
     * @param object object to update.
     * @throws DAOException if there is an error while updating.
     */
    public void update(T object) throws DAOException;

    /**
     * Delete object from database.
     *
     * @param object object to detele.
     * @throws DAOException if there is/are records that depend on this object.
     */
    public void delete(T object) throws DAOException;
}
