package com.xworkz.userapp.controller;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@RequestMapping("/")
@Controller
@Slf4j
public class SignInController {
    // process the Request & Navigate the Request

    @Autowired
    UserService userService;

    public SignInController() {
//        System.out.println("SignInController object is created");
        log.info("SignInController object is created");
    }

//    @PostMapping("signIn")
//    public String signIn(@RequestParam String email, @RequestParam("pwd") String password, Model model) {
//        try {
//            UserDto user = userService.getPasswordByEmail(email, password);
//            model.addAttribute("user", user);
//            System.out.println("Controller got User name coming from service "+ user);
//            return "welcome.jsp";
//        } catch (RuntimeException e) {
//            model.addAttribute("error", "Invalid user/password");
//            return "response.jsp";
//        }
//    }

    /*@PostMapping("signIn")
    public String signIn(@RequestParam String email, @RequestParam("pwd") String password, Model model) {
//        System.out.println("Controller signIn started");
        log.info("Controller signIn started");
        String returnedMessage = userService.validateAndLogIn(email, password);
        System.out.println("The returned message is: " + returnedMessage);

        if (returnedMessage.equals("isPresent")) {
            UserDto userDto = userService.getUserByEmail(email);
            if (userDto == null) {
                model.addAttribute ("error", "User not found for email: " + email);
                log.warn("User not found for email: {}", email);
                return "signIn.jsp"; // Redirecting  to sign-in page with error
            }
            // Add user details to the model
            model.addAttribute("user", userDto);
            log.info("User signed in successfully: {}", email);
            return "welcome.jsp"; // Redirecting to welcome page
        } else if (returnedMessage.equals("forward")) {
            model.addAttribute("email", email);
            log.info("User needs to reset password: {}", email);
            return "resetPassword.jsp";
        } else {
            model.addAttribute("Message", returnedMessage);
            log.warn("Sign-in failed: {}", returnedMessage);
            return "signIn.jsp";
        }

    }
    @RequestMapping("getProfileByEmail")
    public String getProfileByEmail(@RequestParam("email") String email, Model model){
//        System.out.println("SignInController getProfileByEmail started");
        log.info("SignInController getProfileByEmail started");
        UserDto dto = userService.getUserByEmail(email);

        if (dto == null) {
            model.addAttribute("error", "User not found for email: " + email);
            return "updateProfile.jsp";
        }
//        System.out.println("Data Controller got from service: "+dto);
        log.info("Data Controller got from service: "+dto);
        model.addAttribute("profile",dto);

//        System.out.println("controller getProfile ended returning updateProfile.jsp");
        log.info("controller getProfile ended returning updateProfile.jsp");
        return "updateProfile.jsp";
    }*/

    @PostMapping("signIn")
    public String signIn(@RequestParam String email, @RequestParam("pwd") String password, Model model, HttpSession session) {
        log.info("Controller signIn started");
        String returnedMessage = userService.validateAndLogIn(email, password);
        log.info("The returned message is: {}", returnedMessage);

        if (returnedMessage.equals("isPresent")) {
            UserDto userDto = userService.getUserByEmail(email);
            if (userDto == null) {
                model.addAttribute("error", "User not found for email: " + email);
                log.warn("User not found for email: {}", email);
                return "signIn"; // Redirect to sign-in page with error
            }
            // Store user in session
            session.setAttribute("loggedInUser", userDto);
            log.info("User signed in successfully: {}", email);
            return "welcome"; // Redirect to welcome page
        } else if (returnedMessage.equals("forward")) {
            model.addAttribute("email", email);
            log.info("User needs to reset password: {}", email);
            return "resetPassword";
        } else {
            model.addAttribute("Message", returnedMessage);
            log.warn("Sign-in failed: {}", returnedMessage);
            return "signIn";
        }
    }

    @RequestMapping("getProfile")
    public String getProfile(Model model, HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("error", "No user is logged in. Please sign in.");
            return "signIn"; // Redirect to sign-in page
        }

        model.addAttribute("profile", loggedInUser);
        log.info("Returning profile data for user: {}", loggedInUser.getEmail());
        return "updateProfile"; // Redirect to profile update page
    }
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Remove session data
        log.info("User logged out successfully");
        return "logout"; // Redirect to sign-in page
    }



}
