package com.adtimokhin.security;

import com.adtimokhin.models.user.User;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 10.04.2021
 **/
@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userEmail = authentication.getName();
        User user = userService.getUser(userEmail);

        //checking email
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("No such account is found");
        }

        //checking if user is blocked
        if (user.isBanned()) {
            throw new LockedException("User is blocked");
        }

        //checking passwords
        String password = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password has been entered");
        }

        return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getRoles());

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
