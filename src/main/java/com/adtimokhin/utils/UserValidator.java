package com.adtimokhin.utils;

import com.adtimokhin.enums.Role;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author adtimokhin
 * 12.04.2021
 **/

@Component
public class UserValidator {

    @Autowired
    private UserService userService;

    public @Nullable
    ArrayList<String> validate(String email, String password, String secondPassword, Role role) {
        // We start by checking that fields have been validly filled
        ArrayList<String> errors = new ArrayList<>();
        if (password.length() < 5) { // Todo: come back and set a proper password check
            errors.add("Your password should be at least 7 characters long");
        }
        if (!isEmailValid(email)) {
            errors.add("Your email is in invalid form!");
        }
        // User should deal with typo mistakes first and only then should be tested for the validity of their inout.
        if (errors.size() != 0) {
            return errors;
        }

        if (userService.getUser(email) != null) {
            errors.add("User with such email already exists!");
        }
        if (!password.equals(secondPassword)) {
            errors.add("The passwords do not match!");
        }

        if (role == null) {
            /*Todo: we don't want to allow anyone to sign up as admin, for example. When signing up the user can
                 be either a student or a parent (at least in the sign up page for students and parents). Realize that!*/
            errors.add("Incorrect role chosen!");
        }

        return (errors.size() != 0) ? errors : null;

    }

    private static boolean isEmailValid(String email) {
        //Todo: do a bit of research to find what are the actual password requirements for inputting an email.
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        return parts[1].split("\\.").length == 2;
    }


}
