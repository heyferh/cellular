package ru.tsystems.javaschool.cellular.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ferh on 03.10.14.
 */
@Entity
@Table(name = "CLIENTS")
@NamedQuery(name = "Client.getAll", query = "SELECT c FROM Client c")
public class Client extends User implements Serializable {
    private String firstName;
    private String lastName;
    @Temporal(TemporalType.DATE)
    private java.util.Date dayOfBirth;
    private String idCard;
    private String address;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "client")
    private Set<Contract> contracts = new HashSet<Contract>();

    public Client() {
    }

    public Client(String firstName, String lastName, Date dayOfBirth, String idCard, String address, String email, String accountPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
        this.idCard = idCard;
        this.address = address;
        this.email = email;
        this.password = accountPassword;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dayOfBirth=" + dayOfBirth +
                ", idCard='" + idCard + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        return id == client.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
