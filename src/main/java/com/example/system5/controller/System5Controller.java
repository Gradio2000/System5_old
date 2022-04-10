package com.example.system5.controller;


import com.example.system5.model.System5;
import com.example.system5.repository.System5Repository;
import com.sun.istack.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
    public ResponseEntity<EntityModel<System5>> getStart(int id){
        return new ResponseEntity<>(ASSEMBLER.toModel(system5Repository.getById(id)), HttpStatus.OK);
    }


    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<System5> getAll(){
        List<System5> system5List = system5Repository.findAll();
        for (System5 system5 : system5List){
            Link selfLink = linkTo(methodOn(System5Controller.class)
                    .getStart(system5.getSystem5Id())).withSelfRel();

            system5.add(selfLink);
        }
        Link link = linkTo(methodOn(System5Controller.class)).withSelfRel();

        return CollectionModel.of(system5List, link);
    }

    @PostMapping(value = "/start", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<System5>> addMark(@RequestBody System5 system5){
        system5Repository.save(system5);
        return new ResponseEntity<>(ASSEMBLER.toModel(system5), HttpStatus.OK);
    }


}
