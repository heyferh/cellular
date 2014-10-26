package ru.tsystems.javaschool.cellular.service.api;

import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.exception.ClientException;

import java.util.List;

/**
 * Created by ferh on 08.10.14.
 */
public interface ClientService {

    /**
     * Creates new client.
     *
     * @param client Client object which is going to be created.
     * @throws ClientException if there is an error while persisting object.
     */
    public void createClient(Client client) throws ClientException;

    /**
     * Gets client by id.
     *
     * @param id id.
     * @return Client object if and only if it exists.
     * @throws ClientException if there's no such client in database.
     */
    public Client getClientById(long id) throws ClientException;

    /**
     * Gets client by its email.
     *
     * @param email email.
     * @return Client object with given email.
     * @throws ClientException if there's no such client.
     */
    public Client getClientByEmail(String email) throws ClientException;

    /**
     * Gets client by given phone number.
     *
     * @param phoneNumber phone number.
     * @return Client with given phone number.
     * @throws ClientException if there's no client with given phone number.
     */
    public Client getClientByPhoneNumber(String phoneNumber) throws ClientException;

    /**
     * Gets list of all clients.
     *
     * @return List of all clients.
     * @throws ClientException if there is an error while getting clients.
     */
    public List<Client> getAllClients() throws ClientException;

    /**
     * Updates information about given client.
     *
     * @param client client.
     * @throws ClientException if there is a persistence error.
     */
    public void updateClient(Client client) throws ClientException;

    /**
     * Deletes client from database.
     *
     * @param client client.
     * @throws ClientException if there's an error while removing client.
     */
    public void deleteClient(Client client) throws ClientException;
}
