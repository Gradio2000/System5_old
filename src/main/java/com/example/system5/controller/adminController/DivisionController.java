package com.example.system5.controller.adminController;

import com.example.system5.dto.UserDto;
import com.example.system5.model.Division;
import com.example.system5.repository.DivisionRepository;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class DivisionController {
    private final DivisionRepository divisionRepository;

    public DivisionController(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    @GetMapping("/shtat")
    public String getStat(@AuthenticationPrincipal AuthUser authUser, Model model){
        List<Division> divisions = divisionRepository.findAll();
        model.addAttribute(divisions);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "/shtat";
    }

    @PostMapping(value = "/division")
    public String addDivision(@RequestParam String division){
        if (division.isEmpty()){
            return "redirect:/admin/shtat?errordivision=true";
        }
        Division divisionEntity = new Division();
        divisionEntity.setDivision(division);
        divisionRepository.save(divisionEntity);
        return "redirect:/admin/shtat";
    }

    @GetMapping(value = "/division/{id}")
    public String deleteDivision(@PathVariable int id){


        Division division = divisionRepository.findById(id).orElse(null);
        assert division != null;
        if (division.getPositions().size() > 0){
            return "redirect:/admin/shtat?errorDivisionDelete=true";
        }
        else {
            divisionRepository.deleteById(id);
            return "redirect:/admin/shtat";
        }
    }

    @PostMapping("/division/change")
    public String chanheDivision(@RequestParam String divisionName,
                                 @RequestParam int id){
        Division division = divisionRepository.findById(id).orElse(null);
        if (divisionName.isEmpty()){
            return "redirect:/admin/shtat?errorDivisionChange=true";
        }
        assert division != null;
        division.setDivision(divisionName);
        divisionRepository.save(division);
        return "redirect:/admin/shtat";
    }

    @GetMapping("/get_division/{id}")
    @ResponseBody
    public String getDivisionByID(@PathVariable Integer id){
       Division division = divisionRepository.findById(id).orElse(null);
        assert division != null;
        if (division.getPositions().size() > 0){
           return "Сначала удалите все должности подразделения";
       }
        else return "Успех";
    }
}
