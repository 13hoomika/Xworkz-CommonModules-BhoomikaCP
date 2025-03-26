package com.xworkz.userapp.controller;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RequestMapping("/")
@Controller
public class UserController {
    // process the Request & Navigate the Request

    @Autowired
    UserService userService;

    public UserController() {
        System.out.println("UserController obj is created");
    }

    @RequestMapping("fetchUser")
    public String fetchUserById(@RequestParam("id") String id, Model model){
        UserDto dto = userService.getUserById(Integer.parseInt(id));
        model.addAttribute("fetch",dto);
        return "updateUser";
    }

    @RequestMapping("updateUser")
    public String updateUserById(UserDto dto, Model model){
        boolean userUpdated = userService.updateUser(dto);
        if (userUpdated)
        {
            model.addAttribute("updateMsg","User details updated successfully");
        }else model.addAttribute("updateMsg","User details update failed");
        return "updateUser";
    }

    @RequestMapping("allUsers")
    public String getAllUsers(Model model) throws InvocationTargetException, IllegalAccessException {
        List<UserDto> userDtoList = userService.getAllUsers();
            model.addAttribute("listOfUsers", userDtoList);
            return "getAllUsers";
    }

   // when data is coming from path "/" <a href="delete/${user.getId()}">
    @RequestMapping("delete/{idAnything}")
    public RedirectView deleteUserById(@PathVariable("idAnything") String id, HttpServletRequest req){ //userId --> @RequestParam("userId")
        userService.deleteById(Integer.parseInt(id));
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(req.getContextPath() + "/allUsers");
        return redirectView;
    }
}
