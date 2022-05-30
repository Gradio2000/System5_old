package com.example.system5.controller.adminController;

import com.example.system5.model.Position;
import com.example.system5.repository.DivisionRepository;
import com.example.system5.repository.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Slf4j
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
        List<Position> positions;
        Map<String, Boolean> error = new HashMap();
        try {
            positions = positionRepository.findAllByDivisionId(id);
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


    @PostMapping(value = "/position")
    public String addPosition(@RequestParam String position, @RequestParam int id){
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

    @PostMapping("/position/change")
    public String changePosition(@RequestParam String position, @RequestParam int id){
        Position positionForChange = positionRepository.findById(id).orElse(null);
        if (position.isEmpty()){
            return "redirect:/admin/shtat?errorPositionChange=true";
        }
        assert positionForChange != null;
        positionForChange.setPosition(position);
        positionRepository.save(positionForChange);
        return "redirect:/admin/shtat";
    }



}
