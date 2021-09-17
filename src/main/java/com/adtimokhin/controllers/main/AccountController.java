package com.adtimokhin.controllers.main;

import com.adtimokhin.models.user.User;
import com.adtimokhin.security.SecurityContextProvider;
import com.adtimokhin.services.user.UserFullNameService;
import com.adtimokhin.services.user.UserService;
import com.adtimokhin.utils.validator.PasswordValidator;
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
 * 05.07.2021
 **/

/**
 * This controller handles requests related to the user details
 * e.g. changing name, upgrading rating, etc
 **/
@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private SecurityContextProvider contextProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFullNameService userFullNameService;

    @Autowired
    private PasswordValidator passwordValidator;

    @GetMapping("/")
    public String getAccount(Model model) {
        model.addAttribute("user", contextProvider.getUser());
        return "account/account";
    }

    @GetMapping("/generate_random_name")
    public String getRandomNamePage(Model model) {
        model.addAttribute("userName", userService.getRandomUserName());
        model.addAttribute("userSurname", userService.getRandomUserSurname());

        return "account/randomNamePage";
    }

    @PostMapping("/save_new_name")
    public String saveRandomName(@RequestParam(name = "userName") long userNameId,
                                 @RequestParam(name = "userSurname") long userSurnameId) {

        userService.safeNewName(contextProvider.getUser(), userFullNameService.getUserName(userNameId), userFullNameService.getUserSurname(userSurnameId));
        return "redirect:/account/";
    }

    @GetMapping("/change_password")
    public String getChangePasswordPage(Model model) {
        model.addAttribute("email", contextProvider.getUser().getEmail());
        return "/account/changePasswordPage";
    }

    @PostMapping("/change_password")
    public String changePassword(@RequestParam(name = "oldPassword") String oldPassword,
                                 @RequestParam(name = "newPassword") String newPassword,
                                 @RequestParam(name = "newPasswordRepeated") String newPasswordRepeated,
                                 Model model) {
        User user = contextProvider.getUser();
        ArrayList<String> errors = passwordValidator.validate(user, oldPassword, newPassword, newPasswordRepeated);
        if (errors != null) {
            model.addAttribute("error", errors);
            return "/account/changePasswordPage";
        } else {
            userService.changePassword(user, newPassword);
            return "redirect:/account/"; // todo: direct to a page that says that password was successfully updated.
        }
    }

    //Todo: add ' forgot password   ? ' functionality.
}
