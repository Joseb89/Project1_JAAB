package com.jaab.revature.controller;

import com.jaab.revature.model.Role;
import com.jaab.revature.model.User;
import com.jaab.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/newUser")
    public String loadNewUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping("/newUser")
    public String createNewUser(@ModelAttribute User user, Role role){
        userService.createUser(user, role);
        return "user_success";
    }
}
