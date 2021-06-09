package com.adtimokhin.utils.validator;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.company.Token;
import com.adtimokhin.models.user.User;
import com.adtimokhin.services.company.CompanyService;
import com.adtimokhin.services.company.TokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Component
public class OrganizationMemberValidator {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TokenService tokenService;


    public boolean supports(@NotNull Class<?> clazz) {
        return User.class.equals(clazz);
    }


    public @Nullable
    ArrayList<String> validate(String firstName, String lastName, String email, String password, String secondPassword, String phone, String tokenValue) {

        ArrayList<String> errors = userValidator.validate(email, password, secondPassword, Role.ROLE_ORGANIZATION_MEMBER);
        Company company = companyService.getCompanyByPhone(phone);
        if (errors != null) {
            checkNameAndCompany(firstName, lastName, errors, company);
            return errors;
        } else {
            errors = new ArrayList<>();
            checkNameAndCompany(firstName, lastName, errors, company);
            if (!errors.isEmpty()) {
                return errors;
            }

        }

        Token token = tokenService.getToken(tokenValue);
        if (token == null) {
            return new ArrayList<>(Collections.singleton("Invalid token!"));
        }
        if (!token.getCompany().getPhone().equals(phone)) {
            return new ArrayList<>(Collections.singleton("Invalid token!"));
        }
        if (token.getUser() != null){
            return new ArrayList<>(Collections.singleton("Invalid token!"));
        }

        return null;
    }

    private void checkNameAndCompany(String firstName, String lastName, ArrayList<String> errors, Company company) {
        if (company == null) {
            errors.add("Invalid phone number!");
        }
        if (firstName == null || lastName == null) {
            errors.add("Name you\'ve entered is in incorrect format!");
        } else {
            if (firstName.isEmpty() || lastName.isEmpty()) {
                errors.add("Name you\'ve entered is in incorrect format!");
            }
        }
    }
}
