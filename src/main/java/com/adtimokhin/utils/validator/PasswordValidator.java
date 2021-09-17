package com.adtimokhin.utils.validator;

import com.adtimokhin.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.adtimokhin.utils.validator.UserValidator.isPasswordValid;

/**
 * @author adtimokhin
 * 05.07.2021
 **/

@Component
public class PasswordValidator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ArrayList<String> validate(User user, String oldPassword, String newPassword, String newPasswordRepeated) {
        ArrayList<String> errors = new ArrayList<>();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            errors.add("Old password does not match");
            return errors;
        }

        ArrayList<String> e = validate(newPassword, newPasswordRepeated);
        if (e != null) {
            return e;
        }

        if (newPassword.equals(oldPassword)) {
            errors.add("Your password match your current password");
            return errors;
        }
        return null;
    }

    public ArrayList<String> validate(String passwordOne, String passwordTwo) {
        ArrayList<String> errors = new ArrayList<>();
        if (!isPasswordValid(passwordOne)) {
            errors.add("Your password should be at least 8 characters long and contain one lowercase letter, one uppercase letter and one number");
        }
        if (!passwordOne.equals(passwordTwo)) {
            errors.add("The passwords do not match!");
        }

        if (errors.size() == 0) {
            return null;
        }

        return errors;
    }


}
