package ru.tsystems.javaschool.cellular.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ferh on 05.10.14.
 */
@Entity
@Table(name = "OPTIONS")
@NamedQuery(name = "Option.getAll", query = "SELECT op FROM Option op")
public class Option implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private long id;

    @NotEmpty(message = "Title must not be empty")
    @Length(max = 32, message = "Max length is 32")
    private String title;

    @Min(value = 0, message = "Must be non-negative integer value")
    private int cost;

    @Min(value = 0, message = "Must be non-negative integer value")
    private int activationCost;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "REQUIRED_OPTIONS",
            joinColumns = @JoinColumn(name = "src_opt_id"),
            inverseJoinColumns = @JoinColumn(name = "required_opt_id")
    )
    private Set<Option> requiredOptions = new HashSet<Option>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "INCOMPATIBLE_OPTIONS",
            joinColumns = @JoinColumn(name = "src_opt_id"),
            inverseJoinColumns = @JoinColumn(name = "incompatible_opt_id")
    )
    private Set<Option> incompatibleOptions = new HashSet<Option>();

    public Option() {
    }

    public Option(long id, String title, int cost, int activationCost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.activationCost = activationCost;
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

    public int getActivationCost() {
        return activationCost;
    }

    public void setActivationCost(int activationCost) {
        this.activationCost = activationCost;
    }

    public Set<Option> getRequiredOptions() {
        return requiredOptions;
    }

    public void addRequiredOptions(Option requiredOption) {
        this.requiredOptions.add(requiredOption);
    }

    public Set<Option> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void addIncompatibleOptions(Option incompatibleOption) {
        this.incompatibleOptions.add(incompatibleOption);
    }

    @Override
    public String toString() {
        return "Option{" +
                "title='" + title + '\'' +
                ", cost=" + cost +
                ", activationCost=" + activationCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;

        Option option = (Option) o;

        if (id != option.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
