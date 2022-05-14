package com.example.system5.controller.adminController;

import com.example.system5.model.Division;
import com.example.system5.model.Position;
import com.example.system5.repository.DivisionRepository;
import com.example.system5.repository.PositionRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Object getPositions(@PathVariable int id){
        List<Division> divisions = divisionRepository.findAll();
        List<Position> positions;
        Map<String, Boolean> error = new HashMap();
        try {
            positions = divisions.get(id).getPositions();
        } catch (IndexOutOfBoundsException e) {
            error.put("myer", true);
            return CollectionModel.of(error);
        }
        if (positions.size() == 0){
            error.put("myer", true);
            return CollectionModel.of(error);
        }

        return CollectionModel.of(positions);
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

    @GetMapping(value = "/position/delete/{id}")
    public String deletePosition(@PathVariable int id){
        positionRepository.deleteById(id);
        return "redirect:/admin/shtat";
    }


}
