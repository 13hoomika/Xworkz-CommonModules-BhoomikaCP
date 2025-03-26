package com.xworkz.userapp.restcontroller;

import com.xworkz.userapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class RestController {
    public RestController() {
        log.info("RestController object created");
    }
    @Autowired
    UserService userService;

    @GetMapping(value = "/checkValue/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNameCount(@PathVariable String name){
        System.out.println("This is restController name: "+name);
        long count = userService.getNameCount(name);
        if(count > 0){
            return "Name already exists";
        }
        return "";
    }

    @GetMapping(value = "/checkEmailValue/{email:.+}")
    public String getEmailCount(@PathVariable String email) {
        System.out.println("Received email in Controller: [" + email + "]");

        boolean isEmailExist = userService.existByEmail(email);
        System.out.println("isEmailExist: " + isEmailExist);

        return isEmailExist ? "email already exists" : "";
    }

    
    @GetMapping(value = "/checkPhValue/{phNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPhCount(@PathVariable String phNo){
        System.out.println("Received phNo in Controller: [" + phNo + "]");
        boolean isPhExist = userService.existByPhNumber(phNo);
        System.out.println("isPhExist: "+isPhExist);
        if(isPhExist){
            return "phone number already exists";
        }
        return "";
    }
}
