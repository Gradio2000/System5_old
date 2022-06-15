package com.example.qtest.controller;

import com.example.qtest.dto.TestDto;
import com.example.qtest.model.Test;
import com.example.qtest.repository.GroupTestRepository;
import com.example.qtest.repository.TestReposytory;
import com.example.qtest.service.DtoUtils;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tests")
public class TestController {

    private final GroupTestRepository groupTestRepository;
    private final TestReposytory testReposytory;
    private final DtoUtils dtoUtils;

    public TestController(GroupTestRepository groupTestRepository, TestReposytory testReposytory, DtoUtils dtoUtils) {
        this.groupTestRepository = groupTestRepository;
        this.testReposytory = testReposytory;
        this.dtoUtils = dtoUtils;
    }

    @GetMapping("/list/{id}")
    public String getAllTest(@PathVariable Integer id, @AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", authUser.getUser());
        model.addAttribute("groupTestId", id);
        model.addAttribute("groupTestName", groupTestRepository.findById(id).orElse(null).getName());
        List<TestDto> testDtoList = testReposytory.findAllByGroupId(id).stream()
                .map(dtoUtils :: convertToTestDto)
                .collect(Collectors.toList());
        model.addAttribute("testList", testDtoList);
        return "qtest/testlist";
    }

    @PostMapping("/add")
    public String addGroupTest(@ModelAttribute Test test){
        if (test.getTestName().isEmpty()){
            return "redirect:/tests/list/" + test.getGroupId() + "?error=200";
        }
        testReposytory.save(test);
        return "redirect:/tests/list/" + test.getGroupId();
    }

    @PostMapping("/delete")
    public String deleteGroupTest(@RequestParam (required = false) Integer[] check,
                                  @RequestParam Integer testGroupId){
        try {
            testReposytory.deleteAllById(Arrays.asList(check));
        } catch (NullPointerException e) {
            return "redirect:/tests/list/" + testGroupId + "?error=100";
        }
        return "redirect:/tests/list/" + testGroupId;
    }
}
