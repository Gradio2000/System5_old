package com.example.qtest.controller;

import com.example.qtest.dto.GroupTestDto;
import com.example.qtest.dto.TestDto;
import com.example.qtest.model.Attempttest;
import com.example.qtest.repository.AttemptestReporitory;
import com.example.qtest.repository.GroupTestRepository;
import com.example.qtest.repository.TestReposytory;
import com.example.qtest.service.DtoUtils;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tests")
@Slf4j
public class ProcessTestController {

    private final GroupTestRepository groupTestRepository;
    private final TestReposytory testReposytory;
    private final DtoUtils dtoUtils;
    private final AttemptestReporitory attemptestReporitory;


    public ProcessTestController(GroupTestRepository groupTestRepository, TestReposytory testReposytory,
                                 DtoUtils dtoUtils, AttemptestReporitory attemptestReporitory) {
        this.groupTestRepository = groupTestRepository;
        this.testReposytory = testReposytory;
        this.dtoUtils = dtoUtils;
        this.attemptestReporitory = attemptestReporitory;
    }

    @GetMapping("listForTesting/{id}")
    public String listForTesting(@AuthenticationPrincipal AuthUser authUser, Model model, @PathVariable Integer id){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        List<TestDto> testList = testReposytory.findAllDeletedTestsByGroupIdOrderByTestId(id).stream()
                .map(TestDto::getInstance)
                .collect(Collectors.toList());
        model.addAttribute("testList", testList);
        GroupTestDto groupTest = dtoUtils.convertToGroupTestDto(Objects.requireNonNull(groupTestRepository.findById(id).orElse(null)));
        model.addAttribute("groupTest", groupTest);
        return "qtest/forTesting/allTestsForTesting";
    }

    @PostMapping("/listForTesting/test")
    public String getTestForTesting(@AuthenticationPrincipal AuthUser authUser, Model model,
                                    @RequestParam (required = false) Integer[] testIds,
                                    @RequestParam Integer quesAmount, @RequestParam Integer criteria,
                                    @RequestParam (required = false) String testName){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        if (testIds.length > 1){
            model.addAttribute("testIds", Arrays.stream(testIds).collect(Collectors.toList()));
            model.addAttribute("consolidTest", true);
            model.addAttribute("testName", testName);
        }
        else {
            TestDto testDto = TestDto.getInstance(Objects.requireNonNull(testReposytory.findById(testIds[0]).orElse(null)));
            model.addAttribute("testDto", testDto);
            model.addAttribute("consolidTest", false);
        }

        model.addAttribute("quesAmount", quesAmount);
        model.addAttribute("criteria", criteria);

        return "qtest/forTesting/testForTesting";
    }


    @GetMapping("/mytests")
    public String getUserAttempts(@AuthenticationPrincipal AuthUser authUser, Model model,
                                  @RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam (defaultValue = "10") Integer size,
                                  @RequestParam (defaultValue = "up") String sort){

        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        Pageable pageable;
        if (sort.equals("down")){
            pageable = PageRequest.of(page, size, Sort.by("dateTime").descending());
        }
        else {
            pageable = PageRequest.of(page, size, Sort.by("dateTime").ascending());
        }
        Page<Attempttest> attemptsList = attemptestReporitory.findAllByUser(authUser.getUser(), pageable);
        model.addAttribute("attemptsList", attemptsList);
        model.addAttribute("sort", sort);
        return "qtest/myTestsList";
    }
}
