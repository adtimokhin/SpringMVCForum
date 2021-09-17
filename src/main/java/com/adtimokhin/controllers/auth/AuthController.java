package com.adtimokhin.controllers.auth;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.user.User;
import com.adtimokhin.services.user.UserService;
import com.adtimokhin.utils.validator.PasswordValidator;
import com.adtimokhin.utils.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private PasswordValidator passwordValidator;

    private static final String ERROR_ATTRIBUTE = "errors";

    @GetMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) Boolean error,
                           HttpServletRequest request, Model model) {
        if (Boolean.TRUE.equals(error)) {
            try {
                AuthenticationException authenticationException =
                        (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                if (authenticationException.getClass().equals(LockedException.class)) {
                    if (authenticationException.getMessage().equals("email")) {
                        model.addAttribute("error", "Unverified email");
                    } else {
                        model.addAttribute("error", "User banned");
                    }
                } else {
                    model.addAttribute("error", "Bad credentials");
                }
            } catch (Exception e) {
                model.addAttribute("error", "Error occurred");
            }

        }
        return "auth/signIn";
    }

    @GetMapping("/sign_up")
    public String getSignUp() {
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
            userService.addUser(user, true, role);
        } else {
            model.addAttribute(ERROR_ATTRIBUTE, errors);
            if (email != null) {
                model.addAttribute("email", email);
            }
            return "auth/signUp";
        }

        return "index";
    }

    @GetMapping("/verify/{token}")
    public String verifyEmail(@PathVariable(value = "token") String token) {
        if (!userService.verifyEmail(token)) {
            return "failVerification";
        }
        return "successVerification";
    }

    // restoring password for all users

    @GetMapping("/restore_password")
    public String restorePassword() {
        return "auth/forgotPassword";
    }

    @PostMapping("/restore_password")
    public String postRestorePassword(@RequestParam(name = "email") String email, Model model) {

        User user = userService.getUser(email);
        if (user == null) {
            model.addAttribute("error", "User with this email does not exist");
            return "auth/forgotPassword";
        } else {
            if(user.getEmailVerificationToken() != null){
                model.addAttribute("error" , "This email is still not verified");
                return "auth/forgotPassword";
            }

            userService.setPasswordRestoringToken(user);
        }
        return "auth/PasswordResetEmailWasSent";

    }

    @GetMapping("/restore_password/{token}")
    public String restorePasswordWithToken(@PathVariable(value = "token") String token, Model model) {
        model.addAttribute("token", token);
        return "auth/restorePasswordPage";
    }

    @PostMapping("/restore_password/{token}")
    public String restorePasswordWithTokenPost(@PathVariable(value = "token") String token,
                                               @RequestParam(name = "passwordOne") String passwordOne,
                                               @RequestParam(name = "passwordTwo") String passwordTwo,
                                               Model model) {
        ArrayList<String> errors = new ArrayList<>();
        if (userService.passwordResetTokenExists(token)) {
            errors = passwordValidator.validate(passwordOne, passwordTwo);
            if(errors != null){
                model.addAttribute("errors" , errors);
                model.addAttribute("token" , token);
                return "auth/restorePasswordPage";
            }else {
                userService.setNewPasswordForUserWithToken(token, passwordOne);
                return "auth/successfullyChangedPassword";
            }
        }
        errors.add("Token is no longer invalid");
        model.addAttribute("errors" , errors);
        model.addAttribute("token" , token);
        return "auth/restorePasswordPage";
    }

}
