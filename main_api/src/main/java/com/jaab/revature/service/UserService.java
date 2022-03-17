package com.jaab.revature.service;

import com.jaab.revature.dao.UserRepository;
import com.jaab.revature.model.Role;
import com.jaab.revature.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user, Role role){
        user.setRole(role);
        userRepository.save(user);
    }

    public User getUserById(Integer id){
        return userRepository.getById(id);
    }
}
