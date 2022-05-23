package com.example.system5.controller.adminController;

import com.example.system5.dto.UserDto;
import com.example.system5.model.Month;
import com.example.system5.model.System5;
import com.example.system5.repository.System5Repository;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class ReportController {
    System5Repository system5Repository;

    public ReportController(System5Repository system5Repository) {
        this.system5Repository = system5Repository;
    }

    @GetMapping("/prepareReport")
    public String getMonths(@AuthenticationPrincipal AuthUser authUser, Model model){
        List<String> months = Arrays.stream(Month.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        model.addAttribute("months", months);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "prepareReport";
    }

    @GetMapping("/report")
    public String getMonthReport(@AuthenticationPrincipal AuthUser authUser,
                                 @RequestParam String month,
                                 Model model){

        List<System5> system5List = system5Repository.findAllByMonth(month);
        model.addAttribute(system5List);
        model.addAttribute("month", month);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "report";
    }
}
