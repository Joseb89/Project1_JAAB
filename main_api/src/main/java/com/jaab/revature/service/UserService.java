package com.jaab.revature.service;

import com.jaab.revature.dao.UserRepository;
import com.jaab.revature.dto.UserDTO;
import com.jaab.revature.model.Role;
import com.jaab.revature.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public Set<UserDTO> getUsersBySupervisor(String supervisor) {
        Set<UserDTO> employees = getAllEmployees();

        return employees.stream()
                .filter(e -> Objects.equals(e.getSupervisor(), supervisor))
                .collect(Collectors.toSet());
    }

    public Set<UserDTO> getAdminUsers() {
        return getUsersByRole(Role.ROLE_ADMIN);
    }

    public UserDTO getAdminByEmployeeId(Integer id) {
        User user = getUserById(id);
        String supervisor = user.getSupervisor();

        String[] name = supervisor.split(" ");

        User admin = userRepository.getUserByFirstNameAndLastName(name[0], name[1]);

        return copyToDTO(admin);
    }

    private Set<UserDTO> getAllEmployees() {
        return getUsersByRole(Role.ROLE_EMPLOYEE);
    }

    private Set<UserDTO> getUsersByRole(Role role) {
        List<User> users = userRepository.findAll(Sort.by("lastName"));

        return users.stream()
                .filter(user -> Objects.equals(user.getRole(), role))
                .map(this::copyToDTO)
                .collect(Collectors.toSet());
    }

    private UserDTO copyToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
