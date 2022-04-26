package com.example.system5.validation;

import com.example.system5.model.FormFinishReg;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FormFinishRegValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return FormFinishReg.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FormFinishReg formFinishReg = (FormFinishReg) target;
        if (formFinishReg.getPosition_id() == 0){
            errors.rejectValue("position_id", "", "выберете должность");
        }
    }
}
