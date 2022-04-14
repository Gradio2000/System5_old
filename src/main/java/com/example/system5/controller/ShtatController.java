package com.example.system5.controller;

import com.example.system5.model.Shtat;
import com.example.system5.repository.ShtatRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShtatController {
    private final ShtatRepository shtatRepository;

    public ShtatController(ShtatRepository shtatRepository) {
        this.shtatRepository = shtatRepository;
    }

    @GetMapping(value = "/shtat")
    public CollectionModel<Shtat> getShtat(){
        return CollectionModel.of(shtatRepository.findAll());
    }
}
