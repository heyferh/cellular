package ru.tsystems.javaschool.cellular.helper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.tsystems.javaschool.cellular.entity.User;

/**
 * Created by ferh on 22.11.14.
 */
@Component
public class AuthHelper {
    public User getCurrentUser() {
        User user = ((UserBean) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUser();
        return user;
    }
}
