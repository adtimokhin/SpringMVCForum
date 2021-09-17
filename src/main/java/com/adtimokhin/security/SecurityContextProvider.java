package com.adtimokhin.security;

import com.adtimokhin.models.user.User;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 14.04.2021
 **/
@Component
public class SecurityContextProvider {

    @Autowired
    private UserService userService;

    private static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    public User getUser() {
        String email = (String) getSecurityContext().getAuthentication().getPrincipal();
        return userService.getUser(email);
    }
}
