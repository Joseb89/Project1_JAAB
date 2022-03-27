package com.jaab.revature.dao;

import com.jaab.revature.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("From User u where u.firstName = :firstName and u.lastName = :lastName")
    User getUserByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("From User u where u.email = :email")
    User getUserByEmail(@Param("email") String email);
}