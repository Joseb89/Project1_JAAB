package com.jaab.revature.dao;

import com.jaab.revature.model.Form;
import com.jaab.revature.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface FormRepository extends JpaRepository<Form, Integer> {

    @Query("From Form f where f.formID = :formId")
    Form getFormByFormID(@Param("formId") Integer formId);

    @Query("FROM Form f where f.employee.employee_id = :employeeId")
    Set<Form> getFormsByEmployeeID(@Param("employeeId") Integer employeeId);

    @Modifying
    @Query("update Form f set f.requestStatus = :status where f.formID = :id ")
    void updateStatus(@Param("id") Integer id, @Param("status")Status status);

}
