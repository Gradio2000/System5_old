package com.example.system5.controller;


import com.example.system5.model.Month;
import com.example.system5.model.System5;
import com.example.system5.model.User;
import com.example.system5.repository.System5Repository;
import com.example.system5.util.AuthUser;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class System5Controller {

    private final System5Repository system5Repository;

    public System5Controller(System5Repository system5Repository) {
        this.system5Repository = system5Repository;
    }

    @GetMapping(value = "/list")
    public String getAll(@AuthenticationPrincipal AuthUser authUser, Model model){
        User user = authUser.getUser();
        List<System5> system5List = system5Repository.findAll(user.getUserId());
        List<Month> monthList = new ArrayList<>(List.of(Month.values()));

        for(System5 system5 : system5List){
            monthList.removeIf(m -> (m.name().equals(system5.getMonth())));
        }

        System5 system5 = new System5();

        model.addAttribute(system5List);
        model.addAttribute(system5);
        model.addAttribute(monthList);
        return "lists";
    }


    @PostMapping("/adds")
    public String add(@AuthenticationPrincipal AuthUser authUser,
                      @ModelAttribute @Valid System5 system5,
                      BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "redirect:/list?error=true";
        }

         User user = authUser.getUser();
         system5.setUserId(user.getUserId());
         system5.setRes1(system5.getRes1().toUpperCase());
         system5.setRes2(system5.getRes2().toUpperCase());
         system5.setRes3(system5.getRes3().toUpperCase());
         system5.setRes4(system5.getRes4().toUpperCase());
         system5.setRes5(system5.getRes5().toUpperCase());
         system5Repository.save(system5);
         return "redirect:/list";
    }

    @GetMapping("/list/{id}")
    public CollectionModel<System5> getByUserId(@PathVariable Integer id){
        List<System5> system5List = system5Repository.findByUserId(id);
        return CollectionModel.of(system5List);
    }
}
