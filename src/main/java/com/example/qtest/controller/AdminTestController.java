package com.example.qtest.controller;

import com.example.qtest.dto.TestDto;
import com.example.qtest.model.Test;
import com.example.qtest.repository.AttemptestReporitory;
import com.example.qtest.repository.GroupTestRepository;
import com.example.qtest.repository.TestReposytory;
import com.example.qtest.service.DtoUtils;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tests")
@PreAuthorize("hasRole('ADMIN_TEST')")
public class AdminTestController {

    private final GroupTestRepository groupTestRepository;
    private final TestReposytory testReposytory;
    private final DtoUtils dtoUtils;
    private final AttemptestReporitory attemptestReporitory;
    private final UserRepository userRepository;

    public AdminTestController(GroupTestRepository groupTestRepository,
                               TestReposytory testReposytory, DtoUtils dtoUtils,
                               AttemptestReporitory attemptestReporitory,
                               UserRepository userRepository) {
        this.groupTestRepository = groupTestRepository;
        this.testReposytory = testReposytory;
        this.dtoUtils = dtoUtils;
        this.attemptestReporitory = attemptestReporitory;
        this.userRepository = userRepository;
    }

    @GetMapping("/list/{id}")
    public String getAllTest(@PathVariable Integer id, @AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", authUser.getUser());
        model.addAttribute("groupTestId", id);
        model.addAttribute("groupTestName", Objects.requireNonNull(groupTestRepository.findById(id).orElse(null)).getName());
        List<TestDto> testDtoList = testReposytory.findAllByGroupIdOrderByTestId(id).stream()
                .map(TestDto::getInstance)
                .collect(Collectors.toList());
        model.addAttribute("testList", testDtoList);
        return "qtest/administration/testlist";
    }

    @PostMapping("/add")
    public String addGroupTest(@ModelAttribute Test test){
        if (test.getTestName().isEmpty()){
            return "redirect:/tests/list/" + test.getGroupId() + "?error=200";
        }
        test.setQuesMix(true);
        test.setQuesAmount(0);
        test.setTime(0.0);
        test.setCriteria(0.0);
        test.setDeleted(false);
        testReposytory.save(test);
        return "redirect:/tests/list/" + test.getGroupId();
    }

    @PostMapping("/delete")
    public String deleteGroupTest(@RequestParam (required = false) Integer[] check,
                                  @RequestParam Integer testGroupId){

        if (check == null){
            return "redirect:/tests/list/" + testGroupId + "?error=100";
        }

        testReposytory.makeTestsDeletedTrue(check);

        Set<Integer> testIdsUsingInAttempts = attemptestReporitory.findAllTestId();
        Set<Integer> checks = new HashSet<>(Arrays.asList(check));
        checks.removeIf(testIdsUsingInAttempts::contains);
        testReposytory.deleteAllById(checks);

        return "redirect:/tests/list/" + testGroupId;
    }

    @PostMapping("/undelete")
    public String undeleteTest(@RequestParam Integer testGroupId, @RequestParam Integer testId){
        testReposytory.undeleteTest(testId);
        return "redirect:/tests/list/" + testGroupId;
    }

    @PostMapping("/change/")
    @ResponseBody
    public HttpStatus changeTest(@ModelAttribute TestDto testDto){
        Test test = testReposytory.findById(testDto.getTestId()).orElse(null);
        assert test != null;
        test.setTestName(testDto.getTestName());
        test.setCriteria(testDto.getCriteria());
        test.setTime(testDto.getTime());
        test.setQuesAmount(testDto.getQuesAmount());
        test.setQuesMix(testDto.getQuesMix() != null);
        testReposytory.save(test);
        return HttpStatus.OK;
    }

}
