package ru.tsystems.javaschool.cellular.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ferh on 05.10.14.
 */
@Entity
@Table(name = "OPTION_DEPENDENCIES")
@NamedQuery(name = "OptionDependency.getAll", query = "SELECT od FROM OptionDependency od")
public class OptionDependency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "option_dependency_id")
    private long id;

    @Column(name = "src_option_id")
    private Long srcId;

    @Column(name = "dep_option_id")
    private Long depId;

    private Boolean isCompatible;

    public OptionDependency() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCompatible() {
        return isCompatible;
    }

    public void setIsCompatible(Boolean isCompatible) {
        this.isCompatible = isCompatible;
    }

    public Long getSrcId() {
        return srcId;
    }

    public void setSrcId(Long srcId) {
        this.srcId = srcId;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }
}
