package com.adtimokhin.controllers;

import com.adtimokhin.services.report.ReportService;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @Autowired
    private ReportService reportService;

    @GetMapping("/home")
    public String test(){
        return "admin/homePage";
    }

    @GetMapping("get/banned_users")
    public String getUsers(Model model){
        model.addAttribute("reports" , reportService.getAll());
        return "admin/reports";
    }
}
