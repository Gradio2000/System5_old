package com.example.system5.controller;


import com.example.system5.dto.UserDto;
import com.example.system5.model.*;
import com.example.system5.repository.System5Repository;
import com.example.system5.repository.UserRepository;
import com.example.system5.service.System5Service;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class System5Controller {

    private final System5Repository system5Repository;
    private final UserRepository userRepository;
    private final System5Service system5Service;


    public System5Controller(System5Repository system5Repository,
                             UserRepository userRepository,
                             System5Service system5Service) {
        this.system5Repository = system5Repository;
        this.userRepository = userRepository;
        this.system5Service = system5Service;
    }

    @GetMapping(value = "/list")
    public String getAll(@AuthenticationPrincipal AuthUser authUser, Model model){
        User user = authUser.getUser();

        List<System5> system5List = system5Repository.findAllByUserId(user.getUserId());
        model.addAttribute(system5List);

        List<Month> monthList = Arrays.stream(Month.values()).collect(Collectors.toList());
        for(System5 system5 : system5List){
            monthList.removeIf(m -> (m.name().equals(system5.getMonth())));
        }
        model.addAttribute(monthList);

        System5 system5 = new System5();
        model.addAttribute(system5);

        System5empl system5empl = new System5empl();
        model.addAttribute(system5empl);

        boolean employer = false;
        model.addAttribute("employer", employer);

        List<User> userList = userRepository.findAll().stream()
                .filter(u -> u.getUserId() != user.getUserId())
                .collect(Collectors.toList());
        model.addAttribute(userList);

        model.addAttribute("user", UserDto.getInstance(user));

        return "lists";
    }

    @GetMapping("/list/{id}")
    public String getByUserId(@PathVariable Integer id,
                              @ModelAttribute System5 system5,
                              BindingResult bindingResult,
                              @ModelAttribute System5empl system5empl,
                              BindingResult bindingResult1,
                              Model model,
                              @AuthenticationPrincipal AuthUser authUser){

        List<System5> system5List = system5Repository.findByUserIdOrderBySystem5Id(id);

        Map<Integer, Month> monthMap = new HashMap();
        for (System5 system51 : system5List){
            if (system51.getRated() == 0){
                monthMap.put(system51.getSystem5Id(), Month.valueOf(system51.getMonth()));
            }
        }

        boolean employer = true;
        model.addAttribute(system5List);
        model.addAttribute("employer", employer);
        model.addAttribute("months", monthMap);
        model.addAttribute("userId", id);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "lists";
    }


    @PostMapping("/adds")
    public String add(@AuthenticationPrincipal AuthUser authUser,
                      @ModelAttribute @Valid System5 system5,
                      BindingResult bindingResult,
                      @RequestParam int comm_id){

        if (bindingResult.hasErrors()){
            return "redirect:/list?error=1";
        }

         User user = authUser.getUser();
         system5.setUserId(user.getUserId());
         system5.setRes1(system5.getRes1().toUpperCase());
         system5.setRes2(system5.getRes2().toUpperCase());
         system5.setRes3(system5.getRes3().toUpperCase());
         system5.setRes4(system5.getRes4().toUpperCase());
         system5.setRes5(system5.getRes5().toUpperCase());

         int positoin_id = authUser.getUser().getPosition().getPosition_id();
         if (userRepository.existsCommanderPosition(positoin_id)){
             userRepository.updateCommanderPosition(comm_id, positoin_id);
         }
         else {
             userRepository.commEmpAdd(comm_id, positoin_id);
         }

         TotalMark5 totalMark5 = new TotalMark5();
         totalMark5.setTotalMark(system5Service.getTotalMark(system5));
         system5.setTotalMark5(totalMark5);
         totalMark5.setSystem5(system5);

         system5Repository.save(system5);
         return "redirect:/list";
    }

    @PostMapping("/addsempl")
    public String addempl(@AuthenticationPrincipal AuthUser authUser,
                          @ModelAttribute @Valid System5empl system5empl,
                          BindingResult bindingResult){

        int userId= system5empl.getUser_id();
        if (bindingResult.hasErrors()){
            return "redirect:/list/" + userId + "?error=2";
        }

        system5empl.setResempl1(system5empl.getResempl1().toUpperCase());
        system5empl.setResempl2(system5empl.getResempl2().toUpperCase());
        system5empl.setResempl3(system5empl.getResempl3().toUpperCase());
        system5empl.setResempl4(system5empl.getResempl4().toUpperCase());
        system5empl.setResempl5(system5empl.getResempl5().toUpperCase());

        System5 system5 = system5Repository.findById(system5empl.getSystem5Id()).orElse(null);

        assert system5 != null;
        TotalMark5 totalMark5 = system5.getTotalMark5();
        totalMark5.setTotalMarkEmpl(system5Service.getTotalMarkEmpl(system5empl));

        system5.setTotalMark5(totalMark5);
        system5.setRated(1);
        system5.setSystem5empl(system5empl);

        system5empl.setSystem5(system5);

        system5Repository.save(system5);
        return "redirect:/list/" + userId;
    }


}
