package com.example.system5.controller.adminController;

import com.example.system5.model.Division;
import com.example.system5.model.Position;
import com.example.system5.repository.DivisionRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PositionController {
    private final DivisionRepository divisionRepository;

    public PositionController(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    @GetMapping(value = "/positions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<Position> getPositions(@PathVariable int id){
        List<Division> divisions = divisionRepository.findAll();
        List<Position> positions = divisions.get(id).getPositions();
        return CollectionModel.of(positions) ;
    }
}
