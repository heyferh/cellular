package ru.tsystems.javaschool.cellular.helper;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.tsystems.javaschool.cellular.entity.Client;
import ru.tsystems.javaschool.cellular.entity.User;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by ferh on 22.11.14.
 */
public class UserBean implements UserDetails {

    private final static Logger logger = Logger.getLogger(UserBean.class);

    User user;

    public UserBean(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.info("Getting authorities for user: " + user + ". Role: " + getRole(user));
        return Arrays.asList(new SimpleGrantedAuthority(getRole(user)));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private String getRole(User user) {
        return (user instanceof Client) ? "User" : "Admin";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
