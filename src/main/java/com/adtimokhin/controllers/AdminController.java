package com.adtimokhin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("/home")
    public String test(){
        return "admin/homePage";
    }
}
