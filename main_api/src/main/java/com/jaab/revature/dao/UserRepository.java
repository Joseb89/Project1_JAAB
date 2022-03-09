package com.jaab.revature.dao;

import com.jaab.revature.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("From User u where u.employee_id = :id")
    User getUserByEmployee_id(@Param("id") Integer id);

    @Query("From User u where u.email = :email")
    User getUserByEmail(@Param("email") String email);
}
