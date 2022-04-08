package com.jaab.revature.service;

import com.jaab.revature.dao.UserRepository;
import com.jaab.revature.dto.LoginDTO;
import com.jaab.revature.dto.UserDTO;
import com.jaab.revature.model.Role;
import com.jaab.revature.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class serves as a service for adding users to the database and retrieving their data based on specific
 * criteria
 * @author Joseph Barr
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Saves a new user to the database
     * @param user - the user to be created
     * @param role - the user's assigned role
     */
    public void createUser(User user, Role role){
        String encoded = encoder.encode(user.getPassword());
        user.setPassword(encoded);
        user.setRole(role);
        userRepository.save(user);
    }

    /**
     * Retrieves a user based on their primary key id
     * @param id - the user's id
     * @return - queried user
     */
    public User getUserById(Integer id){
        return userRepository.getById(id);
    }

    /**
     * Retrieves a set of employees who have the selected supervisor
     * @param supervisor - the name of the supervisor
     * @return - employees with queried supervisor
     */
    public Set<UserDTO> getUsersBySupervisor(String supervisor) {
        Set<UserDTO> employees = getAllEmployees();

        return employees.stream()
                .filter(e -> Objects.equals(e.getSupervisor(), supervisor))
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves a set of users who have the Admin role
     * @return - Admin users
     */
    public Set<UserDTO> getAdminUsers() {
        return getUsersByRole(Role.ADMIN);
    }

    /**
     * Retrieves the supervisor of an employee based on their primary key id
     * @param id - the employee's id
     * @return - the employee's supervisor
     */
    public UserDTO getAdminByEmployeeId(Integer id) {
        User user = getUserById(id);
        String supervisor = user.getSupervisor();

        String[] name = supervisor.split(" ");

        User admin = userRepository.getUserByFirstNameAndLastName(name[0], name[1]);

        return copyToDTO(admin);
    }

    /**
     * Retrieves a user based on their email
     * @param email - the user's email
     * @return - the queried user
     */
    public LoginDTO getUserByUsername(String email) {
        LoginDTO loginDTO = new LoginDTO();
        User user = userRepository.getUserByEmail(email);
        BeanUtils.copyProperties(user, loginDTO);
        return loginDTO;
    }

    /**
     * Retrieves all users who have the Employee role
     * @return - Employee users
     */
    private Set<UserDTO> getAllEmployees() {
        return getUsersByRole(Role.EMPLOYEE);
    }

    /**
     * Retrieves a set of users based on their role
     * @param role - the selected role
     * @return - users with queried role
     */
    private Set<UserDTO> getUsersByRole(Role role) {
        List<User> users = userRepository.findAll(Sort.by("lastName"));

        return users.stream()
                .filter(user -> Objects.equals(user.getRole(), role))
                .map(this::copyToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Copies user data into a UserDTO object
     * @param user - the user to be copied
     * @return - new UserDTO
     */
    private UserDTO copyToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
