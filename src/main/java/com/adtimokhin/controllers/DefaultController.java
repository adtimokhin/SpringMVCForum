package com.adtimokhin.controllers;

import com.adtimokhin.models.Roles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author adtimokhin
 * 24.04.2021
 **/

@Controller
public class DefaultController {

    /**
     * method used to direct a recently signed in user to his own home page, depending on a role he/she has.
     **/

    @GetMapping("/defaultSuccessUrl")
    public String defaultSuccessUrl(HttpServletRequest request) {
        if (request.isUserInRole(Roles.ROLE_STUDENT.getAuthority())) {
            return "redirect:/student/topics";
        } else if (request.isUserInRole(Roles.ROLE_ADMIN.getAuthority())) {
            return "redirect:/admin/home";
        }
        return "redirect:/parent/topics";

    }

}
