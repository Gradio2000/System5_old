package com.example.qtest.controller;

import com.example.qtest.dto.TestDto;
import com.example.qtest.repository.AppointTestRepository;
import com.example.qtest.service.DtoUtils;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointTests")
public class AppointTestController {
    private final AppointTestRepository appointTestRepository;
    private final DtoUtils dtoUtils;

    public AppointTestController(AppointTestRepository appointTestRepository, DtoUtils dtoUtils) {
        this.appointTestRepository = appointTestRepository;
        this.dtoUtils = dtoUtils;
    }

    @GetMapping("/getUserAppoint")
    public String getUserAppoint(@AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        List<TestDto> testDtoList = appointTestRepository.findAllByUser(authUser.getUser()).stream()
                .map(appointTest -> dtoUtils.convertToTestDto(appointTest.getTest()))
                .collect(Collectors.toList());
        model.addAttribute("testDtoList", testDtoList);
        return "qtest/userAppoint";
    }
}
