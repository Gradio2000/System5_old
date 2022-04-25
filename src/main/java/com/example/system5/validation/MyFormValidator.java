package com.example.system5.validation;


import com.example.system5.model.MyForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MyFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MyForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MyForm myForm = (MyForm) target;
        String password = myForm.getLogin();
        String conpass = myForm.getConfpass();
        if (!password.equals(conpass)){
            errors.rejectValue("confpass", "", "пароли не совпадают");
        }
    }
}
