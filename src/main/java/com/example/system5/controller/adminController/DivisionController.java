package com.example.system5.controller.adminController;

import com.example.system5.model.Division;
import com.example.system5.repository.DivisionRepository;
import com.example.system5.repository.PositionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class DivisionController {
    private final DivisionRepository divisionRepository;


    public DivisionController(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    @GetMapping("/shtat")
    public String getStat(Model model){
        List<Division> divisions = divisionRepository.findAll();
        model.addAttribute(divisions);
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


}
