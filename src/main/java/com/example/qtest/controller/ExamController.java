package com.example.qtest.controller;

import com.example.qtest.dto.AppointTestDto;
import com.example.qtest.dto.GroupTestDto;
import com.example.qtest.model.AppointTest;
import com.example.qtest.model.Test;
import com.example.qtest.repository.AppointTestRepository;
import com.example.qtest.repository.GroupTestRepository;
import com.example.qtest.repository.TestReposytory;
import com.example.qtest.service.DtoUtils;
import com.example.system5.dto.UserDto;
import com.example.system5.model.User;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exam")
@PreAuthorize("hasRole('ADMIN_TEST')")
public class ExamController {
    private final AppointTestRepository appointTestRepository;
    private final UserRepository userRepository;
    private final GroupTestRepository groupTestRepository;
    private final DtoUtils dtoUtils;
    private final TestReposytory testReposytory;

    public ExamController(AppointTestRepository appointTestRepository, UserRepository userRepository,
                          GroupTestRepository groupTestRepository, DtoUtils dtoUtils,
                          TestReposytory testReposytory) {
        this.appointTestRepository = appointTestRepository;
        this.userRepository = userRepository;
        this.groupTestRepository = groupTestRepository;
        this.dtoUtils = dtoUtils;
        this.testReposytory = testReposytory;
    }

    @GetMapping("/exam")
    public String appointExam(@AuthenticationPrincipal AuthUser authUser,
                              Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        List<GroupTestDto> groupTestDtoList = groupTestRepository.findAll().stream()
                .map(dtoUtils::convertToGroupTestDtoWithTestName)
                .collect(Collectors.toList());
        model.addAttribute("groupTestDtoList", groupTestDtoList);

        List<UserDto> userDtoList = userRepository.findAll().stream()
                .map(UserDto::getInstance)
                .collect(Collectors.toList());
        model.addAttribute("userDtoList", userDtoList);
        return "qtest/appointExam";
    }

    @GetMapping("/getUsersAppoints")
    @ResponseBody
    public List<Integer> getUsersAppoints(@RequestParam Integer userId){
        return appointTestRepository
                .findAllByUser(userRepository.findById(userId).orElse(null))
                .stream()
                .filter(appointTest -> !appointTest.getFinished())
                .map(appointTest -> appointTest.getTest().getTestId())
                .collect(Collectors.toList());
    }

    @PostMapping("/appointExam")
    @ResponseBody
    public HttpStatus appointExam(@RequestParam Integer testId, @RequestParam Integer userId,
                                  @RequestParam (required = false) String base, @RequestParam (required = false) Boolean eko){
        User user = userRepository.findById(userId).orElse(null);
        Test test = testReposytory.findById(testId).orElse(null);
        AppointTest appointTest = appointTestRepository.findByUserAndTestAndFinished(user, test, false);
        if (appointTest == null){
            appointTest = new AppointTest(user, test, false, base, eko);
            appointTestRepository.save(appointTest);
        }
        else {
            appointTestRepository.deleteByUserAndTest(userId, testId);
        }
        return HttpStatus.OK;
    }

    @GetMapping ("/journal")
    public String getJournal(@AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        List<AppointTest> appointTestList = appointTestRepository.findAllByFinishedAndEko(true, true);

        List<AppointTestDto> appointTestDtoList = dtoUtils.convertToAppointTestDtoList(appointTestList);
        model.addAttribute("appointTestDtoList", appointTestDtoList);
        return "qtest/journalEko";
    }
}
