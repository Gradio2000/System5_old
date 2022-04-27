package com.example.system5.validation;


import com.example.system5.model.MyForm;
import com.example.system5.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MyFormValidator implements Validator {
    private final UserRepository userRepository;

    public MyFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MyForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MyForm myForm = (MyForm) target;
        String login = myForm.getLogin();
        String password = myForm.getPassword();
        String conpass = myForm.getConfpass();
        if (!password.equals(conpass)){
            errors.rejectValue("confpass", "", "Пароли не совпадают");
        }
        if (userRepository.existsUserByLogin(login)){
            errors.rejectValue("login", "", "Пользователь с таким логином зарегистрирован ранее");
        }

    }
}
