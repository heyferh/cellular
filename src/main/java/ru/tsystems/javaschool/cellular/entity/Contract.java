package ru.tsystems.javaschool.cellular.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ferh on 03.10.14.
 */
@Entity
@Table(name = "CONTRACT")
@NamedQuery(name = "Contract.getAll", query = "SELECT c FROM Contract c")
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contract_id")
    private long id;

    private String phoneNumber;
    private boolean isBlockedByOperator;
    private boolean isBlockedByClient;

    @OneToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
            (
                    name = "CONTRACT_OPTION",
                    joinColumns = {@JoinColumn(name = "contract_id")},
                    inverseJoinColumns = {@JoinColumn(name = "option_id")}
            )
    private Set<Option> options = new HashSet<Option>();

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

    public Set<Option> getOptions() {
        return options;
    }

    public void addOptions(Option option) {

        this.options.add(option);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", isBlockedByOperator=" + isBlockedByOperator +
                ", isBlockedByClient=" + isBlockedByClient +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contract)) return false;

        Contract contract = (Contract) o;

        if (!phoneNumber.equals(contract.phoneNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }
}

