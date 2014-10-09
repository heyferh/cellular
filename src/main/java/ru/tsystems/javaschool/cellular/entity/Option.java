package ru.tsystems.javaschool.cellular.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ferh on 05.10.14.
 */
@Entity
@Table(name = "OPTIONS")
@NamedQuery(name = "Option.getAll", query = "SELECT o FROM Option o")
public class Option implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "option_id")
    private long id;

    private String title;
    private int cost;
    private int activationCost;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    public Option() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(int activationCost) {
        this.activationCost = activationCost;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (id != option.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
