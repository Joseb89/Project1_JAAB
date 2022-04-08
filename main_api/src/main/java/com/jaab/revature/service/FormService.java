package com.jaab.revature.service;

import com.jaab.revature.dao.FormRepository;
import com.jaab.revature.dto.FormDTO;
import com.jaab.revature.model.Form;
import com.jaab.revature.model.Role;
import com.jaab.revature.model.Status;
import com.jaab.revature.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is a service for creating and retrieving form data submitted by employees
 * @author Joseph Barr
 */
@Service
public class FormService {

    private FormRepository formRepository;
    private UserService userService;

    @Autowired
    public void setFormRepository(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Prefills a form with user data
     * @param form - the loaded form
     * @param id - the id of the user
     */
    public void loadForm(Form form, Integer id) {
        User user = userService.getUserById(id);

        if (user.getRole() == Role.ADMIN)
            throw new RuntimeException("Admin users cannot submit forms.");

        form.setEmployee(user);
        BeanUtils.copyProperties(user, form);
    }

    /**
     * Saves a new form to the database
     * @param form - the form to be saved
     * @param id - the primary key id of the user
     * @return - the primary key id of the new form
     */
    public Integer createForm(Form form, Integer id){
        loadForm(form, id);
        form.setRequestDate(new Timestamp(System.currentTimeMillis()));
        form.setRequestStatus(Status.PENDING);
        formRepository.save(form);
        return form.getFormId();
    }

    /**
     * Retrieves a form based on the primary key id
     * @param id - the id of the form
     * @return - queried form
     */
    public FormDTO getFormById(Integer id){
        Form form = formRepository.getById(id);
        return copyToDTO(form);
    }

    /**
     * Retrieves a set of forms based on the employee id
     * @param employeeId - the employee id of the user
     * @return - queried forms
     */
    public Set<FormDTO> getFormsByEmployeeId(Integer employeeId) {
        Set<Form> forms = formRepository.getAllUnapprovedRequests(employeeId);

        return forms.stream()
                .map(this::copyToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Updates the selected form to either APPROVED or DENIED
     * @param id - the primary key of the form
     * @param status - the new status of the form
     */
    public void updateStatus(Integer id, Status status){
        formRepository.updateStatus(id, status);
    }

    /**
     * Copies form data into a new FormDTO object
     * @param form - the form to be copied
     * @return - FormDTO
     */
    private FormDTO copyToDTO(Form form) {
        FormDTO formDTO = new FormDTO();
        formDTO.setEmployeeId(form.getEmployee().getEmployeeId());
        BeanUtils.copyProperties(form, formDTO);
        return formDTO;
    }
}
