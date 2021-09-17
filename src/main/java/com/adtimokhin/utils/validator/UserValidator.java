package com.adtimokhin.utils.validator;

import com.adtimokhin.enums.Role;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.adtimokhin.utils.validator.GeneralValidations.isEmailValid;
import static com.adtimokhin.utils.validator.GeneralValidations.isFieldEmpty;

/**
 * @author adtimokhin
 * 12.04.2021
 **/

@Component
public class UserValidator {

    @Autowired
    private UserService userService;

    private static final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})";
    private static Pattern passwordPattern = Pattern.compile(passwordRegex);

    public @Nullable
    ArrayList<String> validate(String email, String password, String secondPassword, Role role) {
        // We start by checking that fields have been validly filled
        ArrayList<String> errors = new ArrayList<>();
        if (!isPasswordValid(password)) {
            errors.add("Your password should be at least 8 characters long and contain one lowercase letter," +
                    " one uppercase letter and one number");
        }
        if (!isEmailValid(email)) {
            errors.add("Your email is in invalid form");
        }
        // User should deal with typo mistakes first and only then should be tested for the validity of their input.
        if (errors.size() != 0) {
            return errors;
        }

        if (userService.getUser(email) != null) {
            errors.add("User with such email already exists");
        }
        if (!password.equals(secondPassword)) {
            errors.add("The passwords do not match");
        }

        if (role == null) {
            /*Todo: we don't want to allow anyone to sign up as admin, for example. When signing up the user can
                 be either a student or a parent (at least in the sign up page for students and parents). Realize that!*/
            errors.add("Incorrect role chosen");
        }

        return (errors.size() != 0) ? errors : null;

    }


    public static boolean isPasswordValid(String password) {
        //Todo: когда будет потребность, освой regular expressions, и перепиши этот метод с их участем
        //Matcher matcher = passwordPattern.matcher(password);
        //return matcher.matches();
       if(isFieldEmpty(password)){
           return false;
       }
        if (password.length() < 8) {
            return false;
        }
        int lowerCaseLettersCount = 0;
        int upperCaseLettersCount = 0;
        int numberCount = 0;
//        int specialSymbolCount = 0;
        char[] symbols = password.toCharArray();
        for (int i = 0; i < symbols.length; i++) {
            int ascii = symbols[i];
            if (ascii >= 97 && ascii <= 122) {
                lowerCaseLettersCount++;
            } else if (ascii >= 65 && ascii <= 90) {
                upperCaseLettersCount++;
            } else if (ascii >= 48 && ascii <= 57) {
                numberCount++;
            }
        }
        if (lowerCaseLettersCount == 0) {
            return false;
        }
        if (upperCaseLettersCount == 0) {
            return false;
        }
        return numberCount != 0;

    }


}
