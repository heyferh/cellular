package ru.tsystems.javaschool.cellular.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ferh on 03.10.14.
 */
@Entity
@Table(name = "TARIFFS")
@NamedQuery(name = "Tariff.getAll", query = "SELECT t FROM Tariff t")
public class Tariff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tariff_id")
    private long id;

    @NotEmpty(message = "Title must not be empty")
    @Length(max = 32, message = "Max length is 32")
    private String title;

    @Min(value = 0, message = "Must be non-negative integer value")
    private int cost;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TARIFFS_OPTIONS",
            joinColumns = @JoinColumn(name = "tariff_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private Set<Option> options = new HashSet<Option>();

    public Tariff() {
    }

    public Tariff(String title, int cost) {
        this.title = title;
        this.cost = cost;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void addOptions(Option options) {
        this.options.add(options);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "title='" + title + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tariff)) return false;

        Tariff tariff = (Tariff) o;

        if (id != tariff.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}