package com.adtimokhin.controllers.auth;

import com.adtimokhin.models.user.User;
import com.adtimokhin.services.company.CompanyService;
import com.adtimokhin.services.user.UserService;
import com.adtimokhin.utils.validator.OrganizationMemberValidator;
import com.adtimokhin.utils.validator.OrganizationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Controller
@RequestMapping("company/auth")
public class CompanyAuthController {

    private static final String ERROR_ATTRIBUTE = "errors";
    @Autowired
    private CompanyService companyService;

    @Autowired
    private OrganizationMemberValidator memberValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationValidator organizationValidator;


    @GetMapping("register")
    public String getRegister() {

        return "company/register";
    }


    @PostMapping("register")
    public String postRegister(@RequestParam(name = "companyName") String companyName,
                               @RequestParam(name = "websiteURL") String url,
                               @RequestParam(name = "email") String email,
                               @RequestParam(name = "phone") String phone,
                               @RequestParam(name = "location") String location,
                               @RequestParam(name = "token") String token, Model model) {
        // FOR NOW WE ASSUME THAT ALL FIELDS ENTERED ARE VALID
        ArrayList<String> errors = organizationValidator.validate(companyName, url,email,phone,location,token);
        if(errors != null){
            model.addAttribute("errors" , errors);
            return "company/register";
        }
        int tokenNumber = Integer.parseInt(token);
        companyService.addCompany(companyName, url, email, phone, location, tokenNumber);
        return "company/registerSuccess";
    }

    @GetMapping("sign_up")
    public String getSignUp(Model model) {
        if (model.getAttribute(ERROR_ATTRIBUTE) != null) { // dealing with errors
            System.out.println(model.getAttribute(ERROR_ATTRIBUTE)); //Todo: add error messages to be passed via model
        }
        return "company/signUp";
    }

    @PostMapping("sign_up")
    public String postSignUp(@RequestParam(name = "first_name") String firstName,
                             @RequestParam(name = "last_name") String lastName,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "secondPassword") String secondPassword,
                             @RequestParam(name = "email") String email,
                             @RequestParam(name = "phone") String phone,
                             @RequestParam(name = "token") String token,
                             Model model) {
        ArrayList<String> errors =
                memberValidator.validate(firstName, lastName, email, password, secondPassword, phone, token);
        if (errors == null) {
            User user = new User(email, password);
            userService.addOrganizationMember(user, token, firstName, lastName);
        } else {
            model.addAttribute(ERROR_ATTRIBUTE, errors);
            return "company/signUp";
        }
        return "index";

    }

}
