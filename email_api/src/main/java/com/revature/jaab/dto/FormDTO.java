package com.revature.jaab.dto;


import com.revature.jaab.model.Department;
import com.revature.jaab.model.Status;

import java.util.Date;

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

    public FormDTO() {
    }

    public int getFormID() {
        return formID;
    }

    public void setFormID(int formID) {
        this.formID = formID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestTopic() {
        return requestTopic;
    }

    public void setRequestTopic(String requestTopic) {
        this.requestTopic = requestTopic;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Status getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Status requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "FormDTO{" +
                "formID=" + formID +
                ", employeeID=" + employeeID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department=" + department +
                ", supervisor='" + supervisor + '\'' +
                ", requestDate=" + requestDate +
                ", requestTopic='" + requestTopic + '\'' +
                ", requestReason='" + requestReason + '\'' +
                ", cost=" + cost +
                ", requestStatus=" + requestStatus +
                '}';
    }
}
