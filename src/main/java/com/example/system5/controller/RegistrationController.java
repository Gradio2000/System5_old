package com.example.system5.controller;

import com.example.system5.model.*;


import com.example.system5.repository.PositionRepository;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import com.example.system5.util.Validation;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.List;
import java.util.Set;


@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PositionRepository positionRepository;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.positionRepository = positionRepository;
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

        String login = myForm.getLogin();
        String password = myForm.getPassword();
        String encodePassword = passwordEncoder.encode(password);

        User user = new User(login, encodePassword);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);
        user.setRoles(roleSet);

        userRepository.save(user);



        try {
            request.login(login, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        List<Position> positionList = positionRepository.findAll();
        model.addAttribute(positionList);
        return "registrationnext";
    }

    @PostMapping("/finishreg")
    public String finishRegistration(@ModelAttribute FormFinishReg formFinishReg){
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getById(authUser.getUser().getUserId());
        user.setName(formFinishReg.getName());
        user.setPosition_id(formFinishReg.getPosition_id());
        userRepository.save(user);
        return "redirect:/home";
    }

}
