package com.jaab.revature.dto;

import com.jaab.revature.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginDTO implements Serializable {
    private Integer employeeId;
    private String email;
    private String password;
    private Role role;
}
