package ru.tsystems.javaschool.cellular.entity;

import javax.persistence.*;

/**
 * Created by ferh on 17.10.14.
 */
@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    protected String email;
    protected String password;
    protected String role = "user";

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
