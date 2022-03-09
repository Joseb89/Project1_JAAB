package com.jaab.revature.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "forms", schema = "reimburse")
public class Form {

    @Id
    @Column(name = "form_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formID;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    @Column(name = "supervisor")
    private String supervisor;

    @Column(name = "request_date", nullable = false)
    private Date requestDate;

    @Column(name = "request_topic", nullable = false)
    private String requestTopic;

    @Column(name = "request_reason")
    private String requestReason;

    @Column(name = "request_cost", nullable = false)
    private double cost;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    private Status requestStatus;

}
