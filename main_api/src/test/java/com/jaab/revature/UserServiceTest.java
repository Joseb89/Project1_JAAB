package com.jaab.revature;

import com.jaab.revature.dto.UserDTO;
import com.jaab.revature.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getAdminEmailTest(){
        UserDTO admin = userService.getAdminByEmployeeId(4);
        Assertions.assertEquals("yoyigo6412@jooffy.com", admin.getEmail());
    }
}
