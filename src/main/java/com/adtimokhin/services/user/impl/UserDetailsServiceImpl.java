package com.adtimokhin.services.user.impl;

import com.adtimokhin.models.user.User;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //Services
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUser(s);
        if (user == null) {
            throw new UsernameNotFoundException("User with such email is not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRoles());
    }


}
