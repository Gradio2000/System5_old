package com.example.system5.controller.adminController;

import com.example.system5.model.Position;
import com.example.system5.repository.PositionRepository;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Slf4j
public class PositionController {
    private final PositionRepository positionRepository;

    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @GetMapping(value = "/positions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object getPositions(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

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
    public String addPosition(@RequestParam String position, @RequestParam int id,
                              @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

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
    public String deletePosition(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        Position position = positionRepository.findById(id).orElse(null);
        assert position != null;
        if (position.getUser() != null){
            return "redirect:/admin/shtat?errorDeletePosition=true";
        }
        else {
            positionRepository.deleteById(id);
            return "redirect:/admin/shtat";
        }
    }

    @PostMapping("/position/change")
    public String changePosition(@RequestParam String position, @RequestParam int id,
                                 @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

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
