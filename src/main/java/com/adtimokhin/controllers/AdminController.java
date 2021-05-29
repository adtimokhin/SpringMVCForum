package com.adtimokhin.controllers;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.report.ReportService;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private ContextProvider contextProvider;

    @GetMapping("/home")
    public String test() {
        return "admin/homePage";
    }

    @GetMapping("get/blockedUsers")
    public String getBlockedUsers(Model model){
        model.addAttribute("users" , userService.getAllBannedUsers());
        return "admin/bannedUsersPage";
    }

    @GetMapping("get/reports")
    public String getReports(Model model) {
        model.addAttribute("reports", reportService.getAll()); //Todo: не отображаются корректно репорты, потому что, мы всегда считаем, что все репорты ссылаются на комментарий, а не на топик. Нужно фиксить, дружище.
        return "admin/reports";
    }

    @GetMapping("get/report/{id}")
    public String getReport(Model model, @PathVariable(name = "id") long reportId) {
        Report report = reportService.getById(reportId);
        if (report == null) {
            // Todo: throw some exception
            return "redirect:admin/home";
        }

        Comment c = report.getComment();
        if (c == null) {
            model.addAttribute("topic", report.getTopic());
        } else {
            model.addAttribute("comment", c);
        }

        model.addAttribute("reportedUser", report.getReportedUser());
        model.addAttribute("reportingUser", report.getReportingUser());
        model.addAttribute("cause", report.getCause());
        model.addAttribute("reportId" , report.getId());

        return "admin/fullReport";
    }

    @PostMapping("/update/block/user")
    public String banUser(@RequestParam(name = "reason") String reason,
                          @RequestParam(name = "reportId") long reportId){
        reportService.banUser(reportService.getById(reportId) , reason ,contextProvider.getUser());
        return "redirect:/admin/home";

    }

    @PostMapping("/update/unblock/user")
    public String unBanUser(@RequestParam(name = "userId") long id){
        reportService.unBanUser(userService.getUser(id) , contextProvider.getUser());
        return "redirect:/admin/home";
    }
}
