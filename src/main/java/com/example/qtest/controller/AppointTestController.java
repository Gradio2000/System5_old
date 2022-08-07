package com.example.qtest.controller;

import com.example.qtest.dto.TestDto;
import com.example.qtest.model.AppointTest;
import com.example.qtest.repository.AppointTestRepository;
import com.example.qtest.service.DtoUtils;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        List<AppointTest> appointTestList = appointTestRepository.findAllByUser(authUser.getUser()).stream()
                .filter(appointTest -> !appointTest.getFinished())
                .collect(Collectors.toList());
        Map<Integer, TestDto> testDtoMap = new HashMap<>();
        for (AppointTest appointTest: appointTestList){
            testDtoMap.put(appointTest.getId(), dtoUtils.convertToTestDto(appointTest.getTest()));
        }

        model.addAttribute("testDtoMap", testDtoMap);
        return "qtest/userAppoint";
    }
}
