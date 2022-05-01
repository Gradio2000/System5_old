package com.example.system5.controller;


import com.example.system5.model.System5;
import com.example.system5.model.User;
import com.example.system5.repository.System5Repository;
import com.example.system5.util.AuthUser;
import com.sun.istack.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Controller
public class System5Controller {

    private final System5Repository system5Repository;

    public System5Controller(System5Repository system5Repository) {
        this.system5Repository = system5Repository;
    }

    private static final RepresentationModelAssemblerSupport<System5, EntityModel<System5>> ASSEMBLER =
            new RepresentationModelAssemblerSupport<>(System5Controller.class, (Class<EntityModel<System5>>) (Class<?>) EntityModel.class) {
                @Override
                public @NotNull EntityModel<System5> toModel(@NotNull System5 system5) {
                    return EntityModel.of(system5, linkTo(System5Controller.class).withSelfRel());
                }
            };

    @GetMapping(value = "/list")
    public String getAll(@AuthenticationPrincipal AuthUser authUser, Model model){
        User user = authUser.getUser();
        List<System5> system5List = system5Repository.findAll(user.getUserId());
        System5 system5 = new System5();
        model.addAttribute(system5List);
        model.addAttribute(system5);
        return "lists";
    }


    @PostMapping("/adds")
    public String add(@AuthenticationPrincipal AuthUser authUser,
                      @ModelAttribute System5 system5){
         User user = authUser.getUser();
         system5.setUserId(user.getUserId());
         system5Repository.save(system5);
         return "redirect:/list";
    }

    @GetMapping("/list/{id}")
    public CollectionModel<System5> getByUserId(@PathVariable Integer id){
        List<System5> system5List = system5Repository.findByUserId(id);
        return CollectionModel.of(system5List);
    }
}
