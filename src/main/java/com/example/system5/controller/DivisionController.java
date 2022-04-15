package com.example.system5.controller;

import com.example.system5.model.Division;
import com.example.system5.repository.DivisionRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DivisionController {
    private final DivisionRepository divisionRepository;

    public DivisionController(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    @GetMapping("/shtat")
    public CollectionModel<Division> getStat(){
        return CollectionModel.of(divisionRepository.findAll());
    }
}
