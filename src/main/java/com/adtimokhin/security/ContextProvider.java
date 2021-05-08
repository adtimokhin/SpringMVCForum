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
public class ContextProvider {

    @Autowired
    private UserService userService;

    private static SecurityContext getSecurityContext(){return SecurityContextHolder.getContext();}

    public User getUser(){//Todo: Separate this gigantic line of code and make checks
      return (User) getSecurityContext().getAuthentication().getPrincipal();
    }
}
