package com.example.qtest.controller;

import com.example.qtest.model.Attempttest;
import com.example.qtest.model.QuestionsForAttempt;
import com.example.qtest.model.Test;
import com.example.qtest.repository.AttemptestReporitory;
import com.example.qtest.repository.QuestionForAttemptRepository;
import com.example.qtest.repository.TestReposytory;
import com.example.qtest.service.TestService;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/processing")
public class TestProcessingController {
    private final AttemptestReporitory attemptestReporitory;
    private final TestReposytory testReposytory;
    private final TestService testService;
    private final QuestionForAttemptRepository questionForAttemptRepository;

    public TestProcessingController(AttemptestReporitory attemptestReporitory, TestReposytory testReposytory,
                                    TestService testService, QuestionForAttemptRepository questionForAttemptRepository) {
        this.attemptestReporitory = attemptestReporitory;
        this.testReposytory = testReposytory;
        this.testService = testService;
        this.questionForAttemptRepository = questionForAttemptRepository;
    }

    @PostMapping("/start")
    public String startTestProcessing(@AuthenticationPrincipal AuthUser authUser,
                                      Model model, @RequestParam Integer testId){

        Attempttest attempttest = new Attempttest();
        attempttest.setDateTime(Instant.now());
        attempttest.setUserId(authUser.getUser().getUserId());
        attempttest.setTestId(testId);
        attemptestReporitory.save(attempttest);

        Test test = testReposytory.findById(testId).orElse(null);
        assert test != null;
        testService.getShuffleTest(test);

        model.addAttribute("model", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("test", test);
        List<QuestionsForAttempt> questionsForAttemptList = testService.convertTestForSaveBeforeTesting(test, attempttest.getId());
        questionForAttemptRepository.saveAll(questionsForAttemptList);
        return "qtest/process";
    }
}
