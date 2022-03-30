package com.jaab.revature.controller;

import com.jaab.revature.dto.UserDTO;
import com.jaab.revature.model.User;
import com.jaab.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    private final UserService userService;

    @Autowired
    public ServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/employeeEmail/{employeeId}")
    public String getEmployeeEmail(@PathVariable Integer employeeId) {
        User employee = userService.getUserById(employeeId);
        return employee.getEmail();
    }

    @GetMapping("/adminEmail/{employeeId}")
    public String getAdminEmail(@PathVariable Integer employeeId) {
        UserDTO admin = userService.getAdminByEmployeeId(employeeId);
        return admin.getEmail();
    }
}
