package com.example.system5.controller;

import com.example.system5.dto.UserDto;
import com.example.system5.model.*;
import com.example.system5.repository.PositionRepository;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import com.example.system5.validation.FormFinishRegValidator;
import com.example.system5.validation.MyFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@Slf4j
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PositionRepository positionRepository;
    private final MyFormValidator myFormValidator;
    private final FormFinishRegValidator formFinishRegValidator;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                  PositionRepository positionRepository, MyFormValidator myFormValidator,
                                  FormFinishRegValidator formFinishRegValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.positionRepository = positionRepository;
        this.myFormValidator = myFormValidator;
        this.formFinishRegValidator = formFinishRegValidator;
    }

    @InitBinder("myForm")
    public void initBinderMyForm(WebDataBinder binder){
        binder.addValidators(myFormValidator);
    }

    @InitBinder("formFinishReg")
    public void initBinderFormFinishReg(WebDataBinder binder){
        binder.addValidators(formFinishRegValidator);
    }


    @GetMapping("/registration")
    public ModelAndView registration(){
        MyForm myForm = new MyForm();
        return new ModelAndView("registration", "myForm", myForm);
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute @Valid MyForm myForm, BindingResult bindingResult,
                          Model model, HttpServletRequest request) {

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

        FormFinishReg formFinishReg = new FormFinishReg();
        model.addAttribute(formFinishReg);
        return "registrationnext";
    }

    @PostMapping("/finishreg")
    public String finishRegistration(@AuthenticationPrincipal AuthUser authUser,
                                     @ModelAttribute @Valid FormFinishReg formFinishReg,
                                     BindingResult bindingResult,
                                     Model model){

        if (bindingResult.hasErrors()){
            List<Position> positionList = positionRepository.findAll();
            model.addAttribute(positionList);
            return "registrationnext";
        }

        User user = authUser.getUser();
        user.setName(formFinishReg.getName());
        Position position = positionRepository.findById(formFinishReg.getPosition_id()).orElse(null);
        user.setPosition(position);
        userRepository.save(user);
        model.addAttribute("user", UserDto.getInstance(user));
        return "redirect:/list";
    }

    @GetMapping("/cancel")
    public String cancelReg(){
        return "redirect:/logout";
    }

}
