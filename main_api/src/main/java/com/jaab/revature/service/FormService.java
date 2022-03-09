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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FormService{

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

    public void loadForm(Form obj, Integer id){
        User user = userService.getUserById(id);
        obj.setEmployee(user);
        BeanUtils.copyProperties(user, obj);
    }
    public Integer createForm(Form obj, Integer id){
        loadForm(obj, id);
        obj.setRequestDate(new Timestamp(System.currentTimeMillis()));
        obj.setRequestStatus(Status.PENDING);
        formRepository.save(obj);
        return obj.getFormID();
    }

    public FormDTO getFormByID (Integer id){
        Form getForm = formRepository.getFormByFormID(id);
        return copyFormToDTO(getForm);
    }

    public Set<FormDTO> getFormsByEmployeeID (Integer employeeId) {
        Set<Form> forms = formRepository.getFormsByEmployeeID(employeeId);

        return forms.stream()
                .map(this::copyFormToDTO)
                .collect(Collectors.toSet());
    }

    public Set<FormDTO> getAllForms(){
        List<Form> forms = formRepository.findAll(Sort.by("formID"));

        return forms.stream()
                .map(this::copyFormToDTO)
                .collect(Collectors.toSet());
    }

    public FormDTO updateStatus(Integer id, Status status){
        Form form = formRepository.getFormByFormID(id);
        FormDTO formDTO = copyFormToDTO(form);
        formRepository.updateStatus(id, status);
        formDTO.setRequestStatus(status);
        return formDTO;
    }

    private FormDTO copyFormToDTO(Form form) {
        FormDTO formDTO = new FormDTO();
        formDTO.setFormID(form.getFormID());
        formDTO.setEmployeeID(form.getEmployee().getEmployee_id());
        BeanUtils.copyProperties(form, formDTO);
        return formDTO;
    }

}
