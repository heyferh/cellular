package ru.tsystems.javaschool.cellular.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ferh on 17.10.14.
 */
@Entity
@Table(name = "ADMINISTRATORS")
public class Administrator extends User {

    public Administrator() {
    }
}
