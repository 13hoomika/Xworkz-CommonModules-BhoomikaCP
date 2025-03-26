package com.xworkz.userapp.controller;

import com.xworkz.userapp.constants.LocationEnum;
import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@EnableWebMvc
@RequestMapping("/")
@Controller
@Slf4j
public class SignUpController {
    public SignUpController() {
        log.info("SignUpController obj is created");
    }

    @Autowired
    UserService userService;

    @RequestMapping("signup")
    public String addUser(@ModelAttribute UserDto dto, Model model){
        List<LocationEnum> locationList = new ArrayList<>(Arrays.asList(LocationEnum.values()));
        log.info("UserDto received: {}", dto); // Log the received DTO

//        if (bindingResult.hasErrors()) {
//            model.addAttribute("errorMsg", bindingResult.getAllErrors());
//            return "signUp"; // Stay on sign-up page if validation fails
//        }

//        System.out.println("uploaded image: " + dto.getProfileImg());
//        System.out.println("multipartFile : " + dto.getMultipartFile());

        Boolean isValid = userService.saveUserWithGeneratedPassword(dto, model);
        model.addAttribute("name", dto.getName());

        if (!isValid) {
            model.addAttribute("errorMsg","User details could not be saved");
            return "signUp";
        }else {
            model.addAttribute("saveMsg","User registered successfully! Please check your email for login details");
        }
        return "response"; // success
    }
}
