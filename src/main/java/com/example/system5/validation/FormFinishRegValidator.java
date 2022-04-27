package com.example.system5.validation;

import com.example.system5.model.FormFinishReg;
import com.example.system5.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FormFinishRegValidator implements Validator {

    private final UserRepository userRepository;

    public FormFinishRegValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FormFinishReg.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FormFinishReg formFinishReg = (FormFinishReg) target;
        if (formFinishReg.getPosition_id() == 0){
            errors.rejectValue("position_id", "", "Выберите должность");
        }
        if (userRepository.existsUserByPosition_id(formFinishReg.getPosition_id())){
            errors.rejectValue("position_id", "", "Должность уже занята");
        }
    }
}
