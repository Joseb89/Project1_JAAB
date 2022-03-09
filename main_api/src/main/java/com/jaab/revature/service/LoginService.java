package com.jaab.revature.service;

import com.jaab.revature.dao.UserRepository;
import com.jaab.revature.dto.UserDTO;
import com.jaab.revature.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUsername(String email){
        UserDTO userDTO = new UserDTO();
        User user = userRepository.getUserByEmail(email);
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
