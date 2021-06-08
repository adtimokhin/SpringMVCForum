package com.adtimokhin.controllers;

import com.adtimokhin.services.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Controller
@RequestMapping("company/auth")
public class CompanyAuthController {

    @Autowired
    private CompanyService companyService;


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
                               @RequestParam(name = "token") int token) {
        // FOR NOW WE ASSUME THAT ALL FIELDS ENTERED ARE VALID
        companyService.addCompany(companyName, url, email, phone, location, token);
        return "company/register";
    }

}
