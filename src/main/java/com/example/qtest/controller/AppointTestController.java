package com.example.qtest.controller;

import com.example.qtest.dto.AppointTestDto;
import com.example.qtest.repository.AppointTestRepository;
import com.example.qtest.service.DtoUtils;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointTests")
@Slf4j
public class AppointTestController {
    private final AppointTestRepository appointTestRepository;
    private final DtoUtils dtoUtils;


    public AppointTestController(AppointTestRepository appointTestRepository, DtoUtils dtoUtils) {
        this.appointTestRepository = appointTestRepository;
        this.dtoUtils = dtoUtils;
    }

    @GetMapping("/getUserAppoint")
    public String getUserAppoint(@AuthenticationPrincipal AuthUser authUser, Model model){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        List<AppointTestDto> appointTestList = dtoUtils.convertToAppointTestDtoList(
                appointTestRepository.findAllByUser(authUser.getUser()).stream()
                .filter(appointTest -> !appointTest.getFinished())
                .collect(Collectors.toList()));
        model.addAttribute("appointTestList", appointTestList);
        return "qtest/userAppoint";
    }
}
