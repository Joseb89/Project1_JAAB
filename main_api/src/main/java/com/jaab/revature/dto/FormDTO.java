package com.jaab.revature.dto;

import com.jaab.revature.model.Department;
import com.jaab.revature.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FormDTO {

    private int formID;
    private int employeeID;
    private String firstName;
    private String lastName;
    private Department department;
    private String supervisor;
    private Date requestDate;
    private String requestTopic;
    private String requestReason;
    private double cost;
    private Status requestStatus;

}
