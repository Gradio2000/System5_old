package com.example.qtest.controller;

import com.example.qtest.model.Attempttest;
import com.example.qtest.model.QuestionsForAttempt;
import com.example.qtest.model.ResultTest;
import com.example.qtest.model.Test;
import com.example.qtest.repository.AttemptestReporitory;
import com.example.qtest.repository.QuestionForAttemptRepository;
import com.example.qtest.repository.ResultTestReposytory;
import com.example.qtest.repository.TestReposytory;
import com.example.qtest.service.TestService;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/processing")
public class TestProcessingController {
    private final AttemptestReporitory attemptestReporitory;
    private final TestReposytory testReposytory;
    private final TestService testService;
    private final QuestionForAttemptRepository questionForAttemptRepository;
    private final ResultTestReposytory resultTestReposytory;

    public TestProcessingController(AttemptestReporitory attemptestReporitory,
                                    TestReposytory testReposytory, TestService testService,
                                    QuestionForAttemptRepository questionForAttemptRepository,
                                    ResultTestReposytory resultTestReposytory) {
        this.attemptestReporitory = attemptestReporitory;
        this.testReposytory = testReposytory;
        this.testService = testService;
        this.questionForAttemptRepository = questionForAttemptRepository;
        this.resultTestReposytory = resultTestReposytory;
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

            List<QuestionsForAttempt> questionsForAttemptList = testService.convertTestForSaveBeforeTesting(test, attempttest.getId());
            questionForAttemptRepository.saveAll(questionsForAttemptList);

            model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
            model.addAttribute("testName", test.getTestName());
            model.addAttribute("questionList", questionsForAttemptList);
            return "qtest/process";
    }

    @PostMapping("/saveUserAnswer")
    @ResponseBody
    public HttpStatus saveUserAnswer(@RequestParam Integer attemptId, @RequestParam Integer questionId,
                                     HttpServletRequest request){
        String[] quesIds = request.getParameterMap().get("check");
        List<ResultTest> resultTestList = new ArrayList<>();
        for (int i = 0; i < quesIds.length; i++) {
            ResultTest resulttest = new ResultTest();
            resulttest.setAttemptId(attemptId);
            resulttest.setQuestionId(questionId);
            resulttest.setAnswerId(Integer.valueOf(quesIds[i]));
            resultTestList.add(resulttest);
        }
        resultTestReposytory.saveAll(resultTestList);
        return HttpStatus.OK;
    }
}
