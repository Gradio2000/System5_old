package com.example.qtest.controller;

import com.example.qtest.repository.AppointTestRepository;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/appointTests")
@Slf4j
public class AppointTestController {
    private final AppointTestRepository appointTestRepository;


    public AppointTestController(AppointTestRepository appointTestRepository) {
        this.appointTestRepository = appointTestRepository;
    }

    @GetMapping("/getUserAppoint")
    public String getUserAppoint(@AuthenticationPrincipal AuthUser authUser, Model model){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

//        List<AppointTest> appointTestList = appointTestRepository.findAllByUser(authUser.getUser()).stream()
//                .filter(appointTest -> !appointTest.getFinished())
//                .collect(Collectors.toList());
//        Map<Integer, TestDto> testDtoMap = new HashMap<>();
//        for (AppointTest appointTest: appointTestList){
//            testDtoMap.put(appointTest.getId(), TestDto.getInstance(appointTest.getTest()));
//        }
//
//        model.addAttribute("testDtoMap", testDtoMap);
        return "qtest/userAppoint";
    }
}
