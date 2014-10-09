package ru.tsystems.javaschool.cellular.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ferh on 03.10.14.
 */
@Entity
@Table(name = "CONTRACTS")
@NamedQuery(name = "Contract.getAll", query = "SELECT c FROM Contract c")
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contract_id")
    private long id;

    private String phoneNumber;
    private boolean isBlockedByOperator;
    private boolean isBlockedByClient;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private ru.tsystems.javaschool.cellular.entity.Tariff tariff;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Contract() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isBlockedByOperator() {
        return isBlockedByOperator;
    }

    public void setBlockedByOperator(boolean isBlockedByOperator) {
        this.isBlockedByOperator = isBlockedByOperator;
    }

    public boolean isBlockedByClient() {
        return isBlockedByClient;
    }

    public void setBlockedByClient(boolean isBlockedByClient) {
        this.isBlockedByClient = isBlockedByClient;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (id != contract.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}

