package com.adtimokhin.utils.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author adtimokhin
 * 13.06.2021
 **/

@Component
public class GeneralValidations {

    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";//TODO: I've tried different regular expressions from https://mailtrap.io/blog/java-email-validation/#Adding-restrictions-to-the-domain-name-part , though, only the one I've chosen works. It provides more or less accurate check for emails, however, some options are still will be validated as wrong.
    private static Pattern emailPattern = Pattern.compile(emailRegex);


    protected static boolean isEmailValid(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    protected static boolean isFieldEmpty(String field){
        if(field == null){
            return true;
        }
        if (field.isEmpty()){
            return true;
        }
        return false;
    }
}
