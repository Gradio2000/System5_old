package com.example.system5.controller;


import com.example.system5.model.System5;
import com.example.system5.repository.System5Repository;
import com.sun.istack.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
public class System5Controller {

    private final System5Repository system5Repository;
    private System5 system5;

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

    @GetMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<System5>> getStart(){
        system5 = system5Repository.getById(1);
        return new ResponseEntity<>(ASSEMBLER.toModel(system5Repository.getById(1)), HttpStatus.OK);
    }
}
