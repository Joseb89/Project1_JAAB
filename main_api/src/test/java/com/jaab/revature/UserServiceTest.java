package com.jaab.revature;

import com.jaab.revature.dto.LoginDTO;
import com.jaab.revature.dto.UserDTO;
import com.jaab.revature.model.User;
import com.jaab.revature.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class UserServiceTest {

    private User user;
    private UserDTO userDTO;
    private Set<UserDTO> users, admins;
    private LoginDTO login;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void init() {
        user = userService.getUserById(5);
        userDTO = userService.getAdminByEmployeeId(2);
        users = userService.getUsersBySupervisor("James Hawke");
        admins = userService.getAdminUsers();
        login = userService.getUserByUsername("kingofferelden@yahoo.com");
    }

    @Test
    public void getUserByIdTest_Success() {
        Assertions.assertEquals("Aveline", user.getFirstName());
    }

    @Test
    public void getUsersByIdTest_Fail() {
        Assertions.assertNotEquals("Tethras", user.getLastName());
    }

    @Test
    public void getUsersBySupervisorTest_NotEmpty() {
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    public void getUsersBySupervisorTest_Success() {
        for (UserDTO user : users)
            Assertions.assertEquals("James Hawke", user.getSupervisor());
    }

    @Test
    public void getUsersBySupervisorTest_Fail() {
        for (UserDTO user : users)
            Assertions.assertNotEquals("Dane Cousland", user.getSupervisor());
    }

    @Test
    public void getAdminUsersTest_NotEmpty() {
        Assertions.assertFalse(admins.isEmpty());
    }

    @Test
    public void getAdminUsersTest_Success() {
        Assertions.assertEquals(3, admins.size());
    }

    @Test
    public void getAdminByEmployeeIdTest_Success() {
        Assertions.assertEquals("Dane", userDTO.getFirstName());
    }

    @Test
    public void getAdminByEmployeeIdTest_Fail() {
        Assertions.assertNotEquals("Trevelyan",
                userDTO.getLastName());
    }

    @Test
    public void getUserByUserNameTest_Success() {
        Assertions.assertEquals(2, login.getEmployeeId());
    }

    @Test
    public void getUserByUserNameTest_Fail() {
        Assertions.assertNotEquals(8, login.getEmployeeId());
    }
}
