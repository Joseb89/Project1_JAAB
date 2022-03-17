package com.jaab.revature.dao;

import com.jaab.revature.model.Form;
import com.jaab.revature.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FormRepository extends JpaRepository<Form, Integer> {

    @Modifying
    @Query("update Form f set f.requestStatus = :status where f.formId = :id ")
    void updateStatus(@Param("id") Integer id, @Param("status") Status status);
}