package com.xworkz.userapp.controller;

import com.xworkz.userapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@Slf4j
public class DeleteController {

    @Autowired
    private UserService userService;

//    @PostMapping("deleteProfile")
//    public String deleteUserProfileById(@RequestParam("userId") String userId, RedirectAttributes redirectAttributes) {
//        try {
//            userService.deleteById(Integer.parseInt(userId));
//            redirectAttributes.addFlashAttribute("message", "Your account has been deleted successfully.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "An error occurred while deleting your account. Please try again.");
//            System.err.println("Error deleting user with ID " + userId + ": " + e.getMessage());
//        }
//        return "redirect:/logout.jsp";
//    }

    @RequestMapping("deleteProfile")
    public String deleteProfile(@RequestParam String email, Model model, HttpSession session) {
        // System.out.println("Delete profile request received for email: " + email);
        log.info("Delete profile request received for email: {}", email);

        // Call the service to delete the profile
        boolean isDeleted = userService.deleteUserByEmail(email);

        if (isDeleted) {
            log.info("Profile deleted successfully for email: {}", email);
            // Remove the user from the session
            session.invalidate(); // Clears the session
            model.addAttribute("message", "Profile deleted successfully!");
        } else {
            log.warn("Failed to delete profile. Email not found: {}", email);
            model.addAttribute("error", "Failed to delete profile. Email not found.");
        }

        return "deleteProfile"; // Return to the delete profile page
    }

}
