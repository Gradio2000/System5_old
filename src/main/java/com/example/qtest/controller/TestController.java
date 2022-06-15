package com.example.qtest.controller;

import com.example.qtest.model.Test;
import com.example.qtest.repository.TestReposytory;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestController {

    private final TestReposytory testReposytory;

    public TestController(TestReposytory testReposytory) {
        this.testReposytory = testReposytory;
    }

    @GetMapping("/list/{id}")
    public String getAllTest(@PathVariable Integer id, @AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", authUser.getUser());
        List<Test> testList = testReposytory.findAllByGroupTestGrouptestId(id);
        model.addAttribute("testList", testList);
        model.addAttribute("groupTestId", id);
        return "qtest/testlist";
    }

    @PostMapping("/add")
    public String addGroupTest(@ModelAttribute Test test){
        if (test.getTestName().isEmpty()){
            return "redirect:/tests/list/" + test.getGroupTest().getGrouptestId() + "?error=200";
        }
        testReposytory.save(test);
        return "redirect:/tests/list/" + test.getGroupTest().getGrouptestId();
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
