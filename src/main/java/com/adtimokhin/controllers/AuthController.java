package com.adtimokhin.controllers;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.user.User;
import com.adtimokhin.services.user.UserService;
import com.adtimokhin.utils.UserValidator;
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
 * 09.04.2021
 **/

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    private static final String ERROR_ATTRIBUTE = "errors";

    @RequestMapping("/login")
    public String getSignIn(@RequestParam(value = "error", required = false) Boolean error, Model model) {
        //Todo: когда юзер заблокирован, он все равно получает это же сообщение. Сообщение для заблокированных юзеров
        // должно быть иним
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("error", true);
        }
        return "auth/signIn";
    }

    @GetMapping("/sign_up")
    public String getSignUp(Model model) {
        if (model.getAttribute(ERROR_ATTRIBUTE) != null) { // dealing with errors
            System.out.println(model.getAttribute(ERROR_ATTRIBUTE)); //Todo: add error messages to be passed via model
        }
        return "auth/signUp";
    }

    @PostMapping("/sign_up")
    public String postSignUp(@RequestParam(name = "email") String email,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "secondPassword") String secondPassword,
                             @RequestParam(name = "role") Role role,
                             Model model) {
        // validations and creating a new user
        ArrayList<String> errors =
                userValidator.validate(email, password, secondPassword, role);

        if (errors == null) {
            User user = new User(email, password);
            userService.addUser(user, role);
        } else {
            model.addAttribute(ERROR_ATTRIBUTE, errors);
            return "auth/signUp";
        }

        return "index";
    }


}
