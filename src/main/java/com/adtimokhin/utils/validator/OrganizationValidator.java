package com.adtimokhin.utils.validator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.adtimokhin.utils.validator.GeneralValidations.isEmailValid;
import static com.adtimokhin.utils.validator.GeneralValidations.isFieldEmpty;


/**
 * @author adtimokhin
 * 13.06.2021
 **/

@Component
public class OrganizationValidator {

    public ArrayList<String> validate(String companyName, String url, String email, String phone, String location, String token) {
        ArrayList<String> errors = areFieldsEmpty(companyName, url, email, phone, location, token);
        if (errors != null) {
            return errors;
        }
        errors = new ArrayList<>();
        if (!isEmailValid(email)) {
            errors.add("Email is invalid");
        }
        try {
            Integer.parseInt(token);
        } catch (NumberFormatException e) {
            errors.add("Token quantity is entered incorrectly. Please, enter a number");
        }
        if (errors.isEmpty()) {
            return null;
        }
        return errors;
    }


    private static ArrayList<String> areFieldsEmpty(String companyName, String url, String email, String phone, String location, String token) {
        ArrayList<String> errors = new ArrayList<>();
        if (isFieldEmpty(companyName)) {
            errors.add("Company name is invalid");
        }
        if (isFieldEmpty(url)) {
            errors.add("Url you\'ve provided is empty");
        }
        if (isFieldEmpty(email)) {
            errors.add("Email is invalid");
        }
        if (isFieldEmpty(phone)) {
            errors.add("Phone is invalid");
        }
        if (isFieldEmpty(location)) {
            errors.add("Location is invalid");
        }
        if (isFieldEmpty(token)) {
            errors.add("The amount of the tokens provided is invalid");
        }

        if (errors.isEmpty()) {
            return null;
        }
        return errors;
    }
}
