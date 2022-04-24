package com.example.system5.controller;

import com.example.system5.model.MyForm;


import com.example.system5.model.Role;
import com.example.system5.model.User;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.Validation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public ModelAndView registration(){
        MyForm myForm = new MyForm();
        return new ModelAndView("registration", "myForm", myForm);
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute @Valid MyForm myForm, BindingResult bindingResult, Model model, HttpServletRequest request) {

        if (bindingResult.hasErrors()){
            return "registration";
        }

        String name = myForm.getName();
        String login = myForm.getLogin();
        String password = myForm.getPassword();
        String encodePassword = passwordEncoder.encode(password);

        User user = new User(name, login, encodePassword);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);
        user.setRoles(roleSet);

        userRepository.save(user);



        try {
            request.login(login, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "home";
    }

}
