package com.adtimokhin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author adtimokhin
 * 13.04.2021
 **/
@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
