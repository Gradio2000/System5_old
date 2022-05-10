package com.example.system5.controller.adminController;

import com.example.system5.model.Division;
import com.example.system5.repository.DivisionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
