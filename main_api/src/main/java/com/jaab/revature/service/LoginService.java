package com.jaab.revature.service;

import com.jaab.revature.dto.LoginDTO;
import com.jaab.revature.principal.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class serves as a service for retrieving user credentials for authentication
 * @author Joseph Barr
 */
@Service
public class LoginService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public LoginService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginDTO loginDTO = userService.getUserByUsername(username);

        if (loginDTO == null)
            throw new UsernameNotFoundException("User not found");

        return new UserPrincipal(loginDTO);
    }
}
