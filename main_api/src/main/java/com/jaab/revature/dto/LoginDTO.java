package com.jaab.revature.dto;

import com.jaab.revature.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private Integer employeeId;
    private String email;
    private String password;
    private Role role;
}
