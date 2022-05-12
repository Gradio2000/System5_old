package com.example.system5.controller.adminController;

import com.example.system5.model.Division;
import com.example.system5.model.Position;
import com.example.system5.repository.DivisionRepository;
import com.example.system5.repository.PositionRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class PositionController {
    private final DivisionRepository divisionRepository;
    private final PositionRepository positionRepository;

    public PositionController(DivisionRepository divisionRepository, PositionRepository positionRepository) {
        this.divisionRepository = divisionRepository;
        this.positionRepository = positionRepository;
    }

    @GetMapping(value = "/positions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CollectionModel<Position> getPositions(@PathVariable int id){
        List<Division> divisions = divisionRepository.findAll();
        List<Position> positions = divisions.get(id).getPositions();
        return CollectionModel.of(positions) ;
    }


    @PostMapping(value = "/position/{id}")
    public String addPosition(@RequestParam String position, @PathVariable int id){
        if (position.isEmpty()){
            return "redirect:/admin/shtat?errorposition=true";
        }
        Position positionEntity = new Position();
        positionEntity.setPosition(position);
        positionEntity.setDivisionId(id);
        positionRepository.save(positionEntity);
        return "redirect:/admin/shtat";
    }
}
