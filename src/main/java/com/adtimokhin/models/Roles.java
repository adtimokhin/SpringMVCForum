package com.adtimokhin.models;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

public enum Roles implements GrantedAuthority {

    ROLE_STUDENT,
    ROLE_PARENT,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

    public String getRole(){
        return name().substring(5);
    }
}
