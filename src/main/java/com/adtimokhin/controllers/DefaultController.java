package com.adtimokhin.controllers;

import com.adtimokhin.enums.Role;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author adtimokhin
 * 24.04.2021
 **/

@Controller
public class DefaultController {


    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        userService.banUser(userService.getUser(14));
        return "index";
    }

    /**
     * method used to direct a recently signed in user to his own home page, depending on a role the user has.
     **/
    @GetMapping("/defaultSuccessUrl")
    public String defaultSuccessUrl(HttpServletRequest request) {
        if (request.isUserInRole(Role.ROLE_STUDENT.getAuthority())) {
            return "redirect:/student/topics";
        } else if (request.isUserInRole(Role.ROLE_ADMIN.getAuthority())) {
            return "redirect:/admin/home";
        }
        return "redirect:/parent/topics";

    }

}
