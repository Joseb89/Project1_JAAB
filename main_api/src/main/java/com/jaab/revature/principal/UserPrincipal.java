package com.jaab.revature.principal;

import com.jaab.revature.dto.LoginDTO;
import com.jaab.revature.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserPrincipal implements UserDetails {

    private final LoginDTO loginDTO;

    public UserPrincipal(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

        return authorities;
    }

    @Override
    public String getPassword() {
        return loginDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return loginDTO.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getUserId() {
        return loginDTO.getEmployeeId();
    }

    public Role getRole() {
        return loginDTO.getRole();
    }
}
