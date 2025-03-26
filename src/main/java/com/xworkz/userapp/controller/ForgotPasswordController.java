package com.xworkz.userapp.controller;

import com.xworkz.userapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@Slf4j
public class ForgotPasswordController {
    public ForgotPasswordController() {

        log.info("ForgotPasswordController object created");
    }

    @Autowired
    private UserService userService;

//    @GetMapping("forgotPassword")
//    public String showForgotPasswordPage() {
//        System.out.println("Forgot Password page requested");
//        return "resetPassword.jsp";
//    }

    @PostMapping("resetPassword")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword,
            Model model) {
        try {
            // Validate if new password and confirm password match
            if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("error", "New password and confirm password do not match.");
                return "resetPassword";
            }

            // Update the password in the database
            boolean isUpdated = userService.resetPassword(email, newPassword);
            if (isUpdated) {
                model.addAttribute("message", "Password reset successful! Please sign in again with your new password.");
            } else {
                model.addAttribute("error", "Failed to update password, Invalid Password Must be at least 8 characters long, include one uppercase letter, one lowercase letter, one digit, and one special character.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
        }
        return "resetPassword";
    }
}