package com.example.qtest.controller;

import com.example.qtest.model.Attempttest;
import com.example.qtest.repository.AttemptestReporitory;
import com.example.qtest.repository.TestReposytory;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

@Controller
@RequestMapping("/processing")
public class TestProcessingController {
    private final AttemptestReporitory attemptestReporitory;
    private final TestReposytory testReposytory;

    public TestProcessingController(AttemptestReporitory attemptestReporitory, TestReposytory testReposytory) {
        this.attemptestReporitory = attemptestReporitory;
        this.testReposytory = testReposytory;
    }

    @GetMapping("/start")
    public String startTestProcessing(@AuthenticationPrincipal AuthUser authUser,
                                      Model model,
                                      @RequestParam Integer testId){
        model.addAttribute("model", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("test", testReposytory.findById(testId).orElse(null));

        Attempttest attempttest = new Attempttest();
        attempttest.setDateTime(Instant.now());
        attempttest.setUserId(authUser.getUser().getUserId());
        attempttest.setTestId(testId);
        attemptestReporitory.save(attempttest);

        return "qtest/process";
    }
}
