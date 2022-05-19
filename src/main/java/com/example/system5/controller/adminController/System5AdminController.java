package com.example.system5.controller.adminController;

import com.example.system5.model.System5;
import com.example.system5.repository.System5Repository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class System5AdminController {
    private final System5Repository system5Repository;

    public System5AdminController(System5Repository system5Repository) {
        this.system5Repository = system5Repository;
    }

    @GetMapping("/report")
    public String getMonthReport(@RequestParam String month, Model model){
        List<System5> system5List = system5Repository.findAllByMonth(month);
        model.addAttribute(system5List);
        model.addAttribute("month", month);
        return "report";
    }
}
