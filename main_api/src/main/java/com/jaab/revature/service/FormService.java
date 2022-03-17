package com.jaab.revature.service;

import com.jaab.revature.dao.FormRepository;
import com.jaab.revature.dto.FormDTO;
import com.jaab.revature.model.Form;
import com.jaab.revature.model.Status;
import com.jaab.revature.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void loadForm(Form form, Integer id) {
        User user = userService.getUserById(id);
        form.setEmployee(user);
        BeanUtils.copyProperties(user, form);
    }

    public Integer createForm(Form form, Integer id){
        loadForm(form, id);
        form.setRequestDate(new Timestamp(System.currentTimeMillis()));
        form.setRequestStatus(Status.PENDING);
        formRepository.save(form);
        return form.getFormId();
    }

    public Set<FormDTO> getAllForms(){
        List<Form> forms = formRepository.findAll(Sort.by("formId"));

        return forms.stream()
                .map(this::copyToDTO)
                .collect(Collectors.toSet());
    }

    public FormDTO getFormById(Integer id){
        Form form = formRepository.getById(id);
        return copyToDTO(form);
    }

    public Set<FormDTO> getFormsByEmployeeId(Integer id) {
        List<Form> forms = formRepository.findAll(Sort.by("formId"));

        return forms.stream()
                .filter(form -> Objects.equals(form.getEmployee().getEmployeeId(), id))
                .map(this::copyToDTO)
                .collect(Collectors.toSet());
    }

    public void updateStatus(Integer id, Status status){
        Form form = formRepository.getById(id);
        FormDTO formDTO = copyToDTO(form);
        formRepository.updateStatus(formDTO.getFormId(), status);
    }

    private FormDTO copyToDTO(Form form) {
        FormDTO formDTO = new FormDTO();
        BeanUtils.copyProperties(form, formDTO);
        formDTO.setEmployeeId(form.getEmployee().getEmployeeId());
        return formDTO;
    }
}
