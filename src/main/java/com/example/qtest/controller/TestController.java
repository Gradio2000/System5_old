package com.example.qtest.controller;

import com.example.qtest.model.Test;
import com.example.qtest.repository.TestReposytory;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("model", authUser.getUser());
        List<Test> testList = testReposytory.findAllByGroupId(id);
        model.addAttribute("testList", testList);
        return "qtest/testlist";
    }
}
