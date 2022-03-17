package com.jaab.revature.dto;

import com.jaab.revature.model.Department;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Department department;
    private String supervisor;
}
