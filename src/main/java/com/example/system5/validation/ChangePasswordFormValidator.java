package com.example.system5.validation;

import com.example.system5.model.ChangePasswordForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChangePasswordFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePasswordForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangePasswordForm changePasswordForm = (ChangePasswordForm) target;
        String password = changePasswordForm.getPassword();
        String confirmPassword = changePasswordForm.getConfirmPassword();
        if (!password.equals(confirmPassword)){
            errors.rejectValue("confirmPassword", "", "Пароли не совпадают");
        }
    }
}
