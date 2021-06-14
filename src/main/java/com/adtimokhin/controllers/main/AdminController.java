package com.adtimokhin.controllers.main;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.company.CompanyService;
import com.adtimokhin.services.report.ReportService;
import com.adtimokhin.services.user.UserService;
import com.adtimokhin.utils.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ContextProvider contextProvider;

    @Autowired
    private UserValidator userValidator;

    final private static Logger logger = Logger.getLogger("admin");

    @GetMapping("/home")
    public String test() {
        return "admin/homePage";
    }

    @GetMapping("get/blockedUsers")
    public String getBlockedUsers(Model model) {
        model.addAttribute("users", userService.getAllBannedUsers());
        return "admin/bannedUsersPage";
    }

    @GetMapping("get/reports")
    public String getReports(Model model) {
        model.addAttribute("reports", reportService.getAllReports());
        return "admin/reports";
    }

    @GetMapping("get/report/{id}")
    public String getReport(Model model, @PathVariable(name = "id") String stringId) throws NoHandlerFoundException {
        int reportId = 0;
        try {
            reportId = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new NoHandlerFoundException("GET", "/admin/get/report/" + stringId, new HttpHeaders());
        }

        Report report = reportService.getReportById(reportId);
        if (report == null) {
            throw new NoHandlerFoundException("GET", "/admin/get/report/" + reportId, new HttpHeaders());
        }

        Comment c = report.getComment();
        if (c == null) {
            Topic topic = report.getTopic();
            if(topic == null){
                model.addAttribute("answer" , report.getAnswer());
            }else {
                model.addAttribute("topic", report.getTopic());
            }
        } else {
            model.addAttribute("comment", c);
        }

        model.addAttribute("reportedUser", report.getReportedUser());
        model.addAttribute("reportingUser", report.getReportingUser());
        model.addAttribute("cause", report.getCause());
        model.addAttribute("reportId", report.getId());

        return "admin/fullReport";
    }

    @PostMapping("/update/block/user")
    public String banUser(@RequestParam(name = "reason") String reason,
                          @RequestParam(name = "reportId") long reportId) {
        reportService.banUser(reportService.getReportById(reportId), reason, contextProvider.getUser()); //todo: we don't validate if mistakes have occurred while method was running. We need to check that. Not only for this mapping, but for ALL  mappings.
        return "admin/successPage";

    }

    @PostMapping("/update/unblock/user")
    public String unBanUser(@RequestParam(name = "userId") long id) {
        reportService.unBanUser(userService.getUser(id), contextProvider.getUser());
        return "admin/successPage";
    }

    @PostMapping("/update/dismiss/report")
    public String dismissReport(@RequestParam(name = "reason") String reason,
                                @RequestParam(name = "reportId") long reportId){
        reportService.dismissReport(reportId , reason , contextProvider.getUser());
        return "admin/successPage";
    }

    @GetMapping("get/companies")
    public String getCompanies(Model model) {
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        model.addAttribute("verifiable", false);

        return "admin/companies";
    }

    @GetMapping("get/companies/pending")
    public String getCompaniesPending(Model model) {
        List<Company> companies = companyService.getAllPendingCompanies();
        model.addAttribute("companies", companies);
        model.addAttribute("verifiable", true);

        return "admin/companies";

    }

    @PostMapping("verify/company")
    public String verifyCompany(@RequestParam(name = "id") long id) {
        companyService.verifyCompany(contextProvider.getUser(), id);
        return "admin/successPage";
    }

    @GetMapping("create/admin")
    public String createAdmin() {
        return "admin/createAdmin";
    }

    @PostMapping("create/admin")
    public String postCreateAdmin(@RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam("secondPassword") String secondPassword, Model model) {
        User user = userService.getUser(email);
        if (user == null) {
            ArrayList<String> errors = userValidator.validate(email, password, secondPassword, Role.ROLE_ADMIN);
            if (errors == null) {
                user = new User(email, password);
                userService.addUser(user, true, Role.ROLE_ADMIN);
            } else {
                model.addAttribute("errors", errors);
                return "admin/createAdmin";
            }
        } else {
            userService.setRole(user, Role.ROLE_ADMIN);
        }
        return "admin/successPage";
    }
}
